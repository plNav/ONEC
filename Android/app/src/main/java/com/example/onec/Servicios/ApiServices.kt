package com.example.onec.Servicios

import android.util.Log
import com.example.onec.Models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServices {

    interface ApiServices {

        companion object {

            private var apiServices: ApiServices? = null

            fun getInstance(): ApiServices {
                if (apiServices == null) {
                    apiServices = Retrofit.Builder()
                        .baseUrl("http://192.168.0.19:8081/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiServices::class.java)

                    Log.e("Conectado", "Conectado a la API")
                }
                return apiServices!!
            }
        }


        /*********************RUTAS**********************/

        @Headers("Accept: Application/json")


        /********************USUARIOS**********************/

        @GET("usuario/validar/{email}/{passw}")
        suspend fun validarUsuario(
            @Path("email") email : String,
            @Path("passw") passw : String
        ) : Response<List<UsuarioModel>>

        @GET("usuario/email/{email}")
        suspend fun checkEmail(
            @Path("email") email : String
        ) : Response<Boolean>

        @GET("usuario/resetPass/{email}")
        suspend fun resetPass(
            @Path("email") email: String
        ) : Response<UsuarioModel>

        @POST("usuario")
        suspend fun registrar(@Body usuarioPost: UsuarioPost) : Response<UsuarioModel>

        @PUT("usuario/{id}")
        suspend fun actualizarUsuario(@Path(value = "id") id : String, @Body usuario : UsuarioPost) : Response<UsuarioModel>



        /********************CV**********************/

        @POST("cv")
        suspend fun crearCV(@Body cv: CvPost) : Response<CvModel>

        @GET("cv/{id}")
        suspend fun obtenerCVEspecifico(
            @Path("id") id : String
        ): Response<CvModel>

        @GET("cv/usuario/{id}")
        suspend fun obtenerCvUsuario(
            @Path("id") id : String
        ): Response<CvModel>

        @PUT("cv/{id}")
        suspend fun actualizarCV(
            @Path("id") id : String,
            @Body cv : CvPost
        ): Response<CvModel>

        @Multipart
        @POST("cv/upload/{id}")
        fun postImage(
            @Path("id") id:String,
            @Part imagen:MultipartBody.Part
        ): Response<Any>


        /****************Anuncio*******************/

        @POST("anuncio")
        suspend fun crearAnuncio(@Body anuncio : AnuncioPost) : Response<AnuncioModel>

        @GET("anuncio")
        suspend fun obtenerAnuncios() : Response<List<AnuncioModel>>

        @GET("anuncio/{id}")
        suspend fun obtenerAnuncio(
            @Path("id") id : String
        ): Response<AnuncioModel>

        @GET("anuncio/usuario/{id}")
        suspend fun obtenerAnunciosUsuario(
            @Path("id") id : String
        ) : Response<List<AnuncioModel>>

        @PUT("anuncio/{id}")
        suspend fun actualizarAnuncio(
            @Path("id") id : String,
            @Body anuncio: AnuncioPost
        ) : Response<AnuncioModel>

        @DELETE("anuncio/{id}")
        suspend fun borrarAnuncio(
            @Path("id") id: String
        ): Response<AnuncioModel>

    }
}