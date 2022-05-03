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
    public class AnunciosGuardados
    {
        public string id_anuncio { get; set; }
        public string id_user { get; set; }

        public static AnunciosGuardados anuncioGuardadoCreado;
        public static List<AnunciosGuardados> anunciosGuardadosUsuario;

        public AnunciosGuardados()
        {

        }

        public AnunciosGuardados(string id_anuncio, string id_user)
        {
            this.id_anuncio = id_anuncio;
            this.id_user = id_user;
        }

        //Crear nuevo AnuncioGuardado
        public static async Task<bool> crearAnuncioGuardado(AnunciosGuardados anuncioGuardado)
        {
            try
            {
                string url = $"{StaticResources.urlHead}anunciosGuardados";

                JObject values = new JObject
            {
                    { "id_anuncio", anuncioGuardado.id_anuncio },
                    { "id_user", anuncioGuardado.id_user }
                    
            };

                HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

                HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

                if (httpResponse.IsSuccessStatusCode)
                {
                    string result = await httpResponse.Content.ReadAsStringAsync();
                    anuncioGuardadoCreado= JsonSerializer.Deserialize<AnunciosGuardados>(result);
                    return true;
                }
                else return false;
            }
            catch (Exception e)
            {
                return false;
            }

        }

        //Obtener anuncios Guardados de un usuario
        public static async Task<List<AnunciosGuardados>> obtenerAnunciosGuardadosUsuarioID(string id)
        {
            string url = $"{StaticResources.urlHead}anunciosGuardados/usuario/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<AnunciosGuardados> anunciosUsu = JsonSerializer.Deserialize<List<AnunciosGuardados>>(content);
                anunciosGuardadosUsuario = anunciosUsu;
                return anunciosUsu;
            }
            else throw new HttpRequestException("Respuesta fallida en obtenerAnunciosGuardadosUsuarioID");
        }

        //Eliminar Anuncio Guardado
        public static async Task<bool> eliminarAnunciosGuardadosDeUnAnuncio(string id)
        {
            string url = $"{StaticResources.urlHead}anunciosGuardados/deleteAll/{id}";
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
