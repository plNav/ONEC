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

namespace ONEC.VIEWS.Main.CV
{
    /// <summary>
    /// Lógica de interacción para ErrorCargaCV.xaml
    /// </summary>
    public partial class ErrorCargaCV : Page
    {
        Principal principal;
        public ErrorCargaCV(Principal principal)
        {
            InitializeComponent();
            this.principal = principal;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            principal.mainFrame.Content = new CVLoader(principal);
        }
    }
}
