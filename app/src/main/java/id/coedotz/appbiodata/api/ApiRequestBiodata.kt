package id.coedotz.appbiodata.api

import id.coedotz.appbiodata.model.ResponsModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRequestBiodata {

    @FormUrlEncoded
    @POST("insert.php")
    fun sendBiodata(
        @Field("nama") nama: String,
        @Field("usia") usia: String,
        @Field("domisili") domisili: String
    ): Call<ResponsModel>

    @GET("read.php")
    fun getBiodata(): Call<ResponsModel>

    @FormUrlEncoded
    @POST("update.php")
    fun updateData(
        @Field("id") id: String?,
        @Field("nama") nama: String,
        @Field("usia") usia: String,
        @Field("domisili") domisili: String
    ): Call<ResponsModel>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteData(@Field("id") id: String?): Call<ResponsModel>
}
