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


        private void Button_MouseEnter(object sender, MouseEventArgs e)
        {
            btnRegistro.Background = (Brush)(new BrushConverter().ConvertFrom("#1c4a59"));
        }

        private void btnLogin_MouseLeave(object sender, MouseEventArgs e)
        {
            btnRegistro.Background = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
        }

        private void TextBlock_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            StaticResources.main.frameContent.Content = new Login();
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
    }
}
