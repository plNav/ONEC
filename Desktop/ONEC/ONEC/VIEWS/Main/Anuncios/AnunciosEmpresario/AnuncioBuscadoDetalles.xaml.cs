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

namespace ONEC.VIEWS.Main.Anuncios.AnunciosEmpresario
{
    /// <summary>
    /// Lógica de interacción para AnuncioBuscadoDetalles.xaml
    /// </summary>
    public partial class AnuncioBuscadoDetalles : Page
    {
        Principal principal;
        string campo;
        Anuncio anuncio;
        List<string> idsAnunciosFav;
        string email;
        float puntuacion;


        public AnuncioBuscadoDetalles(Principal principal,List<string> idsAnunciosFav,  Anuncio anuncio, string campo, string email, float puntuacion)
        {
            InitializeComponent();
            this.principal = principal;
            this.campo = campo;
            this.anuncio = anuncio;
            this.idsAnunciosFav = idsAnunciosFav;
            this.email = email;
            this.puntuacion = puntuacion;
            cargarInfo(anuncio, email, puntuacion);
        }

        private void cargarInfo(Anuncio anuncio, string email, float puntuacion)
        {
            txtCategoria.Text = anuncio.categoria;
            txtNombre.Text = anuncio.nombre;
            txtDescripcion.Text = anuncio.descripcion;
            txtVisualizaciones.Text = anuncio.numVecesVisto.ToString();
            txtCorreo.Text = email;
            txtPuntuacion.Text = puntuacion.ToString();
            txtPrecio.Text = anuncio.precioPorHora ? anuncio.precio + "€ Hora" : anuncio.precio + "€";
        }

        private async void btnVerReviews_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                List<Resenyas> reviews = await Resenyas.obtenerResenyasAnuncio(anuncio._id);
                List<string> emails = await obtenerEmailsReviews(reviews);
                loading.Close();
                principal.mainFrame.Content = new AnuncioEmpresarioBuscadoValoraciones(principal, idsAnunciosFav, anuncio, campo, email, puntuacion, reviews, emails);
            }catch (Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al obtener las reseñas\npor favor inténtelo más tarde.");
                err.ShowDialog();
            }
        }

        private async Task<List<string>> obtenerEmailsReviews(List<Resenyas> reviews)
        {
            List<string> emails = new List<string>();
            foreach(Resenyas r in reviews)
            {
                Usuario u = await Usuario.obtenerUsuarioId(r.id_user);
                emails.Add(u.email);
            }
            return emails;
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new AnuncioEmpresarioBuscar(principal, idsAnunciosFav, campo);
        }

        private async void btnArchivar_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                AnunciosGuardados anuncio = new AnunciosGuardados(this.anuncio._id, Usuario.usuarioActual._id);
                if (await AnunciosGuardados.crearAnuncioGuardado(anuncio))
                {
                    loading.Close();
                    idsAnunciosFav.Add(anuncio.id_anuncio);
                    Did.Did did = new Did.Did("Anuncio archivado", "El anuncio ha sido archivado.");
                    did.ShowDialog();
                    principal.mainFrame.Content = new AnuncioEmpresarioBuscar(principal, idsAnunciosFav, campo);
                }else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al archivar el anuncio\npor favor inténtelo más tarde,");
                    err.ShowDialog();
                }
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al archivar el anuncio\npor favor inténtelo más tarde,");
                err.ShowDialog();
            }
        }
    }
}
