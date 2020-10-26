package io.cueo.pricetracker.games.ps

/**
 * @author mmayank
 */
data class SearchResult(
    val data: Data
)

data class Data(
    val relationships: Results
)

data class Results(
    val children: Apps
)

data class Apps(
    val data: List<App>
)

data class App(
    val id: String,
    val type: String
)
