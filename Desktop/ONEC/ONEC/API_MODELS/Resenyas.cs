using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace ONEC.API_MODELS
{
    class Resenyas
    {
        public string id_user { get; set; }
        public string id_anuncio { get; set; }
        public float puntuacion { get; set; }
        public string descripcion { get; set; }

        public static Resenyas resenyaCreada;
        public static List<Resenyas> resenyasAnuncio;
        public Resenyas()
        {

        }

        public Resenyas(string id_user, string id_anuncio, float puntuacion, string descripcion)
        {
            this.id_user = id_user;
            this.id_anuncio = id_anuncio;
            this.puntuacion = puntuacion;
            this.descripcion = descripcion;
        }

        //Crear nueva reseña

        public static async Task<bool> crearResenya(Resenyas resenya)
        {
            try
            {
                string url = $"{StaticResources.urlHead}review";

                JObject values = new JObject
            {
                    { "id_user", resenya.id_user},
                    { "id_anuncio", resenya.id_anuncio },
                    { "puntuacion",resenya.puntuacion },
                    { "descripcion", resenya.descripcion }
            };

                HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

                HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

                if (httpResponse.IsSuccessStatusCode)
                {
                    string result = await httpResponse.Content.ReadAsStringAsync();
                    resenyaCreada = JsonSerializer.Deserialize<Resenyas>(result);
                    return true;
                }
                else return false;
            }
            catch (Exception e)
            {
                return false;
            }

        }

        //Obtener reseñas de un anuncio
        public static async Task<List<Resenyas>> obtenerResenyasAnuncio(string id)
        {
            string url = $"{StaticResources.urlHead}review/anuncio/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<Resenyas> lista = JsonSerializer.Deserialize<List<Resenyas>>(content);
                resenyasAnuncio = lista;
                return lista;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener reseñas de un usuario
        public static async Task<List<Resenyas>> obtenerResenyasUsuario(string id)
        {
            string url = $"{StaticResources.urlHead}review/usuario/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<Resenyas> lista = JsonSerializer.Deserialize<List<Resenyas>>(content);
                resenyasAnuncio = lista;
                return lista;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener las reseñas que tiene un usuario en un anuncio específico
        public static async Task<List<Resenyas>> obtenerResenyasUsuarioAnuncio(string id_anuncio, string id_user)
        {
            string url = $"{StaticResources.urlHead}review/{id_anuncio}/{id_user}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<Resenyas> lista = JsonSerializer.Deserialize<List<Resenyas>>(content);
                resenyasAnuncio = lista;
                return lista;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener puntuación de reseñas
        public static async Task<float> obtenerPuntuacionAnuncio(string id)
        {
            string url = $"{StaticResources.urlHead}review/anuncio/puntuacion/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                float puntuacion = JsonSerializer.Deserialize<float>(content);
                return puntuacion;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Eliminar reseña
        public static async Task<bool> eliminarResenya(string id)
        {
            string url = $"{StaticResources.urlHead}review/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.DeleteAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                return true;
            }
            else
            {
                return false;
            }
        }

        //Eliminar reseñas de un anuncio
        //Eliminar reseña
        public static async Task<bool> eliminarResenyasAnuncio(string id)
        {
            string url = $"{StaticResources.urlHead}review/anuncio/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.DeleteAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
