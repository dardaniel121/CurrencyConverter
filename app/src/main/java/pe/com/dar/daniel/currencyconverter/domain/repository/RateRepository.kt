package pe.com.dar.daniel.currencyconverter.domain.repository

import pe.com.dar.daniel.currencyconverter.core.Resource
import pe.com.dar.daniel.currencyconverter.domain.model.Rate

interface RateRepository {
    suspend fun getRates(base: String): Resource<List<Rate>>

}