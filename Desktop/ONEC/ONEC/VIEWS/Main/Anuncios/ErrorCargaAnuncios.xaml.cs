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

namespace ONEC.VIEWS.Main.Anuncios
{
    /// <summary>
    /// Lógica de interacción para ErrorCargaAnuncios.xaml
    /// </summary>
    public partial class ErrorCargaAnuncios : Page
    {
        Principal principal;
        public ErrorCargaAnuncios(Principal principal)
        {
            InitializeComponent();
            this.principal = principal;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            principal.mainFrame.Content = new AnunciosLoader(principal);
        }
    }
}
