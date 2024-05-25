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
import org.eu.sdsz.hanamaru.saucenao.data.DBMask
import org.eu.sdsz.hanamaru.saucenao.data.SaucenaoResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

class SauceNAO {
    companion object {
        const val STATUS_OK = 0
        const val STATUS_ANONYMOUS_ACCOUNT = -1
        const val STATUS_URL_NOT_USABLE = -3
        const val STATUS_EMPTY_IMAGE = -4

        const val DEDUPE_NO = 0
        const val DEDUPE_BOORU = 1
        const val DEDUPE_ALL = 2

        const val HIDE_NO = 0
        const val HIDE_EXPECTED = 1
        const val HIDE_EXPECTED_AND_SUSPECTED = 2
        const val HIDE_ALL_BUT_SAFE = 3

        private val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        private val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://saucenao.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        private val service: ApiService = retrofit.create(ApiService::class.java)

        private interface ApiService {
            @Multipart
            @POST
            fun uploadImageByFile(
                @Url url: String,
                @Query("api_key") apiKey: String,
                @Query("output_type") outputType: Int,
                @Query("numres") numResult: Int?,
                @Query("dbmask") dbMask: Long?,
                @Query("dedupe") deDupe: Int?,
                @Query("hide") hide: Int?,
                @Part file: MultipartBody.Part
            ): Call<SaucenaoResult>

            @GET
            fun uploadImageByUrl(
                @Url url: String,
                @Query("api_key") apiKey: String,
                @Query("output_type") outputType: Int,
                @Query("numres") numResult: Int?,
                @Query("dbmask") dbMask: Long?,
                @Query("dedupe") deDupe: Int?,
                @Query("hide") hide: Int?,
                @Query("url") imageUrl: String
            ): Call<SaucenaoResult>
        }

        private fun createRequestByFile(apiKey: String, bitmapData: ByteArray, dbMask: Long?, numResult: Int?, deDupe: Int?, hide: Int?) : Call<SaucenaoResult> {
            // Create RequestBody & MultipartBody.Part
            val requestFile = bitmapData.toRequestBody("image/png".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", "image.png", requestFile)

            return service.uploadImageByFile(
                url = "search.php",
                apiKey = apiKey,
                outputType = 2,
                numResult = numResult,
                dbMask = dbMask,
                deDupe = deDupe,
                hide = hide,
                file = body
            )
        }

        private fun createRequestByUrl(apiKey: String, imageUrl: String, dbMask: Long?, numResult: Int?, deDupe: Int?, hide: Int?) : Call<SaucenaoResult> {
            return service.uploadImageByUrl(
                url = "search.php",
                apiKey = apiKey,
                outputType = 2,
                numResult = numResult,
                dbMask = dbMask,
                deDupe = deDupe,
                hide = hide,
                imageUrl = imageUrl
            )
        }

        private fun postRequest(request: Call<SaucenaoResult>) : SaucenaoResult? {
            try {
                val responseBody = request.execute().body()
                return responseBody
            } catch (e: IOException) {
                return null
            }
        }

        // Primary Search Func
        fun search(apiKey: String, imageByteArray: ByteArray, dbMask: DBMask? = null, numResult: Int? = null, deDupe: Int? = null, hide: Int? = null) : SaucenaoResult? {
            val request = createRequestByFile(apiKey, imageByteArray, dbMask?.value, numResult, deDupe, hide)
            val responseJson = postRequest(request)
            return responseJson
        }

        fun search(apiKey: String, bitmap: Bitmap, dbMask: DBMask?, numResult: Int? = null, deDupe: Int? = null, hide: Int? = null) : SaucenaoResult? {
            // Save bitmap to PNG
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bitmapData = bos.toByteArray()
            return search(apiKey, bitmapData, dbMask, numResult, deDupe, hide)
        }

        fun search(apiKey: String, url: String, dbMask: DBMask? = null, numResult: Int? = null, deDupe: Int? = null, hide: Int? = null) : SaucenaoResult? {
            val request = createRequestByUrl(apiKey, url, dbMask?.value, numResult, deDupe, hide)
            val responseJson = postRequest(request)
            return responseJson
        }
    }
}