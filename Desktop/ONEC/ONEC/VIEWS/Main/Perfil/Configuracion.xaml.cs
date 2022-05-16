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
using System.Windows.Navigation;
using System.Windows.Shapes;
using ONEC.API_MODELS;
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Perfil
{
    /// <summary>
    /// Lógica de interacción para Configuracion.xaml
    /// </summary>
    public partial class Configuracion : Page
    {
        Principal principal;
        public Configuracion(Principal principal)
        {
            InitializeComponent();
            this.principal = principal;
            txtEmail.Text = Usuario.usuarioActual.email;
            btnGuardarCambios.Visibility = Visibility.Collapsed;
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            if (txtEmail.IsReadOnly)
            {
                txtEmail.IsReadOnly = false;
                imgEditar.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\cancelar.png");
            }
            else
            {
                btnGuardarCambios.Visibility = Visibility.Collapsed;
                txtEmail.IsReadOnly = true;
                txtEmail.Text = Usuario.usuarioActual.email;
                imgEditar.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\pencilB.png");
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Verificacion v = new Verificacion("pass");
            v.ShowDialog();
        }

        private void txtEmail_TextChanged(object sender, TextChangedEventArgs e)
        {
            btnGuardarCambios.Visibility = Visibility.Visible;
        }

        //Comprueba que el formato introducido en el textbox de email es válido
        private bool isValidEmailAddress(string s)
        {
            Regex regex = new Regex(@"^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$");
            return regex.IsMatch(s);
        }

        private async void btnGuardarCambios_Click(object sender, RoutedEventArgs e)
        {
            if (!String.IsNullOrEmpty(txtEmail.Text) && !String.IsNullOrWhiteSpace(txtEmail.Text) && isValidEmailAddress(txtEmail.Text))
            {
                Loading.Loading loading = new Loading.Loading();
                try
                {
                    if (!await Usuario.comprobarMail(txtEmail.Text))
                    {
                        Verificacion v = new Verificacion("email");
                        v.ShowDialog();
                        if (v.verificado)
                        {
                            Usuario u = new Usuario(txtEmail.Text, Usuario.usuarioActual.password);
                            if (await Usuario.actualizarUsuario(u))
                            {
                                loading.Close();
                                Did.Did did = new Did.Did("Correo actualizado", "El correo ha sido actualizado");
                                txtEmail.Text = Usuario.usuarioActual.email;
                                did.ShowDialog();
                            }
                            else
                            {
                                loading.Close();
                                ErrorPopUp err = new ErrorPopUp("Error al actualizar correo");
                                err.ShowDialog();
                            }

                        }
                    }
                    else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("El correo introducido\nya está siendo usado.");
                        err.ShowDialog();
                    }
                }catch(Exception ex)
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al actualzar correo");
                    err.ShowDialog();
                }
            }else
            {
                ErrorPopUp err = new ErrorPopUp("Correo no válido");
                err.ShowDialog();
            }

        }
    }
}
