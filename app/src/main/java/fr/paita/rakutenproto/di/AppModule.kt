package fr.paita.rakutenproto.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.paita.rakutenproto.BuildConfig
import fr.paita.rakutenproto.di.interceptors.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val API_BASE_URL: String = BuildConfig.API_BASE_URL
    private const val HTTP_TIMEOUT: Int = 15 // seconds

    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    @JvmStatic
    @Provides
    @Singleton
    @Named("basic_client")
    fun provideOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .callTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(networkConnectionInterceptor)
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    @Named("retrofit")
    fun provideBasicRetrofitClient(@Named("basic_client") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}