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
    class Visualizaciones
    {
        public string id_anuncio { get; set; }
        public string id_usuario { get; set; }

        public static List<Visualizaciones> visualizacionesUsuario;

        public Visualizaciones()
        {

        }

        public Visualizaciones(string id_anuncio, string id_usuario)
        {
            this.id_anuncio = id_anuncio;
            this.id_usuario = id_usuario;
        }

        //Añadir visualizacion
        public static async Task<bool> crearVisualizacion(Visualizaciones visualizacion)
        {
            try
            {
                string url = $"{StaticResources.urlHead}visualizacion";

                JObject values = new JObject
            {
                    { "id_anuncio", visualizacion.id_anuncio },
                    { "id_usuario", visualizacion.id_usuario }
               
            };

                HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

                HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

                if (httpResponse.IsSuccessStatusCode)
                {
                    string result = await httpResponse.Content.ReadAsStringAsync();
                    return true;
                }
                else return false;
            }
            catch (Exception e)
            {
                return false;
            }

        }

        //Obtener visualizaciones de usuario en un anuncio
        public static async Task<List<Visualizaciones>> obtenerVisualizacionesUsuarioAnuncio(string id_anuncio, string id_usuario)
        {
            string url = $"{StaticResources.urlHead}visualizacion/UsuarioEnAnuncio/{id_anuncio}/{id_usuario}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<Visualizaciones> vis = JsonSerializer.Deserialize<List<Visualizaciones>>(content);
                visualizacionesUsuario = vis;
                return vis;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }


        //Eliminar todas las visualizaciones de un anuncio
        public static async Task<bool> eliminarVisualizacionesAnuncio(string id)
        {
            string url = $"{StaticResources.urlHead}visualizacion/anuncios/{id}";
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
