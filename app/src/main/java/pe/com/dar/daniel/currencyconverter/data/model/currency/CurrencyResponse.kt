package pe.com.dar.daniel.currencyconverter.data.model.currency

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)