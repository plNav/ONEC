package com.example.onec.Soporte

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import com.example.onec.Models.*
import java.io.File


class StaticVariables {
    companion object {



        /**
        * appModo : Boolean
        * true = Modo de App Empresario
        * false = Mode de App candidato
        * */
        var appModo : Boolean = false

        /**
         * fragmento : Int
         * 1 = Ofertas
         * 2 = Anuncios
         * 3 = CV
         * 4 = Perfil
         * */
        var fragmento = 1



        /**
        * fun clean() -> Sirve para Resetear valores en la aplicación, por si se cambia de modo, o se cierra sesión
        * */
        fun clean() {
            appModo = false
            fragmento = 1
        }

        /**
         *  Usuario
         * */

        var usuario: UsuarioModel? = null

        /**
         *  CV
         * */

        var cv : CvModel? = null



        /**
        * Variables estáticas de  creación de CV
        * */


        /**
        * imageUri : Uri?   -> Contiene el Uri de la Imagen que ha seleccionado de la galeria el Usuario tipo Estandar en su CV para no perderla al cambiar de Composable
        * */
        var imagen : File? = null
        var img : Bitmap? = null
        var imageUri : Uri? = null
        var pasoRegistro: String = "1"
        var nombreCv : String = ""
        var telefono : String = ""
        var ubicacion : String = ""
        var experiencia : Int = 0
        var titulo : String = ""
        var especialidad : String? = null
        var habilidades : MutableList<String> = mutableStateListOf()
        var habilidadesLow : MutableList<String> = mutableStateListOf()


        /**
         * Anuncios
         * */

        var anunciosUsuario : MutableList<AnuncioModel> = mutableStateListOf()
        var anunciosBuscados = false
        var anuncioSeleccionado : AnuncioModel? = null
        var puntuacionAnuncioSelect : Float? = null


        /**
         *  Anuncios Favoritos
         * */
        var anunciosFavoritos : MutableList<AnunciosGuardadosModel> = mutableStateListOf()
        var anuncioGuardadoSelect : AnunciosGuardadosModel? = null
        var anuncioFavSelect : AnuncioModel? = null
        var correoAnuncioFavSelect : String? = null
        var puntuacionAnuncioFavSelect : Float? = null
        var anunciosFavBuscados = false


        /**
         * Anuncios Buscados Empresario
         */
        var anunciosBuscadosEmpre: MutableList<AnuncioModel> = mutableStateListOf()
        var anuncioBuscadoSelect: AnuncioModel? = null
        var correoAnuncioBuscadoSelect : String? = null
        var puntuacionAnuncioBuscado : Float? = null
        var anuncioEmpreBuscado = false


        /**
         * Revisiones
         * */
        var revisionesAnuncioSelecc : MutableList<ResenyaModel> = mutableStateListOf()


        /**
         * Ofertas
         * */

        var ofertaSeleccionada : ModelOferta? = null


        /**
         * Candidatos Ofertas Guardados
         * */
        var candidatoGuardadoSeleccionado : CandidatosOfertasModel? = null
        var candidatoGuardadoSeleccionadoCV : CvModel? = null
    }
}