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
import org.eu.sdsz.hanamaru.saucenao.data.JsonResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread


val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
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
        @Query("numres") numResult: Int,
        @Query("minsim") minSimilarity: Int,
        @Query("dbmask") dbMask: Int,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>
}

fun uploadImage(apiKey: String, bitmapData : ByteArray) : String {
    // Create RequestBody & MultipartBody.Part
    val requestFile = bitmapData.toRequestBody("image/png".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("file", "image.png", requestFile)

    val service = retrofit.create(ApiService::class.java)
    val call = service.uploadImage(
        url = "search.php",
        apiKey = apiKey,
        outputType = 2,
        numResult = 1,
        minSimilarity = 80,
        dbMask = 999,
        file = body
    )

    var responseBody = ""
    val latch = CountDownLatch(1)

    thread {
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                responseBody = response.body()?.string().toString()
            } else {
                println("response is not successful")
                /* TODO throw response error here */
            }
        } catch (e: IOException) {
            println("response IOException")
        } finally {
            latch.countDown()
        }
    }
    latch.await()
    println("end uploadImage")
    println("Response: $responseBody")
    return responseBody
}

// Primary Search Func
fun search(apiKey: String, imageByteArray: ByteArray) : JsonResult {
    val responseJson = uploadImage(apiKey, imageByteArray)
    val result = processJson(responseJson)
    return result
}

fun search(apiKey: String, bitmap: Bitmap) : JsonResult {
    // Save bitmap to PNG
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
    val bitmapData = bos.toByteArray()
    return search(apiKey, bitmapData)
}

fun search(Url: String) : JsonResult? {
    println("Search by Url Start")
    val exampleUrl = "http://( ﾟ∀。)/dedfaf_posts/MC/skin/Murasame.png"
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
            return search("", imageByte)
        } else {
            print("imageByte is null")
            return null
        }
    }
}