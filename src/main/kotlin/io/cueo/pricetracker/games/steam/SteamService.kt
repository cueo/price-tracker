package io.cueo.pricetracker.games.steam

import io.cueo.pricetracker.games.StoreService
import org.jsoup.Jsoup
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder

/**
 * @author mmayank
 */
@Service
class SteamService : StoreService {

    private val scheme = "https"
    private val host = "store.steampowered.com"

    private val webClient = WebClient.create()

    override fun search(query: String) : List<String> {
        val url = "$scheme://$host/search/results?term=$query"
        val document = Jsoup.connect(url).get()
        val resultRows = document.select("#search_resultsRows")[0].select("a")
        return resultRows.map {
            it.attr("data-ds-appid")
        }.filter { it.isNotEmpty() }.toList()
    }

    override fun appDetails(id: String) : Response? = webClient.get()
        .uri { uriBuilder: UriBuilder ->
            uriBuilder.scheme(scheme)
                .host(host)
                .path("/api/appdetails")
                .queryParam("appids", id)
                .build()
        }.retrieve()
        .bodyToMono(object : ParameterizedTypeReference<Map<String, Response>>() {})
        .block()?.get(id)
}

fun main() {
    val client = SteamService()
    val results = client.search("gta")
    println(results)
    val response = client.appDetails(results[0])
    println(response)
}
