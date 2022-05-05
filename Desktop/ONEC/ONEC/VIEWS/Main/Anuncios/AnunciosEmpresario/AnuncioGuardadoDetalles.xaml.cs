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
using ONEC.VIEWS.Error;
using ONEC.API_MODELS;

namespace ONEC.VIEWS.Main.Anuncios.AnunciosEmpresario
{
    /// <summary>
    /// Lógica de interacción para AnuncioGuardadoDetalles.xaml
    /// </summary>
    public partial class AnuncioGuardadoDetalles : Page
    {
        AnunciosGuardados anuncioGuardado;
        List<AnunciosGuardados> anunciosGuardados;
        Principal principal;
        Anuncio anuncio;
        public AnuncioGuardadoDetalles(Principal principal, Anuncio anuncio, Usuario usuario, float puntuacion, List<Resenyas> reviews, AnunciosGuardados anuncioG, List<AnunciosGuardados> anunciosGuardados)
        {
            InitializeComponent();
            this.principal = principal;
            this.anuncio = anuncio;
            this.anuncioGuardado = anuncioG;
            this.anunciosGuardados = anunciosGuardados;

            loadDeatails(anuncio, usuario.email, puntuacion, reviews.Count(), anuncioG);
        }

        private void loadDeatails(Anuncio anuncio, string email, float puntuacion, int reviewsUsuario, AnunciosGuardados anuncioG)
        {
            txtCategoria.Text = anuncio.categoria;
            txtNombre.Text = anuncio.nombre;
            txtDescripcion.Text = anuncio.descripcion;
            txtPrecio.Text = anuncio.precioPorHora ? anuncio.precio + "€ Hora" : anuncio.precio + "€";
            txtPuntuacion.Text = puntuacion.ToString();
            txtVisualizaciones.Text = anuncio.numVecesVisto.ToString();
            btnReviews.Visibility = reviewsUsuario <= 0 ? Visibility.Visible : Visibility.Collapsed;
        }

        private async void btnEliminar_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                if ( await AnunciosGuardados.eliminarAnunciosGuardado(anuncioGuardado._id))
                {
                    loading.Close();
                    Did.Did did = new Did.Did("Anuncio Eliminado", "El anuncio ha sido eliminado.");
                    did.ShowDialog();
                    principal.mainFrame.Content = new AnunciosLoader(principal);
                }else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                    err.ShowDialog();
                }
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                err.ShowDialog();
            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new AnunciosLoader(principal);
        }

        private async void btnVerReviews_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                List<Resenyas> res = await Resenyas.obtenerResenyasAnuncio(anuncio._id);
                List<string> emails = await obtenerEmails(res);
                loading.Close();
                principal.mainFrame.Content = new AnuncioEmpresarioValoraciones(principal, res, anuncioGuardado, emails, anunciosGuardados);
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al cargar reseñas");
                err.ShowDialog();
            }
        }

        private async Task<List<string>> obtenerEmails(List<Resenyas> res)
        {
            List<string> lista = new List<string>();
            foreach (Resenyas re in res)
            {
                Usuario u = await Usuario.obtenerUsuarioId(re.id_user);
                lista.Add(u.email);
            }
            return lista;
        }
    }
}
