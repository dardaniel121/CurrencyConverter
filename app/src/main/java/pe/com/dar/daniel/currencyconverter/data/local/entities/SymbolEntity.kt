package pe.com.dar.daniel.currencyconverter.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "symbol_table")
data class SymbolEntity(
    /*  @PrimaryKey(autoGenerate = true)
      @ColumnInfo(name = "id") val id: Int = 0,*/

    @PrimaryKey
    @ColumnInfo(name = "symbol") val symbol: String,

    @ColumnInfo(name = "detail") val detail: String

)
