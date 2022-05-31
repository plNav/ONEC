using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using ONEC.VIEWS;
using ONEC.VIEWS.Main;


namespace ONEC
{
    class StaticResources
    {
        /**
         * modo
         * 1 = Despliegue local
         * 2 = Despliegue Heroku
         * **/
        private static int modo = 2;


        public static string urlHead = modo == 1 ? "http://localhost:8081/api/" :  "https://onec-nuevo.herokuapp.com/api/";
        public static HttpClient httpClient = new HttpClient();
        public static MainWindow main;
        public static Principal principal;
        public static bool modoEmpresario = false;
    }
}
