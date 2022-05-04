using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace ONEC.API_MODELS
{
    public class Usuario
    {
         /********************************
         *          Variables            *
         ********************************/
        public string _id { get; set; }
        public string email { get; set; }
        public string password { get; set; }

        public static Usuario usuarioActual;

         /********************************
         *          Constructor          *
         ********************************/
        public Usuario(string email, string password)
        {
            this.email = email;
            this.password = password;
        }

         /*******************************
         *          Funciones           *
         *******************************/


        //Crear a un Usuario
        public static async Task<bool> crearUsuario(Usuario usuario)
        {
            string url = $"{StaticResources.urlHead}usuario";

            string passwCifrado = Encrypt.GetSHA256(usuario.password);

            JObject values = new JObject
            {
                { "email", usuario.email },
                { "password", passwCifrado }
            };

            HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

            HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

            if (httpResponse.IsSuccessStatusCode)
            {
                string result = await httpResponse.Content.ReadAsStringAsync();
                usuarioActual = JsonSerializer.Deserialize<Usuario>(result);
                return true;
            }
            else return false;

        }

        //Comprobar que un email introducido existe
        public static async Task<bool> comprobarMail(string email)
        {
            string url = $"{StaticResources.urlHead}usuario/email/{email}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                return JsonSerializer.Deserialize<bool>(content);
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener todos los usuarios
        public static async Task<Usuario> obtenerUsuarios()
        {
            string url = $"{StaticResources.urlHead}usuario";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                return JsonSerializer.Deserialize<Usuario>(content);
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener un Usuario por ID
        public static async Task<Usuario> obtenerUsuarioId(string id)
        {
            string url = $"{StaticResources.urlHead}usuario/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                return JsonSerializer.Deserialize<Usuario>(content);
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }


        //Actualizar Usuario
        public static async Task<bool> actualizarUsuario(Usuario usuario)
        {
            string url = $"{StaticResources.urlHead}usuario/{usuarioActual._id}";
            JObject values = new JObject
            {
                { "email", usuario.email },
                { "password", usuario.password }


            };

            HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

            HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

            if (httpResponse.IsSuccessStatusCode)
            {
                string result = await httpResponse.Content.ReadAsStringAsync();
                usuarioActual.email = usuario.email;
                usuarioActual.password = usuario.password;
                return true;
            }
            else return false;
        }

        //Comprobar Login Usuario
        public static async Task<bool> loguear(string email, string password)
        {
            string passwCifrado = Encrypt.GetSHA256(password);
            string url = $"{StaticResources.urlHead}usuario/validar/{email}/{passwCifrado}";

            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();

                List<Usuario> usuarios = JsonSerializer.Deserialize<List<Usuario>>(content);
                usuarioActual = usuarios[0];
                return true;
            }
            else return false;
        }

        //Cambio de contraseña
        public static async Task<Usuario> resetPass(string email)
        {
            string url = $"{StaticResources.urlHead}usuario/resetPass/{email}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                return JsonSerializer.Deserialize<Usuario>(content);
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Borrar Usuario
        public static async Task<bool> borrarUsuario()
        {
            string url = $"{StaticResources.urlHead}usuario/{usuarioActual._id}";

            HttpResponseMessage httpResponse = await StaticResources.httpClient.DeleteAsync(url);

            if (httpResponse.IsSuccessStatusCode) return true;
            else return false;
        }
    }
}
