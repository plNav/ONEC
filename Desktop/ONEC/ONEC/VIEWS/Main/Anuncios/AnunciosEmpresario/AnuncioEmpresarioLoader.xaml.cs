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

namespace ONEC.VIEWS.Main.Anuncios.AnunciosEmpresario
{
    /// <summary>
    /// Lógica de interacción para AnuncioEmpresarioLoader.xaml
    /// </summary>
    public partial class AnuncioEmpresarioLoader : Page
    {
        public AnuncioEmpresarioLoader(Principal principal, AnunciosGuardados anuncioGuardado, List<AnunciosGuardados> anunciosGuardados)
        {
            InitializeComponent();
            obtenerDetallesAnuncio(principal, anuncioGuardado, anunciosGuardados);
        }

        private async void obtenerDetallesAnuncio(Principal principal, AnunciosGuardados anuncioG, List<AnunciosGuardados> anunciosGuardados)
        {
            
            try
            {
                Anuncio anuncio = await Anuncio.obtenerAnuncioId(anuncioG.id_anuncio);
                Usuario user = await Usuario.obtenerUsuarioId(anuncioG.id_user);
                float puntuacion = await Resenyas.obtenerPuntuacionAnuncio(anuncioG.id_anuncio);
                List<Resenyas> reviewsUsuario = await Resenyas.obtenerResenyasUsuarioAnuncio(anuncioG.id_anuncio, anuncioG.id_user);
                principal.mainFrame.Content = new AnuncioGuardadoDetalles(principal, anuncio, user, puntuacion, reviewsUsuario, anuncioG, anunciosGuardados);
            }catch(Exception e)
            {
                principal.mainFrame.Content = new ErrorCargarAnuncioEmpresario(principal, anuncioG, anunciosGuardados);
            }
        }
    }
}
