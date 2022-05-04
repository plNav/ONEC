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
    public partial class EditarAnuncio : Page
    {
        Principal principal;
        Anuncio anuncio;
        string puntuacion;
        public EditarAnuncio(Principal principal, Anuncio anuncio, string puntuacion)
        {
            InitializeComponent();
            this.principal = principal;
            this.anuncio = anuncio;
            cargarDatos(anuncio);
        }

        private async void cancelar_Click(object sender, RoutedEventArgs e)
        {
            //Primero calcular la puntuacion del anuncio, y luego navegar a los detalles
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                float puntuacion = await Resenyas.obtenerPuntuacionAnuncio(anuncio._id);
                principal.mainFrame.Content = new AnuncioDetalles(anuncio, principal, puntuacion.ToString());
                loading.Close();
            }
            catch (Exception ex)
            {
                loading.Close();
                principal.mainFrame.Content = new AnuncioDetallesError(principal, anuncio);
            }
        }

        private bool validFloat(string value)
        {
            try
            {
                float.Parse(value);
                return !value.Contains(" "); //Con esta linea comprobamos que no contenga ningún acento
            }
            catch (Exception e)
            {
                return false;
            }
        }

        private void txtPrecio_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            bool approvedDecimalPoint = false;
            if (e.Text == ".")
            {
                if (!((TextBox)sender).Text.Contains("."))
                    approvedDecimalPoint = true;
            }

            if (!(char.IsDigit(e.Text, e.Text.Length - 1) || approvedDecimalPoint))
                e.Handled = true;
        }

        private void cargarDatos(Anuncio anuncio)
        {
            txtCategoria.Text = anuncio.categoria;
            txtNombre.Text = anuncio.nombre;
            txtDescripcion.Text = anuncio.descripcion;
            txtPrecio.Text = anuncio.precio.ToString();
            precioHora.IsChecked = anuncio.precioPorHora;
        }

        private async void aceptar_Click(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(txtCategoria.Text) || String.IsNullOrWhiteSpace(txtCategoria.Text) || String.IsNullOrEmpty(txtNombre.Text) || String.IsNullOrWhiteSpace(txtNombre.Text) || String.IsNullOrEmpty(txtDescripcion.Text) || String.IsNullOrWhiteSpace(txtDescripcion.Text) || String.IsNullOrEmpty(txtPrecio.Text) || String.IsNullOrWhiteSpace(txtPrecio.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Debe rellenar todos los campos");
                err.ShowDialog();
            }
            else if (!validFloat(txtPrecio.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Precio no válido");
                err.ShowDialog();
            }
            else
            {
                Loading.Loading loading = new Loading.Loading();
                loading.Show();
                try
                {
                    Anuncio anun = new Anuncio(Usuario.usuarioActual._id, txtCategoria.Text, txtNombre.Text, txtDescripcion.Text, float.Parse(txtPrecio.Text), precioHora.IsChecked.Value);
                    if (await Anuncio.actualizarAnuncio(anuncio._id, anun))
                    {
                        loading.Close();
                        Anuncio.anunciosUsuario.Remove(anuncio);
                        anuncio.categoria = anun.categoria;
                        anuncio.nombre = anun.nombre;
                        anuncio.descripcion = anun.descripcion;
                        anuncio.precio = anun.precio;
                        anuncio.precioPorHora = anun.precioPorHora;
                        Anuncio.anunciosUsuario.Add(anuncio);
                        Did.Did did = new Did.Did("Anuncio actualizado", "El anuncio ha sido actualizado");
                        did.ShowDialog();
                        principal.mainFrame.Content = new AnunciosMain(principal, Anuncio.anunciosUsuario);
                    }else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al editar el anuncio");
                        err.ShowDialog();
                    }
                }
                catch (Exception ex)
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al editar el anuncio");
                    err.ShowDialog();
                }
            }
        }
    }
}
