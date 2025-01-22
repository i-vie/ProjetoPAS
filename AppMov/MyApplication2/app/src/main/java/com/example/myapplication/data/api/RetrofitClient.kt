package com.example.myapplication.data.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    //codigo obtido através de: https://stackoverflow.com/questions/70246508/retrofit-okhttp-unexpected-end-of-stream
    //"When Android Studio's emulators running in Windows series OS (checked for 7 & 10) receive json-typed reply 
    //from server with retrofit it can with various probability loose 1 or 2 last symbols of the body when it is decoded to string, 
    //this symbols contain closing curly brackets and so such body could not be parsed to object by gson converter which results in 
    //throwing exception.
    //The idea of workaround I found is to add an interceptor to retrofit which would check the decoded to string body if its 
    //last symbols match those of valid json response and add them if they are missed." 
    val stringInterceptor = Interceptor { chain: Interceptor.Chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        val source = response.body?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer()
        var responseString = buffer?.clone()?.readString(Charset.forName("UTF-8"))
        if (responseString != null && responseString.isNotEmpty()) {
            if (!responseString.trimEnd().endsWith("}")) {
                responseString += "}"
            }
        }
        val contentType = response.body?.contentType()
        val body = ResponseBody.create(contentType, responseString ?: "")
        return@Interceptor response.newBuilder().body(body).build()

        }


    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
