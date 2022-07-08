package pe.com.dar.daniel.currencyconverter.presentation

sealed class CurrencyEvent {
    class Success(val resultText: String) : CurrencyEvent()
    class Failure(val errorText: String) : CurrencyEvent()
    object Loading : CurrencyEvent()
    object Empty : CurrencyEvent()
}
