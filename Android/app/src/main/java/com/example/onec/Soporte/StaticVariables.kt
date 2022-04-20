package com.example.onec.Soporte

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import com.example.onec.Models.AnuncioModel
import com.example.onec.Models.CvModel
import com.example.onec.Models.UsuarioModel
import java.io.File


class StaticVariables {
    companion object {

        /**
        * fragmento : Int
        * 1 = Ofertas
        * 2 = Anuncios
        * 3 = CV
        * 4 = Perfil
        * */
        var fragmento = 1



        /**
        * appModo : Boolean
        * true = Modo de App Empresario
        * false = Mode de App candidato
        * */
        var appModo : Boolean = false



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


        /*
        * imageUri : Uri?   -> Contiene el Uri de la Imagen que ha seleccionado de la galeria el Usuario tipo Estandar en su CV para no perderla al cambiar de Composable
        * */
        var imagen : File? = null
        var imageUri : Uri? = null
        var pasoRegistro: String = "1"
        var nombreCv : String = ""
        var telefono : String = ""
        var ubicacion : String = ""
        var experiencia : Int = 0
        var titulo : String = ""
        var especialidad : String? = null
        var habilidades : MutableList<String> = mutableStateListOf()


        /**
         * Anuncios
         * */

        var anunciosUsuario : MutableList<AnuncioModel> = mutableStateListOf()
        var anunciosBuscados = false
    }
}