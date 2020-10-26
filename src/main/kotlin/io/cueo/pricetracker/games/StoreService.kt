package io.cueo.pricetracker.games

/**
 * @author mmayank
 */
interface StoreService {

    fun search(query: String) : List<String>

    fun appDetails(id: String) : Any?
}
