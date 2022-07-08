package pe.com.dar.daniel.currencyconverter.data.remote

import pe.com.dar.daniel.currencyconverter.data.model.currency.CurrencyResponse
import pe.com.dar.daniel.currencyconverter.data.model.symbol.SymbolResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {


    @GET("latest")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>

    @GET("symbols")
    suspend fun getSymbols(): Response<SymbolResponse>
}