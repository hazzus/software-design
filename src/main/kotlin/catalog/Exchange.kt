package catalog

object Exchange {
    private const val RUB_c = 1.0
    private const val EUR_c = 84.0
    private const val USD_c = 75.0
    fun fromString(name: String?): Currency {
        return when (name) {
            "RUB" -> Currency.RUB
            "EUR" -> Currency.EUR
            "USD" -> Currency.USD
            else -> throw RuntimeException("Invalid Currency")
        }
    }

    fun convertTo(value: Double, to: Currency?): Double {
        return when (to) {
            Currency.RUB -> value / RUB_c
            Currency.EUR -> value / EUR_c
            Currency.USD -> value / USD_c
            else -> throw RuntimeException("Invalid Currency")
        }
    }

    fun convertFrom(value: Double, from: Currency?): Double {
        return when (from) {
            Currency.RUB -> value * RUB_c
            Currency.EUR -> value * EUR_c
            Currency.USD -> value * USD_c
            else -> throw RuntimeException("Invalid Currency")
        }
    }

    enum class Currency {
        RUB, EUR, USD
    }
}
