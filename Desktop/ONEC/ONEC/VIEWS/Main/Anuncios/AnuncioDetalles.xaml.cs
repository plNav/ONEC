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
    /// Lógica de interacción para EditarAnuncio.xaml
    /// </summary>
    public partial class AnuncioDetalles : Page
    {
        Anuncio anuncio;
        Principal principal;
        string puntuacion;
        public AnuncioDetalles(Anuncio anuncio, Principal principal, string puntuacion)
        {
            InitializeComponent();
            this.anuncio = anuncio;
            this.principal = principal;
            this.puntuacion = puntuacion;
            setValues(anuncio, puntuacion);
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new AnunciosMain(principal, Anuncio.anunciosUsuario);
        }

        private void setValues(Anuncio anuncio, string puntuacion)
        {
            txtCategoria.Text = anuncio.categoria;
            txtCorreo.Text = Usuario.usuarioActual.email;
            txtDescripcion.Text = anuncio.descripcion;
            txtNombre.Text = anuncio.nombre;
            txtPrecio.Text = anuncio.precioPorHora ? anuncio.precio + "€ Hora" : anuncio.precio + "€";
            txtPuntuacion.Text = puntuacion;
            txtVisualizaciones.Text = anuncio.numVecesVisto.ToString();
        }

        private async void btnEditar_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                List<Resenyas> list = await Resenyas.obtenerResenyasAnuncio(anuncio._id);
                if (list.Count() > 0)
                {
                    loading.Close();
                    Info.Info info = new Info.Info("El anuncio no se puede editar\n ya ha sido reseñado");
                    info.ShowDialog();
                }
                else
                {
                    loading.Close();
                    principal.mainFrame.Content = new EditarAnuncio(principal, anuncio, puntuacion);
                }
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al cargar el anuncio");
                err.ShowDialog();
            }
        }

        private async void btnEliminar_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            loading.Show();
            try
            {
                //Primero eliminamos las reseñas
                if (await Resenyas.eliminarResenya(anuncio._id))
                {
                    if (await AnunciosGuardados.eliminarAnunciosGuardadosDeUnAnuncio(anuncio._id))
                    {
                        if (await Visualizaciones.eliminarVisualizacionesAnuncio(anuncio._id))
                        {
                            if (await Anuncio.eliminarAnuncio(anuncio._id))
                            {
                                Anuncio.anunciosUsuario.Remove(anuncio);
                                loading.Close();
                                Did.Did did = new Did.Did("Anuncio eliminado","El anuncio ha sido eliminado");
                                did.ShowDialog();
                                principal.mainFrame.Content = new AnunciosMain(principal, Anuncio.anunciosUsuario);
                            }
                            else
                            {
                                loading.Close();
                                ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                                err.ShowDialog();
                            }

                        }
                        else
                        {
                            loading.Close();
                            ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                            err.ShowDialog();
                        }
                    }
                    else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                        err.ShowDialog();
                    }
                }
                else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                    err.ShowDialog();
                }
            }
            catch (Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                err.ShowDialog();
            }

        }

        private async void btnReviews_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                List<Resenyas> reviews = await Resenyas.obtenerResenyasAnuncio(anuncio._id);
                List<string> emails = await obtenerEmails(reviews);
                principal.mainFrame.Content = new AnuncioValoraciones(principal, anuncio, reviews, emails);
                loading.Close();
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al cargar las reseñas\npor favor inténtelo más tarde.");
                err.ShowDialog();
            }
        }

        private async Task<List<string>> obtenerEmails(List<Resenyas> res)
        {
            List<string> lista = new List<string>();
            foreach(Resenyas re in res)
            {
                Usuario u = await Usuario.obtenerUsuarioId(re.id_user);
                lista.Add(u.email);
            }
            return lista;
        }
    }
}
