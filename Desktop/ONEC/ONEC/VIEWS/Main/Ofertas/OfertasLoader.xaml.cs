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

namespace ONEC.VIEWS.Main.Ofertas
{
    /// <summary>
    /// Lógica de interacción para OfertasLoader.xaml
    /// </summary>
    public partial class OfertasLoader : Page
    {
        public OfertasLoader(Principal principal)
        {
            InitializeComponent();
            cargarOfertas(principal);
        }

        private async void cargarOfertas(Principal principal)
        {
            try
            {
                List<API_MODELS.Ofertas> ofertas = await API_MODELS.Ofertas.obtenerOfertasUsuario(Usuario.usuarioActual._id);
                principal.mainFrame.Content = new OfertasMain(principal, ofertas);
            }catch(Exception ex)
            {
                principal.mainFrame.Content = new ErrorCargaOfertas(principal);
            }
        }
    }
}
