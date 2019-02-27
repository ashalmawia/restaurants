package com.ashalmawia.foursquare.data.network.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.foursquare.com/v2/"

private const val CLIENT_ID = "JCSC33RPWFA1URXSBJL1XFHC3PRJIWU02JFXY5040YLCZWUZ"
private const val CLIENT_SECRET = "45CALCYR5RQ3AJV35CLCN0BMHWDKTRF3HCJ3TUAGXSZWZRM1"
private const val API_VERSION = "20190227"

fun retrofit() = Retrofit.Builder()
    .client(okhttpClient())
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

private fun okhttpClient() = OkHttpClient.Builder()
    .addInterceptor { addAuthParams(it) }
    .build()

private fun addAuthParams(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val url = request.url().newBuilder()
        .addEncodedQueryParameter("client_id", CLIENT_ID)
        .addEncodedQueryParameter("client_secret", CLIENT_SECRET)
        .addEncodedQueryParameter("v", API_VERSION)
        .build()
    val newRequest = request.newBuilder()
        .url(url)
        .build()
    Log.d("asdf", url.toString())
    return chain.proceed(newRequest)
}