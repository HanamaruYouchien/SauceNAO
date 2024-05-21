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

val service = retrofit.create(ApiService::class.java)

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

fun createRequestByFile(apiKey: String, bitmapData: ByteArray) : Call<ResponseBody> {
    // Create RequestBody & MultipartBody.Part
    val requestFile = bitmapData.toRequestBody("image/png".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("file", "image.png", requestFile)

    return service.uploadImage(
        url = "search.php",
        apiKey = apiKey,
        outputType = 2,
        numResult = 1,
        minSimilarity = 80,
        dbMask = 999,
        file = body
    )
}

fun createRequestByUrl(apiKey: String, imageUrl: String) : Call<ResponseBody> {
    return service.uploadImagebyUrl(
        url = "search.php",
        apiKey = apiKey,
        outputType = 2,
        numResult = 1,
        minSimilarity = 80,
        dbMask = 999,
        imageUrl = imageUrl
    )
}


fun postRequest(request: Call<ResponseBody>) : String {
    var responseBody = ""

    try {
        val response = request.execute()
        if (response.isSuccessful) {
            responseBody = response.body()?.string().toString()
        } else {
            println("response is not successful")
            /* TODO throw response error here */
        }
    } catch (e: IOException) {
        println("response IOException")
    }
    println("end uploadImage")
    println("Response: $responseBody")
    return responseBody
}
// Primary Search Func
fun search(apiKey: String, imageByteArray: ByteArray) : JsonResult {
    val request = createRequestByFile(apiKey, imageByteArray)
    val responseJson = postRequest(request)
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

fun search(apiKey: String, url: String) : JsonResult {
    val request = createRequestByUrl(apiKey, url)
    val responseJson = postRequest(request)
    val result = processJson(responseJson)
    println(result)
    return result
}