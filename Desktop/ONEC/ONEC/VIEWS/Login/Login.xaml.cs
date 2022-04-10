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
using ONEC.VIEWS.Main;
using ONEC.VIEWS.ModoApp;

namespace ONEC.VIEWS
{
    /// <summary>
    /// Lógica de interacción para Login.xaml
    /// </summary>
    public partial class Login : Page
    {
        bool modifiedEm = false;
        bool modifiedPass = false;
        bool verPass = false;
        public Login()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            StaticResources.main.frameContent.Content = new Registro();
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

        private void Button_MouseEnter(object sender, MouseEventArgs e)
        {
            btnLogin.Background = (Brush)(new BrushConverter().ConvertFrom("#1c4a59"));
        }

        private void btnLogin_MouseLeave(object sender, MouseEventArgs e)
        {
            btnLogin.Background = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
        }

        private void TextBlock_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            //Abrimos el registro
            StaticResources.main.frameContent.Content = new Registro();
        }

        private void TextBlock_MouseLeftButtonDown_1(object sender, MouseButtonEventArgs e)
        {
            //Abrimos el forgotPassword
            StaticResources.main.frameContent.Content = new PassOlvidada();
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            StaticResources.main.frameContent.Content = new ModoAppPage();
        }
    }
}
