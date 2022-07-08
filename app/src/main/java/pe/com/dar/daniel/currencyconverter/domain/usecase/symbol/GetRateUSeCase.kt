package pe.com.dar.daniel.currencyconverter.domain.usecase.symbol

import pe.com.dar.daniel.currencyconverter.core.Resource
import pe.com.dar.daniel.currencyconverter.domain.model.Rate
import pe.com.dar.daniel.currencyconverter.domain.repository.RateRepository
import javax.inject.Inject

class GetRateUSeCase @Inject constructor(private val rateRepository: RateRepository) {

    suspend fun getRateList(base: String): Resource<List<Rate>> {
        return rateRepository.getRates(base)
    }

}