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
using ONEC.VIEWS.Main.Anuncios.AnunciosEmpresario;

namespace ONEC.VIEWS.Main.Anuncios
{
    /// <summary>
    /// Lógica de interacción para AnunciosLoader.xaml
    /// </summary>
    public partial class AnunciosLoader : Page
    {
       
        public AnunciosLoader(Principal principal)
        {
            InitializeComponent();
            cargarAnuncios(principal);
        }

        public async void cargarAnuncios(Principal principal)
        {
            if(StaticResources.modoEmpresario)
            {
                //Cargamos Anuncios Guardados
                try
                {
                    List<AnunciosGuardados> anunciosG = await AnunciosGuardados.obtenerAnunciosGuardadosUsuarioID(Usuario.usuarioActual._id);
                    List<Anuncio> anuncios = new List<Anuncio>();
                    List<Usuario> usuarios = new List<Usuario>();
                    List<float> puntuaciones = new List<float>();
                    foreach(AnunciosGuardados anuncio in anunciosG)
                    {
                       anuncios.Add(await Anuncio.obtenerAnuncioId(anuncio.id_anuncio));
                       usuarios.Add(await Usuario.obtenerUsuarioId(anuncio.id_user));
                       puntuaciones.Add(await Resenyas.obtenerPuntuacionAnuncio(anuncio.id_anuncio));
                    }
                    principal.mainFrame.Content = new AnunciosMainEmpresario(principal, anuncios, usuarios, puntuaciones);
                }
                catch (Exception ex)
                {
                    principal.mainFrame.Content = new ErrorCargaAnuncios(principal);
                }
            }else
            {
                //Cargamos los anuncios del usuario
                try
                {
                      List<Anuncio> anuncios = await Anuncio.obtenerAnunciosUsuario(Usuario.usuarioActual._id);
                    //los anuncios se cargan y los pasamos a otra ventana, en la que gestionamos si la lista está vacía o no
                    principal.mainFrame.Content = new AnunciosMain(principal, anuncios);
                }catch(Exception ex)
                {
                    //Mostramos el error
                    principal.mainFrame.Content = new ErrorCargaAnuncios(principal);
                }
            }
        }
    }
}
