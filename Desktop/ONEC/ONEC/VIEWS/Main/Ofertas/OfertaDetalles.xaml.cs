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
    /// Lógica de interacción para OfertaDetalles.xaml
    /// </summary>
    public partial class OfertaDetalles : Page
    {
        Principal principal;
        API_MODELS.Ofertas oferta;
        List<API_MODELS.Ofertas> ofertas;
        public OfertaDetalles(Principal principal, List<API_MODELS.Ofertas> ofertas, API_MODELS.Ofertas oferta)
        {
            InitializeComponent();
            this.principal = principal;
            this.oferta = oferta;
            this.ofertas = ofertas;
            cargarDetalles(oferta);
        }

        private void cargarDetalles(API_MODELS.Ofertas oferta)
        {
            txtNombre.Text = oferta.nombre;
            txtDescripcion.Text = oferta.descripcion;
            txtExp.Text = oferta.experiencia > 0 ? oferta.experiencia.ToString() : "No requiere";
            txtTitulacion.Text = oferta.especialidad != null ? oferta.especialidad : oferta.titulo != null ? oferta.titulo : "No requiere";
            if (oferta.habilidades.Count() > 0)
            {
                wrapHab.Visibility = Visibility.Visible;
                foreach(string hab in oferta.habilidades)
                {
                    Border border = new Border
                    {
                        CornerRadius = new CornerRadius(5),
                        Margin = new Thickness(5, 5, 0, 0),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#4D6D86")),
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
                txtHab.Visibility = Visibility.Visible;
            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new OfertasLoader(principal);
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            ErrorPopUp err = new ErrorPopUp("Error al cargar los candidatos");
            try
            {
                loading.Show();
                List<CandidatosOfertas> candidatos = await CandidatosOfertas.obtenerCandidatosOferta(oferta._id);
                List<API_MODELS.CV> cvs = await obtenerCVCandidatos(candidatos);
                loading.Close();
                principal.mainFrame.Content = new OfertaCandidatos(principal, ofertas, oferta, cvs, candidatos);
            }
            catch(Exception ex)
            {
                loading.Close();
                err.ShowDialog();
            }
        }

        private async Task<List<API_MODELS.CV>> obtenerCVCandidatos(List<CandidatosOfertas> ofertas)
        {
            List<API_MODELS.CV> cvs = new List<API_MODELS.CV>();
            foreach(CandidatosOfertas candidato in ofertas)
            {
                cvs.Add(await API_MODELS.CV.obtenerCv(candidato.id_cv));
            }
            return cvs;
        }

        private async void Button_Click_1(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try {
                loading.Show();
                if(await API_MODELS.CandidatosOfertas.eliminarCandidatosOferta(oferta._id))
                {
                    if (await API_MODELS.Ofertas.eliminarOferta(oferta._id))
                    {
                        loading.Close();
                        Did.Did did = new Did.Did("Oferta eliminada", "La oferta ha sido eliminada");
                        did.ShowDialog();
                        principal.mainFrame.Content = new OfertasLoader(principal);
                    }else
                    {
                        ErrorPopUp err = new ErrorPopUp("Error al eliminar oferta");
                        loading.Close();
                        err.ShowDialog();
                    }
                }else
                {
                    ErrorPopUp err = new ErrorPopUp("Error al eliminar oferta");
                    loading.Close();
                    err.ShowDialog();
                }
            }catch(Exception ex)
            {
                ErrorPopUp err = new ErrorPopUp("Error al eliminar oferta");
                loading.Close();
                err.ShowDialog();
            }
        }
    }
}
