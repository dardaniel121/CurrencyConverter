package pe.com.dar.daniel.currencyconverter.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pe.com.dar.daniel.currencyconverter.data.local.CurrencyDatabase
import pe.com.dar.daniel.currencyconverter.data.remote.CurrencyApi
import pe.com.dar.daniel.currencyconverter.data.remote.util.HeaderInterceptor
import pe.com.dar.daniel.currencyconverter.data.repository.RateRepositoryImpl
import pe.com.dar.daniel.currencyconverter.data.repository.SymbolRepositoryImpl
import pe.com.dar.daniel.currencyconverter.domain.repository.RateRepository
import pe.com.dar.daniel.currencyconverter.domain.repository.SymbolRepository
import pe.com.dar.daniel.currencyconverter.domain.usecase.symbol.GetRateUSeCase
import pe.com.dar.daniel.currencyconverter.domain.usecase.symbol.GetSymbolUseCase
import pe.com.dar.daniel.currencyconverter.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(headerInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideCurrencyApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): CurrencyApi =
        retrofitBuilder.client(okHttpClient).build().create(CurrencyApi::class.java)


    @Singleton
    @Provides
    fun provideCurrencyDatabase(app: Application): CurrencyDatabase {
        return Room.databaseBuilder(app, CurrencyDatabase::class.java, Constants.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(api: CurrencyApi): RateRepository =
        RateRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideSymbolRepository(api: CurrencyApi, db: CurrencyDatabase): SymbolRepository {
        return SymbolRepositoryImpl(api, db)
    }

    @Singleton
    @Provides
    fun provideSymbolUseCase(repository: SymbolRepository): GetSymbolUseCase {
        return GetSymbolUseCase(repository)
    }


    @Singleton
    @Provides
    fun provideRateUseCase(repository: RateRepository): GetRateUSeCase {
        return GetRateUSeCase(repository)
    }


}


