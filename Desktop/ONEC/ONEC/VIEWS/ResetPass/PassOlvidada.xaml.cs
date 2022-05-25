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

namespace ONEC.VIEWS.ResetPass
{
    /// <summary>
    /// Lógica de interacción para PassOlvidada.xaml
    /// </summary>
    public partial class PassOlvidada : Page
    {
        public PassOlvidada()
        {
            InitializeComponent();
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            StaticResources.main.frameContent.Content = new Login();
        }

        private async void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(txtEmail.Text) || String.IsNullOrWhiteSpace(txtEmail.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Error al mandar correo");
                err.ShowDialog();
            }
            else if (!isValidEmailAddress(txtEmail.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Debe introducir\nun correo válido");
                err.ShowDialog();
            }
            else
            {
                Loading.Loading load = new Loading.Loading();
                try
                {
                    load.Show();
                    if (!await Usuario.comprobarMail(txtEmail.Text))
                    {
                        load.Close();
                        ErrorPopUp err = new ErrorPopUp("El correo introducido\nno pertenece\na ningún usuario");
                        err.ShowDialog();
                    }
                    else
                    {
                        Usuario u = await Usuario.resetPass(txtEmail.Text);
                        if (u != null)
                        {
                            load.Close();
                            StaticResources.main.frameContent.Content = new MailEnviado(txtEmail.Text);
                        }else
                        {
                            load.Close();
                            ErrorPopUp err = new ErrorPopUp("Error al mandar correo");
                            err.ShowDialog();
                        }
                    }
                    
                }
                catch (Exception ex)
                {
                    load.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al mandar correo");
                    err.ShowDialog();
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
