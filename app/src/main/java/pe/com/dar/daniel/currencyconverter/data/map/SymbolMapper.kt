package pe.com.dar.daniel.currencyconverter.data.map

import pe.com.dar.daniel.currencyconverter.data.local.entities.SymbolEntity
import pe.com.dar.daniel.currencyconverter.data.model.symbol.Symbols
import pe.com.dar.daniel.currencyconverter.domain.model.Symbol
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties


fun SymbolEntity.toSymbol(): Symbol {
    return Symbol(symbol = symbol, description = detail)
}

fun Symbol.toSymbolEntity(): SymbolEntity = SymbolEntity(symbol = symbol!!, detail = description!!)


fun Symbols.asMap() = this::class.declaredMemberProperties
    .map {
        it as KProperty1<Symbols, Any?>
        it.name to it.get(this).toString()
    }.toMap()

fun Symbols.toListSymbolEntity(): List<SymbolEntity> {
    return this.asMap().entries.toList().map { s ->
        SymbolEntity(s.key, s.value)
    }
}

/*
fun toListSymbol(symbolResponse: SymbolResponse): List<Symbol> {
    return symbolResponse.symbols.asMap().entries.map { Symbol(it.key, it.value) }
}*/
/*
class User(val age: Int, val name: String)

fun User.asMap() = this::class.declaredMemberProperties
    .map {
        it as KProperty1<User, Any?>
        it.name to it.get(this).toString()
    }.toMap()


fun main() {

    val us = User(20, "rocio")

    us.asMap().entries.map {
        it
    }

}*/
