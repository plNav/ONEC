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

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            StaticResources.main.frameContent.Content = new MailEnviado();
        }
    }
}
