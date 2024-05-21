package org.eu.sdsz.hanamaru.saucenao.process

import android.graphics.Bitmap
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
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

fun uploadImage(bitmapdata : ByteArray) {
    // Save bitmap to PNG
//    val bos = ByteArrayOutputStream()
//    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
//    val bitmapdata = bos.toByteArray()

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

fun search(imageByteArray : ByteArray){
    println("Search Start")

//    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.testimage)
//    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    uploadImage(imageByteArray)
}

fun searchbyBitmap(bitmap: Bitmap) {
    println("Search by Bitmap Start")
    // Save bitmap to PNG
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
    val bitmapdata = bos.toByteArray()
    search(bitmapdata)
}

fun searchbyUrl(Url: String) {
    println("Search by Url Start")
    val exampleUrl = "http://123.57.89.45/dedfaf_posts/MC/skin/Murasame.png"
//    thread {
//        try {
////            val url = URL(Url)
//            val url = URL(exampleUrl)
//            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//            connection.doInput = true
//            connection.connect()
//            val input: InputStream = connection.inputStream
//            val bitmap = input.toString()
//        }
//    }
    val client = OkHttpClient()
//    val request = Request.Builder().url(Url).build()
    // USE EXAMPLE AT CURRENT
    val request = Request.Builder().url(exampleUrl).build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        val imageByte = response.body?.bytes()

        if (imageByte != null) {
            print(imageByte)
            search(imageByte)
        } else {
            print("imageByte is null")
        }
    }
}