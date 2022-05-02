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
        public static string urlHead = "http://localhost:8081/api/";
        public static HttpClient httpClient = new HttpClient();
        public static MainWindow main;
        public static Principal principal;
        public static bool modoEmpresario = false;
    }
}
