package pe.com.dar.daniel.currencyconverter.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pe.com.dar.daniel.currencyconverter.core.Resource
import pe.com.dar.daniel.currencyconverter.domain.usecase.symbol.GetRateUSeCase
import pe.com.dar.daniel.currencyconverter.domain.usecase.symbol.GetSymbolUseCase
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val useCase: GetSymbolUseCase,
    private val rateUSeCase: GetRateUSeCase
) : ViewModel() {

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    private val _symbol = MutableStateFlow(SymbolState())
    val symbol: StateFlow<SymbolState> = _symbol.asStateFlow()

    private var symbolJob: Job? = null

    fun showSymbols() {
        symbolJob?.cancel()
        symbolJob = viewModelScope.launch {
            delay(500L)
            useCase.getSymbolsList().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _symbol.value = symbol.value.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        _symbol.value = symbol.value.copy(
                            listSymbol = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _symbol.value = symbol.value.copy(error = result.msg ?: "Unknown error")
                    }
                }
            }.launchIn(this)
        }
    }


    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("not a valid amount")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = CurrencyEvent.Loading

            when (val ratesResponse = rateUSeCase.getRateList(fromCurrency)) {

                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.msg!!)
                is Resource.Success -> {
                    val rate = ratesResponse.data!!.find { it.currency == toCurrency }
                    if (rate == null) {
                        _conversion.value = CurrencyEvent.Failure("UnExpected error")
                    } else {
                        val convertedCurrency = round(fromAmount * rate.rate.toDouble() * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
                else -> {}
            }
        }
    }


}