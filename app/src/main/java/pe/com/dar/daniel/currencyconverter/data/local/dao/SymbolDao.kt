package pe.com.dar.daniel.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.com.dar.daniel.currencyconverter.data.local.entities.SymbolEntity


@Dao
interface SymbolDao {

    @Query("SELECT * FROM symbol_table ORDER BY symbol ASC")
    suspend fun getAllSymbols(): List<SymbolEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymbols(symbols: List<SymbolEntity>)

    @Query("DELETE FROM symbol_table")
    suspend fun deleteAllSymbols()

}