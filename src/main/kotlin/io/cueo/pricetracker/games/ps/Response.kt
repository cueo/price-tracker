package io.cueo.pricetracker.games.ps

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author mmayank
 */
data class Response(
    val name: String,
    @JsonProperty("default_sku") val priceDetails: PriceDetails
)

data class PriceDetails(
    val price: Long,
    @JsonProperty("display_price") val displayPrice: String,
    @JsonProperty("rewards") val discounts: List<Discount>
)

data class Discount(
    @JsonProperty("discount") val percent: Int,
    val price: Long,
    @JsonProperty("display_price") val displayPrice: String
)
