package pe.com.dar.daniel.currencyconverter.data.repository

import android.util.Log
import com.google.gson.Gson
import pe.com.dar.daniel.currencyconverter.core.Resource
import pe.com.dar.daniel.currencyconverter.data.map.toListRatesCurrency
import pe.com.dar.daniel.currencyconverter.data.remote.CurrencyApi
import pe.com.dar.daniel.currencyconverter.domain.model.Rate
import pe.com.dar.daniel.currencyconverter.domain.repository.RateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateRepositoryImpl @Inject constructor(private val api: CurrencyApi) :
    RateRepository {
    override suspend fun getRates(base: String): Resource<List<Rate>> {
        return try {
            Log.d("4ds base ", base)

            val response = api.getRates(base)
            val result = response.body()
            Log.d("4ds respo  ", Gson().toJson(result))
            if (response.isSuccessful && result != null) {
                Log.d("4ds 2  ", Gson().toJson(result.rates.toListRatesCurrency()))
                Resource.Success(result.rates.toListRatesCurrency())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occured")
        }
    }

}

