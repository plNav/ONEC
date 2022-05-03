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

namespace ONEC.VIEWS.Main.Anuncios
{
    /// <summary>
    /// Lógica de interacción para EditarAnuncio.xaml
    /// </summary>
    public partial class EditarAnuncio : Page
    {
        Anuncio anuncio;
        Principal principal;
        public EditarAnuncio(Anuncio anuncio, Principal principal)
        {
            InitializeComponent();
            this.anuncio = anuncio;
            this.principal = principal;
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new AnunciosMain(principal, Anuncio.anunciosUsuario);
        }
    }
}
