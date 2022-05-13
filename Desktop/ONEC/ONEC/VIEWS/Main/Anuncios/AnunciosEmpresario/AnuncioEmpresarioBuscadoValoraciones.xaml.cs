using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using ONEC.API_MODELS;

namespace ONEC.VIEWS.Main.Anuncios.AnunciosEmpresario
{
    /// <summary>
    /// Lógica de interacción para AnuncioEmpresarioBuscadoValoraciones.xaml
    /// </summary>
    public partial class AnuncioEmpresarioBuscadoValoraciones : Page
    {
        Principal principal;
        List<string> idsAnunciosFav;
        Anuncio anuncio;
        string campo;
        string email;
        float puntuacion;

        public AnuncioEmpresarioBuscadoValoraciones(Principal principal, List<string> idsAnunciosFav, Anuncio anuncio, string campo, string email, float puntuacion, List<Resenyas> reviews, List<string>emails)
        {
            InitializeComponent();
            this.principal = principal;
            this.idsAnunciosFav = idsAnunciosFav;
            this.anuncio = anuncio;
            this.campo = campo;
            this.email = email;
            this.puntuacion = puntuacion;
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new AnuncioBuscadoDetalles(principal, idsAnunciosFav, anuncio, campo, email, puntuacion);
        }
    }
}
