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
using ONEC.API_MODELS;
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Anuncios.AnunciosEmpresario
{
    /// <summary>
    /// Lógica de interacción para ErrorCargarAnuncioEmpresario.xaml
    /// </summary>
    public partial class ErrorCargarAnuncioEmpresario : Page
    {
        Principal principal;
        AnunciosGuardados anuncio;
        List<AnunciosGuardados> anunciosG;
        public ErrorCargarAnuncioEmpresario(Principal principal, AnunciosGuardados anuncio, List<AnunciosGuardados> anunciosG)
        {
            InitializeComponent();
            this.principal = principal;
            this.anuncio = anuncio;
            this.anunciosG = anunciosG;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            principal.mainFrame.Content = new AnuncioEmpresarioLoader(principal, anuncio, anunciosG);
        }
    }
}
