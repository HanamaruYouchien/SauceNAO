package org.eu.sdsz.hanamaru.saucenao.process

import android.graphics.Bitmap
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url


val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://saucenao.com/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {
    @Multipart
    @POST
    fun uploadImage(
        @Url url: String,
        @Query("api_key") apiKey: String,
        @Query("output_type") outputType: Int,
        @Query("numres") numres: Int,
        @Query("minsim") minsim: Int,
        @Query("dbmask") dbmask: Int,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>
}

fun uploadImage(bitmap: Bitmap) {
    // Save bitmap to PNG
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
    val bitmapdata = bos.toByteArray()

    // Create RequestBody & MultipartBody.Part
    val requestFile = bitmapdata.toRequestBody("image/png".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("file", "image.png", requestFile)

    val service = retrofit.create(ApiService::class.java)
    val call = service.uploadImage(
        url = "search.php",
        apiKey = "22d61c4a6cfe2f3496c44ad287daa878614a4fa6",
        outputType = 2,
        numres = 1,
        minsim = 80,
        dbmask = 999,
        file = body
    )

    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                println("Response: $responseBody")
            } else {
                println("Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            t.printStackTrace()
        }
    })
}

fun search(){
    println("Search Start")

//    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.testimage)
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    uploadImage(bitmap)
}
