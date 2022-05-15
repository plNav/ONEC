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

namespace ONEC.VIEWS.Main.Ofertas
{
    /// <summary>
    /// Lógica de interacción para OfertaDetallesCandidato.xaml
    /// </summary>
    public partial class OfertaDetallesCandidato : Page
    {
        Principal principal;
        List<API_MODELS.Ofertas> ofertas;
        API_MODELS.Ofertas oferta;
        List<API_MODELS.CV> cvs;
        List<CandidatosOfertas> candidatos;
        API_MODELS.CV cv;
        CandidatosOfertas candidato;
        string email;
        public OfertaDetallesCandidato(Principal principal, List<API_MODELS.Ofertas> ofertas, API_MODELS.Ofertas oferta, List<API_MODELS.CV> cvs, List<CandidatosOfertas> candidatos, API_MODELS.CV cv, string email, CandidatosOfertas candidato)
        {
            InitializeComponent();
            this.principal = principal;
            this.ofertas = ofertas;
            this.oferta = oferta;
            this.cvs = cvs;
            this.candidatos = candidatos;
            this.cv = cv;
            this.email = email;
            this.candidato = candidato;
            pintarCandidato(cv, email);
        }

        private void pintarCandidato(API_MODELS.CV cv, string email)
        {
            txtCorreo.Text = email;
            txtNombre.Text = cv.nombre;
            txtTelefono.Text = cv.telefono;
            txtUbicacion.Text = cv.ubicacion;
            txtTitulo.Text = cv.especialidad != null ? cv.especialidad : cv.titulo != null ? cv.titulo : "Sin titulación";
            txtExp.Text = cv.experiencia > 0 ? cv.experiencia.ToString() + " años." : "Sin experiencia";
            if (cv.habilidades.Count() > 0)
            {
                wrapHab.Visibility = Visibility.Visible;
                txtSinHab.Visibility = Visibility.Collapsed;
                foreach(string hab in cv.habilidades)
                {
                    Border border = new Border
                    {
                        CornerRadius = new CornerRadius(5),
                        Margin = new Thickness(5, 5, 0, 0),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#2C8AAF")),
                        Padding = new Thickness(5)
                    };

                    Label habilidad = new Label
                    {
                        Foreground = Brushes.White,
                        FontSize = 13,
                        VerticalAlignment = VerticalAlignment.Center,
                        HorizontalAlignment = HorizontalAlignment.Center,
                        Content = "# " + hab
                    };
                    border.Child = habilidad;
                    wrapHab.Children.Add(border);
                }
            }else
            {
                wrapHab.Visibility = Visibility.Collapsed;
                txtSinHab.Visibility = Visibility.Visible;
            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new OfertaCandidatos(principal, ofertas, oferta, cvs, candidatos);
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                if(await API_MODELS.CandidatosOfertas.eliminarCandidatoID(candidato._id))
                {
                    cvs.Remove(cv);
                    candidatos.Remove(candidato);
                    loading.Close();
                    Did.Did did = new Did.Did("Candidato eliminado", "El candidato ha sido eliminado");
                    did.ShowDialog();
                    principal.mainFrame.Content = new OfertaCandidatos(principal, ofertas, oferta, cvs, candidatos);
                }else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al eliminar candidato");
                    err.ShowDialog();
                }
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al eliminar candidato");
                err.ShowDialog();
            }
        }
    }
}
