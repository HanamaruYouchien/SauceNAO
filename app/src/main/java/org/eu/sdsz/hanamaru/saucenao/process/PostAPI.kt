package org.eu.sdsz.hanamaru.saucenao.process

import android.graphics.Bitmap
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import org.eu.sdsz.hanamaru.saucenao.data.JsonResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
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

    @GET
    fun uploadImagebyUrl(
        @Url url: String,
        @Query("api_key") apiKey: String,
        @Query("output_type") outputType: Int,
        @Query("numres") numResult: Int,
        @Query("minsim") minSimilarity: Int,
        @Query("dbmask") dbMask: Int,
        @Query("url") imageUrl: String
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

fun uploadImagebyUrl(apiKey: String, imageUrl: String) : String {
    // Create RequestBody & MultipartBody.Part

    val service = retrofit.create(ApiService::class.java)
    val call = service.uploadImagebyUrl(
        url = "search.php",
        apiKey = apiKey,
        outputType = 2,
        numResult = 1,
        minSimilarity = 80,
        dbMask = 999,
        imageUrl = imageUrl
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

fun search(apiKey: String, Url: String) : JsonResult {
    val responseJson = uploadImagebyUrl(apiKey, Url)
    val result = processJson(responseJson)
    println(result)
    return result
}