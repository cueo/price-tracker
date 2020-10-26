package io.cueo.pricetracker.games.ps

import io.cueo.pricetracker.games.StoreService
import org.slf4j.LoggerFactory
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder

/**
 * @author mmayank
 */
@Service
class PSService : StoreService {


    val scheme = "https"
    val host = "store.playstation.com"

    private var webClient = webClient()

    private fun webClient(): WebClient {
        return WebClient.builder()
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs { configurer: ClientCodecConfigurer ->
                    configurer.defaultCodecs().maxInMemorySize(1024 * 1024)
                }
                .build())
            .build()
    }

    override fun search(query: String): List<String> {
        val path = "valkyrie-api/en/US/999/faceted-search/$query"
        mapOf(
            "query" to query,
            "size" to "10",
            "bucket" to "games"
        )
        val result = webClient.get()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder.scheme(scheme)
                    .host(host)
                    .path(path)
                    .queryParam("query", query)
                    .queryParam("size", "30")  // TODO: extract to a common class
                    .queryParam("bucket", "games")
                    .build()
            }.retrieve()
            .bodyToMono(SearchResult::class.java)
            .block()
        return result?.data?.relationships?.children?.data?.filter { it.type == "game" }?.map { it.id } ?: emptyList()
    }

    override fun appDetails(id: String): Response? {
        val path = "store/api/chihiro/00_09_000/container/US/en/999/$id"
        return webClient.get()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder.scheme(scheme)
                    .host(host)
                    .path(path)
                    .build()
            }.retrieve()
            .bodyToMono(Response::class.java)
            .block()
    }
}

fun main() {
    val ps = PSService()
    val ids = ps.search("gta")
    val log = LoggerFactory.getLogger(PSService::class.java)
    log.info("ids={}", ids)
    log.info("app={}", ps.appDetails(ids[0]))
}
