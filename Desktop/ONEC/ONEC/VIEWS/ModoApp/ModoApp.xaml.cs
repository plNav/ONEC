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

namespace ONEC.VIEWS.ModoApp
{
    /// <summary>
    /// Lógica de interacción para ModoApp.xaml
    /// </summary>
    public partial class ModoAppPage : Page
    {
        public ModoAppPage()
        {
            InitializeComponent();
        }

        private void btnBuscador_Click(object sender, RoutedEventArgs e)
        {
            StaticResources.modoEmpresario = true;
            StaticResources.main.frameContent.Content = new Principal();
        }

        private void btnEstandar_Click(object sender, RoutedEventArgs e)
        {
            StaticResources.modoEmpresario = false;  //En la variable estática ya está puesto como false, pero lo ponemos también aquí para prevenir posibles errores.
            StaticResources.main.frameContent.Content = new Principal();
        }
    }
}
