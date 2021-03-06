package com.example.onec.Servicios

import android.util.Log
import com.example.onec.Models.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiServices {

    interface ApiServices {

        companion object {

            private var apiServices: ApiServices? = null

            fun getInstance(): ApiServices {
                /***
                 * modo
                 * 1 = Despliegue localHost
                 * 2 = Despliegue Heroku
                 * */
                    val modo = 2

                /***
                 * ipLocal = introducir la ip local de la máquina en la que está ejecutándose el servidor web
                 * */
                    val ipLocal : String = "192.168.0.20"


                /**Crear instancia*/
                if (apiServices == null) {
                    apiServices = Retrofit.Builder()
                        .baseUrl(if(modo == 2) "https://onec-nuevo.herokuapp.com/api/" else "http://$ipLocal:8081/api/")
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

        @GET("usuario/{id}")
        suspend fun obtenerUsuario(
            @Path("id") id : String
        ) : Response<UsuarioModel>

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

        @GET("cv/oferta/{id}/{habilidadesReq}")
        suspend fun buscarCVs(
            @Path("id") id : String,
            @Path("habilidadesReq") habilidadesReq : String
        ): Response<MutableList<CvModel>>

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

        @GET("anuncio/buscar/{campo}")
        suspend fun buscarAnuncio(
          @Path("campo") campo : String
        ) :Response<MutableList<AnuncioModel>>

        @GET("review/anuncio/puntuacion/{id}")
        suspend fun calcularPuntuacionAnuncio(
           @Path("id") id : String
        ) : Response<Float>

        @PUT("anuncio/{id}")
        suspend fun actualizarAnuncio(
            @Path("id") id : String,
            @Body anuncio: AnuncioPost
        ) : Response<AnuncioModel>

        @DELETE("anuncio/{id}")
        suspend fun borrarAnuncio(
            @Path("id") id: String
        ): Response<AnuncioModel>




        /****************Reseñas********************/
        @POST("review")
        suspend fun crearResenya(@Body resenya : ResenyaPost) : Response<ResenyaModel>

        @GET("review/anuncio/{id}")
        suspend fun obtenerResenyasAnuncio(
            @Path("id") id : String
        ): Response<MutableList<ResenyaModel>>

        @GET("review/{id}/{id_user}")
        suspend fun obtenerResenyasAnuncioUsuario(
            @Path("id") id : String,
            @Path("id_user") id_user : String
        ) : Response<List<ResenyaModel>>

        @DELETE("review/anuncio/{id}")
        suspend fun borrarResenyasAnuncio(
            @Path("id") id: String
        ): Response<Any>


        /**************Anuncios Guardados*******************/
        @POST("anunciosGuardados")
        suspend fun agregarAnuncioFavorito(
            @Body anuncio : AnunciosGuardadosPost
        ) : Response<AnunciosGuardadosModel>

        @GET("anunciosGuardados/usuario/{id}")
        suspend fun obtenerAnunciosFavUsuario(
            @Path("id") id : String
        ) : Response<MutableList<AnunciosGuardadosModel>>


       @DELETE("anunciosGuardados/{id}")
       suspend fun borrarAnuncioFavoritos(
           @Path("id") id : String
       ) : Response<Any>

       @DELETE("anunciosGuardados/deleteAll/{id}")
       suspend fun borrarAnunciosGuardadosIdAnuncio(
           @Path("id") id : String
       ) : Response<Any>


        /**************Visualizaciones*******************/
        @POST("visualizacion")
        suspend fun crearVisualizacion(
            @Body visualizacion : VisualizacionesPost
        ) : Response<VisualizacionesModel>

        @GET("visualizacion/UsuarioEnAnuncio/{id_anuncio}/{id_usuario}")
        suspend fun obtenerVisualizacionesUsuarioAnuncio(
            @Path("id_anuncio") id_anuncio : String,
            @Path("id_usuario") id_usuario : String
        ) : Response<MutableList<VisualizacionesModel>>

        @DELETE("visualizacion/anuncios/{id}")
        suspend fun eliminarVisualizacionesAnuncio(
            @Path("id") id : String
        ) : Response<Any>

        /************Candidatos Ofertas*******************/
        @POST("candidatosOfertas")
        suspend fun crearCandidatoOferta(
            @Body candidato : CandidatosOfertasPost
        ) : Response<CandidatosOfertasModel>

        @GET("candidatosOfertas/oferta/{id}")
        suspend fun obtenerCandidatosOferta(
            @Path("id") id : String
        ): Response<MutableList<CandidatosOfertasModel>>

        @GET("candidatosOfertas/candidato/{id}")
        suspend fun obtenerOfertasCandidato(
           @Path("id") id : String
        ): Response<MutableList<CandidatosOfertasModel>>

        @DELETE("candidatosOfertas/{id}")
        suspend fun eliminarCandidatosOfertasID(
            @Path("id") id : String
        ): Response<Any>

        @DELETE("candidatosOfertas/candidato/{id}")
        suspend fun eliminarOfertasCandidato(
            @Path("id") id : String
        ): Response<Any>

        @DELETE("candidatosOfertas/oferta/{id}")
        suspend fun eliminarCandidatosOferta(
            @Path("id") id : String
        ): Response<Any>


        /*********************Ofertas**********************/
        @POST("oferta")
        suspend fun crearOferta(
            @Body oferta : OfertaPost
        ) : Response<ModelOferta>

        @GET("oferta/usuario/{id}")
        suspend fun obtenerOfertasUsuario(
            @Path("id") id : String
        ): Response<MutableList<ModelOferta>>

        @DELETE("oferta/{id}")
        suspend fun eliminarOferta(
            @Path("id") id : String
        ): Response<Any>
    }
}