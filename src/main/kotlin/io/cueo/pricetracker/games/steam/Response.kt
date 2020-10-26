package io.cueo.pricetracker.games.steam

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author mmayank
 */
data class Response(
    val success: Boolean,
    val data: App
)

data class App(
    @JsonProperty("is_free") val isFree: Boolean,
    @JsonProperty("price_overview") val priceOverview: PriceOverview
)

data class PriceOverview(
    val currency: String,
    @JsonProperty("discount_percent") val discountPercent: Int,
    val initial: Long,
    val final: Long
)
