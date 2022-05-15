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
    public class CV
    {
        /********************************
         *          Variables            *
         ********************************/

        public string _id { get; set; }
        public string id_user { get; set; }
        public string nombre { get; set; }
        public string telefono { get; set; }
        public string ubicacion { get; set; }
        public string correo { get; set; }
        public int experiencia { get; set; }
        public string titulo { get; set; }
        public string especialidad { get; set; }
        public List<string> habilidades { get; set; }
        public List<string> habilidadesLow { get; set; }

        public static CV cvActual;
        public static List<CV> cvsCompatiblesOferta;

        /********************************
         *          Constructores       *
         ********************************/
        public CV(string id_user, string nombre, string telefono, string ubicacion, string correo, int experiencia, string titulo, string especialidad, List<string> habilidades, List<string>habilidadesLow)
        {
            this.id_user = id_user;
            this.nombre = nombre;
            this.telefono = telefono;
            this.ubicacion = ubicacion;
            this.correo = correo;
            this.experiencia = experiencia;
            this.titulo = titulo;
            this.especialidad = especialidad;
            this.habilidades = habilidades;
            this.habilidadesLow = habilidadesLow;
        }

        public CV()
        {

        }

        /*******************************
        *          Funciones           *
        *******************************/
        public static async Task<bool> crearCV(CV cv)
        {
            try
            {
                string url = $"{StaticResources.urlHead}cv";

                JObject habilidades = new JObject();
                habilidades["habilidades"] = JToken.FromObject(cv.habilidades);


                JObject jsonHabilidadesLow = new JObject();
                jsonHabilidadesLow["habilidadesLow"] = JToken.FromObject(cv.habilidadesLow);

                JObject values = new JObject
            {
                { "id_user", cv.id_user},
                { "nombre", cv.nombre},
                { "telefono", cv.telefono},
                { "ubicacion", cv.ubicacion},
                { "correo", cv.correo},
                { "experiencia", cv.experiencia},
                { "titulo", cv.titulo},
                { "especialidad", cv.especialidad},
                { "habilidades", habilidades["habilidades"]},
                { "habilidadesLow", jsonHabilidadesLow["habilidadesLow"]},
            };

                HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

                HttpResponseMessage httpResponse = await StaticResources.httpClient.PostAsync(url, content);

                if (httpResponse.IsSuccessStatusCode)
                {
                    string result = await httpResponse.Content.ReadAsStringAsync();
                    cvActual = JsonSerializer.Deserialize<CV>(result);
                    return true;
                }
                else return false;
            }catch(Exception e)
            {
                return false;
            }

        }

        public static async Task<CV> obtenerCv(string id)
        {
            string url = $"{StaticResources.urlHead}cv/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                CV cv = JsonSerializer.Deserialize<CV>(content);
                cvActual = cv;
                return cv;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        public static async Task<CV> obtenerCvId(string id)
        {
            string url = $"{StaticResources.urlHead}cv/usuario/{id}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                CV cv = JsonSerializer.Deserialize<CV>(content);
                cvActual = cv;
                return cv;
            }else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }


        //Buscador de CVs para oferta
        public static async Task<List<CV>> buscadorCvOferta(string id, string reqHab)
        {
            string url = $"{StaticResources.urlHead}cv/oferta/{id}/{reqHab}";
            HttpResponseMessage httpResponse = await StaticResources.httpClient.GetAsync(url);

            if (httpResponse.IsSuccessStatusCode)
            {
                string content = await httpResponse.Content.ReadAsStringAsync();
                List<CV> cvs = JsonSerializer.Deserialize<List<CV>>(content);
                cvsCompatiblesOferta = cvs;
                return cvs;
            }
            else throw new HttpRequestException("Respuesta fallida en comprobarMail");
        }

        public static async Task<bool> actualizarCV(string id, CV cv)
        {
            string url = $"{StaticResources.urlHead}cv/{id}";

            JObject habilidades = new JObject();
            habilidades["habilidades"] = JToken.FromObject(cv.habilidades);


            JObject jsonHabilidadesLow = new JObject();
            jsonHabilidadesLow["habilidadesLow"] = JToken.FromObject(cv.habilidadesLow);

            JObject values = new JObject
            {
                {"id_user", cv.id_user},
                {"nombre", cv.nombre },
                {"telefono", cv.telefono },
                {"ubicacion",cv.ubicacion },
                {"correo",cv.correo },
                {"experiencia",cv.experiencia },
                {"titulo",cv.titulo },
                {"especialidad", cv.especialidad },
                {"habilidades", habilidades["habilidades"] },
                {"habilidadesLow", jsonHabilidadesLow["habilidadesLow"] }
            };

            HttpContent content = new StringContent(values.ToString(), System.Text.Encoding.UTF8, "application/json");

            HttpResponseMessage httpResponse = await StaticResources.httpClient.PutAsync(url, content);

            if (httpResponse.IsSuccessStatusCode)
            {
                string result = await httpResponse.Content.ReadAsStringAsync();
                return true;
            }
            else return false;
        }
    }
}
