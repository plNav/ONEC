package com.example.onec.Servicios

import android.util.Log
import com.example.onec.Models.CvModel
import com.example.onec.Models.CvPost
import com.example.onec.Models.UsuarioModel
import com.example.onec.Models.UsuarioPost
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

        @GET("cv/{id}")
        suspend fun actualizarCV(
            @Path("id") id : String,
            @Body cv : CvModel
        ): Response<CvModel>
    }
}