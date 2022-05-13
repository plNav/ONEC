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
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Anuncios
{
    /// <summary>
    /// Lógica de interacción para AnuncioDetallesError.xaml
    /// </summary>
    public partial class AnuncioDetallesError : Page
    {
        Anuncio anuncio;
        Principal principal;
        public AnuncioDetallesError(Principal principal,Anuncio anuncio)
        {
            InitializeComponent();
            this.anuncio = anuncio;
            this.principal = principal;
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                float puntuacion = await Resenyas.obtenerPuntuacionAnuncio(anuncio._id);
                principal.mainFrame.Content = new AnuncioDetalles(anuncio, principal, puntuacion.ToString());
                loading.Close();
            }catch (Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al cargar el anuncio");
                err.ShowDialog();
            }
        }
    }
}
