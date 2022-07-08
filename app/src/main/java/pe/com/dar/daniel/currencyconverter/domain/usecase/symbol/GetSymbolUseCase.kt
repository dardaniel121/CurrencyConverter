package pe.com.dar.daniel.currencyconverter.domain.usecase.symbol


import kotlinx.coroutines.flow.Flow
import pe.com.dar.daniel.currencyconverter.core.Resource
import pe.com.dar.daniel.currencyconverter.domain.model.Symbol
import pe.com.dar.daniel.currencyconverter.domain.repository.SymbolRepository
import javax.inject.Inject

class GetSymbolUseCase @Inject constructor(private val symbolRepository: SymbolRepository) {

    suspend fun getSymbolsList(): Flow<Resource<List<Symbol>>> {
        return symbolRepository.getSymbol();
    }

}