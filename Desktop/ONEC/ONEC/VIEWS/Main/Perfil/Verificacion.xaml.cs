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
using System.Windows.Shapes;
using ONEC.API_MODELS;
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Perfil
{
    /// <summary>
    /// Lógica de interacción para Verificacion.xaml
    /// </summary>
    public partial class Verificacion : Window
    {
        public bool verificado = false;
        string tipo;
        bool verPass = false;
        public Verificacion(string tipo)
        {
            InitializeComponent();
            this.tipo = tipo;
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

        private void btnAceptar_Click(object sender, RoutedEventArgs e)
        {
            if (txtPrev.IsVisible && Usuario.usuarioActual.password.Equals(Encrypt.GetSHA256(txtPrev.Text)))
            {
                this.Close();
                CambioPass cambio = new CambioPass();
                cambio.ShowDialog();
            }
            else if (txtPass.IsVisible && Usuario.usuarioActual.password.Equals(Encrypt.GetSHA256(txtPass.Password)))
            {
                if (tipo.Equals("pass"))
                {
                    this.Close();
                    CambioPass cambio = new CambioPass();
                    cambio.ShowDialog();
                }else if (tipo.Equals("email"))
                {
                    verificado = true;
                    this.Close();
                }
            }
            else
            {
                this.Close();
                ErrorPopUp err = new ErrorPopUp("Contraseña incorrecta");
                err.ShowDialog();
                Verificacion v = new Verificacion(tipo);
                v.ShowDialog();
            }
        }
    }
}
