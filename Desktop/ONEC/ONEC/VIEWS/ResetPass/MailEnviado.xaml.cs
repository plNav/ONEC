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
using ONEC.API_MODELS;

namespace ONEC.VIEWS.ResetPass
{
    /// <summary>
    /// Lógica de interacción para EnviarMail.xaml
    /// </summary>
    public partial class MailEnviado : Page
    {
        string email;
        public MailEnviado(string email)
        {
            InitializeComponent();
            this.email = email;
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            StaticResources.main.frameContent.Content = new Login();
        }

        private async void TextBlock_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            Loading.Loading load = new Loading.Loading();
            try
            {
                load.Show();
                Usuario u = await Usuario.resetPass(email);
                if (u != null)
                {
                    load.Close();
                    StaticResources.main.frameContent.Content = new MailEnviado(email);
                }
                else
                {
                    load.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al mandar correo");
                    err.ShowDialog();
                }
            }catch(Exception ex)
            {
                load.Close();
                ErrorPopUp err = new ErrorPopUp("Error al mandar correo");
                err.ShowDialog();
            }
        }
    }
}
