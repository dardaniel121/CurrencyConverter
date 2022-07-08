package pe.com.dar.daniel.currencyconverter.data.map

import pe.com.dar.daniel.currencyconverter.data.model.currency.Rates
import pe.com.dar.daniel.currencyconverter.domain.model.Rate
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties


fun Rates.asMap() = this::class.declaredMemberProperties
    .map {
        it as KProperty1<Rates, Any?>
        it.name to it.get(this).toString()
    }.toMap()


fun Rates.toListRatesCurrency(): List<Rate> = this.asMap()
    .entries.toList().map { Rate(it.key.uppercase(), it.value) }