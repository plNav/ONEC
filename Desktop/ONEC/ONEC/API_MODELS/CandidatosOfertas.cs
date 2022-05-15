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
    public class CandidatosOfertas
    {
        public string _id { get; set; }
        public string id_usuario { get; set; }

        public string id_oferta { get; set; }

        public string id_cv { get; set; }

        public static CandidatosOfertas candidatoCreado  {get; set;}

        public CandidatosOfertas()
        {

        }

        public CandidatosOfertas(string id_usuario, string id_oferta, string id_cv)
        {
            this.id_usuario = id_usuario;
            this.id_oferta = id_oferta;
            this.id_cv = id_cv;
        }

        //Añadir nuevo candidato a una oferta
        public static async Task<CandidatosOfertas> crearCandidatoOferta(CandidatosOfertas candidatosOfertas)
        {
            try
            {
                string url = $"{StaticResources.urlHead}candidatosOfertas";
                JObject values = new JObject

                {
                    {"id_usuario",candidatosOfertas.id_usuario },
                    {"id_oferta", candidatosOfertas.id_oferta },
                    {"id_cv", candidatosOfertas.id_cv }
                };
                HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

                HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

                if (httpResponse.IsSuccessStatusCode)
                {
                    string result = await httpResponse.Content.ReadAsStringAsync();
                    CandidatosOfertas co = JsonSerializer.Deserialize<CandidatosOfertas>(result);
                    candidatoCreado = co;
                    return co;
                }
                else return null;
            }
            catch (Exception e)
            {
                return null;
            }

        }

        //Obtener candidatos de una oferta
        public static async Task<List<CandidatosOfertas>> obtenerCandidatosOferta(string id)
        {
            string url = $"{StaticResources.urlHead}candidatosOfertas/oferta/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<CandidatosOfertas> lista = JsonSerializer.Deserialize<List<CandidatosOfertas>>(content);
                return lista;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener ofertas de un candidato
        public static async Task<List<CandidatosOfertas>> obtenerOfertasCandidato(string id)
        {
            string url = $"{StaticResources.urlHead}candidatosOfertas/candidato/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<CandidatosOfertas> lista = JsonSerializer.Deserialize<List<CandidatosOfertas>>(content);
                return lista;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Eliminar ofertas de un candidato
        public static async Task<bool> eliminarOfertasCandidato(string id)
        {
            string url = $"{StaticResources.urlHead}candidatosOfertas/candidato/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.DeleteAsync(url);
   
            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                return true;
            }else
            {
                return false;
            }
        }

        //Eliminar candidatos oferta
        public static async Task<bool> eliminarCandidatosOferta(string id)
        {
            string url = $"{StaticResources.urlHead}candidatosOfertas/oferta/{id}";
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

        //Eliminar candidato específico
        public static async Task<bool> eliminarCandidatoID(string id)
        {
            string url = $"{StaticResources.urlHead}candidatosOfertas/{id}";
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
