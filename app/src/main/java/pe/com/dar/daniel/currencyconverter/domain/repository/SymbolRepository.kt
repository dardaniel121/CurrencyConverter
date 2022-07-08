package pe.com.dar.daniel.currencyconverter.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.com.dar.daniel.currencyconverter.core.Resource
import pe.com.dar.daniel.currencyconverter.domain.model.Symbol

interface SymbolRepository {

    suspend fun getSymbol(): Flow<Resource<List<Symbol>>>
 //   suspend fun getSymbolFromLocal(): Flow<Resource<List<Symbol>>>
  //  suspend fun saveSimBol(list: List<Symbol>)


}