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

namespace ONEC.VIEWS.Main.Ofertas
{
    /// <summary>
    /// Lógica de interacción para ErrorCargaOfertas.xaml
    /// </summary>
    public partial class ErrorCargaOfertas : Page
    {
        Principal principal;
        public ErrorCargaOfertas(Principal principal)
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            principal.mainFrame.Content = new OfertasLoader(principal);
        }
    }
}
