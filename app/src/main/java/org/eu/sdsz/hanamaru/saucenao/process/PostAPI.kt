package org.eu.sdsz.hanamaru.saucenao.process

import android.graphics.Bitmap
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import org.eu.sdsz.hanamaru.saucenao.data.JsonResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://saucenao.com/")
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

val service: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @Multipart
    @POST
    fun uploadImageByFile(
        @Url url: String,
        @Query("api_key") apiKey: String,
        @Query("output_type") outputType: Int,
        @Query("numres") numResult: Int,
        @Query("minsim") minSimilarity: Int,
        @Query("dbmask") dbMask: Int,
        @Part file: MultipartBody.Part
    ): Call<JsonResult>

    @GET
    fun uploadImageByUrl(
        @Url url: String,
        @Query("api_key") apiKey: String,
        @Query("output_type") outputType: Int,
        @Query("numres") numResult: Int,
        @Query("minsim") minSimilarity: Int,
        @Query("dbmask") dbMask: Int,
        @Query("url") imageUrl: String
    ): Call<JsonResult>
}

fun createRequestByFile(apiKey: String, bitmapData: ByteArray) : Call<JsonResult> {
    // Create RequestBody & MultipartBody.Part
    val requestFile = bitmapData.toRequestBody("image/png".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("file", "image.png", requestFile)

    return service.uploadImageByFile(
        url = "search.php",
        apiKey = apiKey,
        outputType = 2,
        numResult = 8,
        minSimilarity = 80,
        dbMask = 999,
        file = body
    )
}

fun createRequestByUrl(apiKey: String, imageUrl: String) : Call<JsonResult> {
    return service.uploadImageByUrl(
        url = "search.php",
        apiKey = apiKey,
        outputType = 2,
        numResult = 8,
        minSimilarity = 80,
        dbMask = 999,
        imageUrl = imageUrl
    )
}

fun postRequest(request: Call<JsonResult>) : JsonResult? {
    try {
        val responseBody = request.execute().body()
        return responseBody
    } catch (e: IOException) {
        return null
    }
}

// Primary Search Func
fun search(apiKey: String, imageByteArray: ByteArray) : JsonResult? {
    val request = createRequestByFile(apiKey, imageByteArray)
    val responseJson = postRequest(request)
    return responseJson
}

fun search(apiKey: String, bitmap: Bitmap) : JsonResult? {
    // Save bitmap to PNG
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
    val bitmapData = bos.toByteArray()
    return search(apiKey, bitmapData)
}

fun search(apiKey: String, url: String) : JsonResult? {
    val request = createRequestByUrl(apiKey, url)
    val responseJson = postRequest(request)
    return responseJson
}