package pe.com.dar.daniel.currencyconverter.presentation

import pe.com.dar.daniel.currencyconverter.domain.model.Symbol

data class SymbolState(
    val listSymbol: List<Symbol> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)