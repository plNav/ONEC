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
using ONEC.VIEWS.Error;
using ONEC.API_MODELS;
using ONEC.VIEWS.ModoApp;

namespace ONEC.VIEWS
{
    /// <summary>
    /// Lógica de interacción para Registro.xaml
    /// </summary>
    public partial class Registro : Page
    {
        bool verPass = false;
        bool verPassRepetir = false;
        public Registro()
        {
            InitializeComponent();
        }
        
        //Cambia el color de fondo del botón de registro al hacer mouse enter
        private void Button_MouseEnter(object sender, MouseEventArgs e)
        {
            btnRegistro.Background = (Brush)(new BrushConverter().ConvertFrom("#1c4a59"));
        }

        //Cambia el color de fondo del botón de registro al hacer mouse leave
        private void btnLogin_MouseLeave(object sender, MouseEventArgs e)
        {
            btnRegistro.Background = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
        }

        //Cambia la Page a Login al hacer click en el texto
        private void TextBlock_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            StaticResources.main.frameContent.Content = new Login();
        }

        //Muestra o oculta la pass introducida al hacer click en la imagen
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

        //Muestra o oculta la password repetida introducida al hacer click en la imagen
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

        private async void btnRegistro_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            loading.Show();
            string email = txtEmail.Text,
                pass = verPass ? txtPrev.Text : txtPass.Password,
                passRep = verPassRepetir ? txtPrevRepetir.Text : txtPassRepetir.Password;

            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(pass) || string.IsNullOrEmpty(passRep))
            {
                loading.Close();
                ErrorPopUp error = new ErrorPopUp("Debe introducir todos los campos.");
                error.ShowDialog();
            }else if (!isValidEmailAddress(email))
            {
                loading.Close();
                ErrorPopUp error = new ErrorPopUp("Debe de introducir\nun email válido.");
                error.ShowDialog();
            }else if (!pass.Equals(passRep))
            {
                loading.Close();
                ErrorPopUp error = new ErrorPopUp("Las contraseñas introducidas\nno coinciden.");
                error.ShowDialog();
            }else
            {
                try
                {
                    if (await Usuario.comprobarMail(email))
                    {
                        loading.Close();
                        ErrorPopUp error = new ErrorPopUp("El email introducido\nya pertenece a un usuario.");
                        error.ShowDialog();
                    }
                    else
                    {
                        //Creamos el usuario y navegamos al main
                        Usuario user = new Usuario(email, pass);
                        if (await Usuario.crearUsuario(user))
                        {
                            loading.Close();
                            StaticResources.main.frameContent.Content = new ModoAppPage();

                        }else
                        {
                            loading.Close();
                            ErrorPopUp error = new ErrorPopUp("Error al crear el usuario\nintentelo más tarde.");
                            error.ShowDialog();
                        }
                    }
                }catch(Exception ex)
                {
                    loading.Close();
                    ErrorPopUp error = new ErrorPopUp("Error al crear el usuario\nintentelo más tarde.");
                    error.ShowDialog();
                }
            }
        }

        //Comprueba que el formato introducido en el textbox de email es válido
        private bool isValidEmailAddress(string s)
        {
            Regex regex = new Regex(@"^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$");
            return regex.IsMatch(s);
        }
    }
}
