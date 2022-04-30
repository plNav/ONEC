using ONEC.VIEWS.ResetPass;
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
using ONEC.VIEWS.Main;
using ONEC.VIEWS.ModoApp;
using System.Text.RegularExpressions;
using ONEC.API_MODELS;
using ONEC.VIEWS.Loading;

namespace ONEC.VIEWS
{
    /// <summary>
    /// Lógica de interacción para Login.xaml
    /// </summary>
    public partial class Login : Page
    {
        bool verPass = false;
        public Login()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            StaticResources.main.frameContent.Content = new Registro();
        }

        //Este método se encarga de ocultar y visualizar la contraseña que escribe el usuario
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

        //Cambiar color de fondo al hacer mouse enter en botón
        private void Button_MouseEnter(object sender, MouseEventArgs e)
        {
            btnLogin.Background = (Brush)(new BrushConverter().ConvertFrom("#1c4a59"));
        }

        //Cambiar color de fondo del botón al hacer mouse leave
        private void btnLogin_MouseLeave(object sender, MouseEventArgs e)
        {
            btnLogin.Background = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
        }

        //Navega a Registro al hacer click
        private void TextBlock_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            //Abrimos el registro
            StaticResources.main.frameContent.Content = new Registro();
        }

        //Navega a contraseña olvidada al hacer click
        private void TextBlock_MouseLeftButtonDown_1(object sender, MouseButtonEventArgs e)
        {
            //Abrimos el forgotPassword
            StaticResources.main.frameContent.Content = new PassOlvidada();
        }

        //Método que realiza el checking de los campos e inicia sesión
        private async void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            //StaticResources.main.frameContent.Content = new ModoAppPage();
            string email = txtEmail.Text;
            string pass = verPass ? txtPrev.Text : txtPass.Password;
            try
            {
                if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(pass))
                {
                    //mostramos error de campos vacíos
                    ErrorPopUp error = new ErrorPopUp("Hay campos sin introducir.");
                    error.ShowDialog();
                }
                else if (!IsValidEmailAddress(email))
                {
                    //mostrar error de correo no valido (Formato)
                    ErrorPopUp error = new ErrorPopUp("El email introducido\nno tiene un formato válido.");
                    error.ShowDialog();
                }
                else if (!await Usuario.comprobarMail(email))
                {
                    //El email introducido no pertenece a ningún usuario
                    ErrorPopUp error = new ErrorPopUp("No existe ningún usuario\ncon el email introducido.");
                    error.ShowDialog();
                }
                else
                {
                    //abrir popUp de Loading y intentar conectarse
                    Loading.Loading loading = new Loading.Loading();
                    loading.Show();
                    if(await Usuario.loguear(email,pass))
                    {
                        loading.Close();
                        StaticResources.main.frameContent.Content = new ModoAppPage();
                    }else
                    {
                        loading.Close();
                        ErrorPopUp error = new ErrorPopUp("La contrase introducida\nes incorrecta");
                        error.ShowDialog();
                    }
                }
            }catch(Exception ex)
            {
                ErrorPopUp error = new ErrorPopUp("Ha ocurrido un error\nintentelo más tarde.");
                error.ShowDialog();
            }
        }

        //Comprueba que el formato introducido en el textbox de email es válido
        private bool IsValidEmailAddress(string s)
        {
            Regex regex = new Regex(@"^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$");
            return regex.IsMatch(s);
        }
    }
}
