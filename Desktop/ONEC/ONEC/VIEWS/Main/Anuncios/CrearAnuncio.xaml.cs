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
using ONEC.VIEWS.Loading;

namespace ONEC.VIEWS.Main.Anuncios
{
    /// <summary>
    /// Lógica de interacción para CrearAnuncio.xaml
    /// </summary>
    public partial class CrearAnuncio : Page
    {
        Principal principal;
        public CrearAnuncio(Principal principal)
        {
            InitializeComponent();
            this.principal = principal;
        }

        private async void cancelar_Click(object sender, RoutedEventArgs e)
        {
            principal.mainFrame.Content = new AnunciosMain(principal, Anuncio.anunciosUsuario);
        }

        private async void aceptar_Click(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(txtCategoria.Text) || String.IsNullOrWhiteSpace(txtCategoria.Text) || String.IsNullOrEmpty(txtNombre.Text) || String.IsNullOrWhiteSpace(txtNombre.Text) || String.IsNullOrEmpty(txtDescripcion.Text) || String.IsNullOrWhiteSpace(txtDescripcion.Text) || String.IsNullOrEmpty(txtPrecio.Text) || String.IsNullOrWhiteSpace(txtPrecio.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Debe rellenar todos los campos");
                err.ShowDialog();
            }else if(!validFloat(txtPrecio.Text))
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
                    if (await Anuncio.crearAnuncio(anun))
                    {
                        Anuncio.anunciosUsuario.Add(Anuncio.anuncioCreado);
                        principal.mainFrame.Content = new AnunciosMain(principal, Anuncio.anunciosUsuario);
                        loading.Close();
                    }
                    else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Precio no válido");
                        err.ShowDialog();
                    }
                }catch(Exception ex)
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al crear el anuncio");
                    err.ShowDialog();
                }
            }
            
        }

        private bool validFloat(string value)
        {
            try
            {
                float.Parse(value);
                return !value.Contains(" "); //Con esta linea comprobamos que no contenga ningún accento
            }catch(Exception e)
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
    }
}
