package id.coedotz.appbiodata.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retroserver {
    private const val base_url = "https://proyek.luckytruedev.com/johancrud/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}
