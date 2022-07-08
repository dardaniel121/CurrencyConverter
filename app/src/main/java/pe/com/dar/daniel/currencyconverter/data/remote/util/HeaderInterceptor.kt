package pe.com.dar.daniel.currencyconverter.data.remote.util

import okhttp3.Interceptor
import okhttp3.Response
import pe.com.dar.daniel.currencyconverter.util.Constants
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = Constants.API_KEY

        var request = chain.request()
        request = request.newBuilder()
            .header("apikey", token)
            .build()
        return chain.proceed(request)
    }


}