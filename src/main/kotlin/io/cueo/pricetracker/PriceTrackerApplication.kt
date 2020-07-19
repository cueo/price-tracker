package io.cueo.pricetracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PriceTrackerApplication

fun main(args: Array<String>) {
    runApplication<PriceTrackerApplication>(*args)
}
