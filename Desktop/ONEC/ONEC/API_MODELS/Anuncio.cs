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
    class Anuncio
    {
        public string id_user { get; set; }
        public string categoria { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public float precio { get; set; }
        public bool precioPorHora { get; set; }
        public int numVecesVisto { get; set; }

        public static Anuncio anuncioCreado;
        public static Anuncio anuncioBuscado;

        public Anuncio()
        {

        }

        public Anuncio(string id_user, string categoria, string nombre, string descripcion, float precio, bool precioPorHora, int numVecesVisto)
        {
            this.id_user = id_user;
            this.categoria = categoria;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.precio = precio;
            this.precioPorHora = precioPorHora;
            this.numVecesVisto = numVecesVisto;
        }


        //Crear Anuncio
        public static async Task<bool> crearAnuncio(Anuncio anuncio)
        {
            try
            {
                string url = $"{StaticResources.urlHead}anuncio";

                JObject values = new JObject
            {
                    { "id_user", anuncio.id_user },
                    { "categoria", anuncio.categoria },
                    { "nombre", anuncio.nombre },
                    { "descripcion" , anuncio.descripcion },
                    { "precio", anuncio.precio },
                    { "precioPorHora", anuncio.precioPorHora },
                    { "numVecesVisto", anuncio.numVecesVisto }
            };

                HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

                HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

                if (httpResponse.IsSuccessStatusCode)
                {
                    string result = await httpResponse.Content.ReadAsStringAsync();
                    anuncioCreado = JsonSerializer.Deserialize<Anuncio>(result);
                    return true;
                }
                else return false;
            }
            catch (Exception e)
            {
                return false;
            }

        }

        //Obtener Anuncio por id
        public static async Task<Anuncio> obtenerAnuncioId(string id)
        {
            string url = $"{StaticResources.urlHead}anuncio/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                Anuncio an = JsonSerializer.Deserialize<Anuncio>(content);
                anuncioBuscado = an;
                return an;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener todos los anuncios de un usuario
        public static async Task<List<Anuncio>> obtenerAnunciosUsuario(string id)
        {
            string url = $"{StaticResources.urlHead}anuncio/usuario/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<Anuncio> Lan = JsonSerializer.Deserialize<List<Anuncio>>(content);
                return Lan;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }
    }
}
