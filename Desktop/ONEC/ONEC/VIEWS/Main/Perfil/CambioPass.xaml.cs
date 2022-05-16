using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using ONEC.API_MODELS;
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Perfil
{
    /// <summary>
    /// Lógica de interacción para CambioPass.xaml
    /// </summary>
    public partial class CambioPass : Window
    {
        bool verPass = false;
        bool verPassRepetir = false;
        public CambioPass()
        {
            InitializeComponent();
            Owner = StaticResources.main;
            StaticResources.main.Opacity = 0.5;
        }

        private void btnCancelar_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            StaticResources.main.Opacity = 1;
        }

        private void imgVerPass_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            if (verPass)
            {
                verPass = false;
                //Si la contraseña se esta visualizando en texto plano, la ocultamos
                imgVerPass.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ocultar.png");
                txtPass.Password = txtPrev.Text;
                txtPrev.Visibility = Visibility.Collapsed;
                txtPass.Visibility = Visibility.Visible;
            }
            else
            {
                verPass = true;
                //Si la contraseña está oculta (Formato password), la visualizamos
                imgVerPass.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ver.png");
                txtPrev.Text = txtPass.Password;
                txtPass.Visibility = Visibility.Collapsed;
                txtPrev.Visibility = Visibility.Visible;
            }
        }

        private void imgVerPassRepetir_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            if (verPassRepetir)
            {
                verPassRepetir = false;
                //Si la contraseña se esta visualizando en texto plano, la ocultamos
                imgVerPassRepetir.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ocultar.png");
                txtPassRepetir.Password = txtPrevRepetir.Text;
                txtPrevRepetir.Visibility = Visibility.Collapsed;
                txtPassRepetir.Visibility = Visibility.Visible;
            }
            else
            {
                verPassRepetir = true;
                //Si la contraseña está oculta (Formato password), la visualizamos
                imgVerPassRepetir.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ver.png");
                txtPrevRepetir.Text = txtPassRepetir.Password;
                txtPassRepetir.Visibility = Visibility.Collapsed;
                txtPrevRepetir.Visibility = Visibility.Visible;
            }
        }

        private async void btnAceptar_Click(object sender, RoutedEventArgs e)
        {
            string pass = verPass ? txtPrev.Text : txtPass.Password,
                passRep = verPassRepetir ? txtPrevRepetir.Text : txtPassRepetir.Password;
            if (
                txtPass.IsVisible && String.IsNullOrEmpty(txtPass.Password) ||
                txtPass.IsVisible && String.IsNullOrWhiteSpace(txtPass.Password) || 
                txtPrev.IsVisible && String.IsNullOrWhiteSpace(txtPrev.Text) || 
                txtPrev.IsVisible && String.IsNullOrEmpty(txtPrev.Text) || 
                txtPassRepetir.IsVisible && String.IsNullOrEmpty(txtPassRepetir.Password) ||
                txtPassRepetir.IsVisible && String.IsNullOrWhiteSpace(txtPassRepetir.Password) || 
                txtPrevRepetir.IsVisible && String.IsNullOrEmpty(txtPrevRepetir.Text) ||
                txtPrevRepetir.IsVisible && String.IsNullOrWhiteSpace(txtPrevRepetir.Text)
                )
            {
                this.Close();
                ErrorPopUp err = new ErrorPopUp("Faltan campos por introducir");
                err.ShowDialog();
                CambioPass c = new CambioPass();
                c.ShowDialog();

            }
            else if(!pass.Equals(passRep))
            {
                this.Close();
                ErrorPopUp err = new ErrorPopUp("Las contraseñas no coinciden");
                err.ShowDialog();
                CambioPass c = new CambioPass();
                c.ShowDialog();
            }
            else
            {
                string passw = Encrypt.GetSHA256(pass);
                Loading.Loading loading = new Loading.Loading();
                try
                {
                    loading.Show();
                    Usuario usu = new Usuario(Usuario.usuarioActual.email, passw);
                    if (await Usuario.actualizarUsuario(usu))
                    {

                        Usuario.usuarioActual.password = passw;
                        loading.Close();
                        this.Close();
                        Did.Did d = new Did.Did("Contraseña actualizada", "La contraseña ha sido actualizada");
                        d.ShowDialog();
                    }else
                    {
                        this.Close();
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al cambiar contraseña");
                        err.ShowDialog();
                        CambioPass c = new CambioPass();
                        c.ShowDialog();
                    }
                }catch(Exception ex)
                {
                    MessageBox.Show(ex.Message);
                    this.Close();
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al cambiar contraseña");
                    err.ShowDialog();
                    CambioPass c = new CambioPass();
                    c.ShowDialog();
                }
            }
        }
    }
}
