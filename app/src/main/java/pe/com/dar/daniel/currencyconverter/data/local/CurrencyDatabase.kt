package pe.com.dar.daniel.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.com.dar.daniel.currencyconverter.data.local.dao.SymbolDao
import pe.com.dar.daniel.currencyconverter.data.local.entities.SymbolEntity


@Database(
    entities = [SymbolEntity::class],
    version = 1
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract val symbolDao: SymbolDao

}