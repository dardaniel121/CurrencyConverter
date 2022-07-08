package pe.com.dar.daniel.currencyconverter.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import pe.com.dar.daniel.currencyconverter.core.Resource
import pe.com.dar.daniel.currencyconverter.data.local.CurrencyDatabase
import pe.com.dar.daniel.currencyconverter.data.map.toListSymbolEntity
import pe.com.dar.daniel.currencyconverter.data.map.toSymbol
import pe.com.dar.daniel.currencyconverter.data.remote.CurrencyApi
import pe.com.dar.daniel.currencyconverter.domain.model.Symbol
import pe.com.dar.daniel.currencyconverter.domain.repository.SymbolRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SymbolRepositoryImpl @Inject constructor(
    private val api: CurrencyApi,
    private val db: CurrencyDatabase
) : SymbolRepository {
    override suspend fun getSymbol(): Flow<Resource<List<Symbol>>> {
        return withContext(Dispatchers.IO) {
            flow {
                emit(Resource.Loading())

                val localSymbol = db.symbolDao.getAllSymbols()
                emit(Resource.Success(localSymbol.map { it.toSymbol() }))

                if (localSymbol.isNotEmpty()) {
                    emit(Resource.Loading(false))
                    return@flow
                }

                val remoteSymbol = try {
                    api.getSymbols()
                } catch (e: HttpException) {
                    emit(Resource.Error(""))
                    null
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Error: ${e.message}"))
                    null
                }
                remoteSymbol.let { symbols ->
                    val result = symbols!!.body()!!.symbols.toListSymbolEntity()
                    Log.d("4ds e.e ", Gson().toJson(result))
                    db.symbolDao.insertSymbols(result)
                    emit(Resource.Success(result.map { it.toSymbol() }))
                    emit(Resource.Loading(false))
                }
            }
        }
    }
/*
    override suspend fun getSymbolFromLocal(): Flow<Resource<List<Symbol>>> {
        return withContext(Dispatchers.IO) {
            flow {
                emit(Resource.Loading())
                try {
                    val symbols = db.symbolDao.getAllSymbols()
                    emit(Resource.Success(symbols.map { it.toSymbol() }))
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Error: ${e.message}"))
                }
            }
        }
    }

    override suspend fun saveSimBol(list: List<Symbol>) {
        db.symbolDao.insertSymbols(list.map { s -> s.toSymbolEntity() })
    }
*/
}


/*

  override suspend fun getSymbol(): Resource<List<Symbol>> {
        TODO("Not yet implemented")
    }



 */