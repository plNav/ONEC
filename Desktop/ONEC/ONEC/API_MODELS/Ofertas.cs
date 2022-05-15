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
   public class Ofertas
    {
        public string _id { get; set; }
        public string id_user { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public int experiencia { get; set; }
        public string titulo { get; set; }
        public string especialidad { get; set; }
        public List<string> habilidades { get; set; }
        public List<string> habilidadesLow { get; set; }
        public bool habilidadesReq { get; set; }

        public static Ofertas ofertaCreada;
        public static Ofertas ofertaBuscadaId;
        public static List<Ofertas> ofertasUsuario;

        public Ofertas()
        {

        }

        public Ofertas(string id_user, string nombre, string descripcion, int experiencia, string titulo, string especialidad, List<string>habilidades, List<string> habilidadesLow, bool habilidadesReq)
        {
            this.id_user = id_user;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.experiencia = experiencia;
            this.titulo = titulo;
            this.especialidad = especialidad;
            this.habilidades = habilidades;
            this.habilidadesLow = habilidadesLow;
            this.habilidadesReq = habilidadesReq;
        }

        //Crear Oferta
        public static async Task<bool> crearOferta(Ofertas oferta)
        {
            try
            {
                string url = $"{StaticResources.urlHead}oferta";

                JObject habilidades = new JObject();
                habilidades["habilidades"] = JToken.FromObject(oferta.habilidades);


                JObject jsonHabilidadesLow = new JObject();
                jsonHabilidadesLow["habilidadesLow"] = JToken.FromObject(oferta.habilidadesLow);

                JObject values = new JObject
            {
                { "id_user", oferta.id_user},
                { "nombre", oferta.nombre},
                { "descripcion", oferta.descripcion},
                { "experiencia", oferta.experiencia},
                { "titulo", oferta.titulo },
                { "especialidad", oferta.especialidad },
                { "habilidades", habilidades["habilidades"]},
                { "habilidadesLow", jsonHabilidadesLow["habilidadesLow"]},
                { "habilidadesReq", oferta.habilidadesReq},
            };

                HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

                HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

                if (httpResponse.IsSuccessStatusCode)
                {
                    string result = await httpResponse.Content.ReadAsStringAsync();
                    ofertaCreada = JsonSerializer.Deserialize<Ofertas>(result);
                    return true;
                }
                else return false;
            }
            catch (Exception e)
            {
                return false;
            }

        }

        //Obtener oferta por id
        public static async Task<Ofertas> obtenerOfertaId(string id)
        {
            string url = $"{StaticResources.urlHead}oferta/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                Ofertas oferta = JsonSerializer.Deserialize<Ofertas>(content);
                ofertaBuscadaId = oferta;
                return oferta;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Obtener ofertas de un usuario
        public static async Task<List<Ofertas>> obtenerOfertasUsuario(string id)
        {
            string url = $"{StaticResources.urlHead}oferta/usuario/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<Ofertas> ofertas = JsonSerializer.Deserialize<List<Ofertas>>(content);
                ofertasUsuario = ofertas;
                return ofertas;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        //Eliminar oferta
        public static async Task<bool> eliminarOferta(string id)
        {
            string url = $"{StaticResources.urlHead}oferta/{id}";
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
