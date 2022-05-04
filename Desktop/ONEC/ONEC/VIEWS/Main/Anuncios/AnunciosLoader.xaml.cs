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
        Principal prin;
        public AnunciosLoader(Principal principal)
        {
            InitializeComponent();
            this.prin = principal;
            cargarAnuncios(prin);
        }

        public async void cargarAnuncios(Principal principal)
        {
            if(StaticResources.modoEmpresario)
            {
                //Cargamos Anuncios Guardados
                try
                {

                    List<AnunciosGuardados> anunciosG = await AnunciosGuardados.obtenerAnunciosGuardadosUsuarioID(Usuario.usuarioActual._id);
                    List<Anuncio> anuncios = await obtenerAnuncios(anunciosG);
                    List<Usuario> usuarios = await obtenerUsuarios(anunciosG);
                    List<float> puntuaciones = await obtenerPuntuaciones(anunciosG);
                    principal.mainFrame.Content = new AnunciosMainEmpresario(anuncios, usuarios, puntuaciones);
                    //principal.mainFrame.Content = new AnunciosMain(principal, anuncios);
                }
                catch (Exception ex)
                {
                    principal.mainFrame.Content = new ErrorCargaAnuncios(principal);
                    MessageBox.Show(ex.Message);
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


        //Funciones asíncronas que recorren los anuncios y nos devuelven las listas necesarias
        private async Task<List<Anuncio>> obtenerAnuncios(List<AnunciosGuardados> anuncios)
        {
            List<Anuncio> lista = new List<Anuncio>();
            foreach(AnunciosGuardados an in anuncios)
            {
                lista.Add(await Anuncio.obtenerAnuncioId(an.id_anuncio));
            }
            return lista;
        }

        private async Task<List<Usuario>> obtenerUsuarios(List<AnunciosGuardados> anuncios)
        {
            List<Usuario> usuarios = new List<Usuario>();
            foreach (AnunciosGuardados an in anuncios)
            {
                usuarios.Add(await Usuario.obtenerUsuarioId(an.id_user));
            }
            return usuarios;
        }

        private async Task<List<float>> obtenerPuntuaciones(List<AnunciosGuardados> anuncios)
        {
            List<float> puntuaciones = new List<float>();
            foreach (AnunciosGuardados an in anuncios)
            {
                puntuaciones.Add(await Resenyas.obtenerPuntuacionAnuncio(an.id_anuncio));
            }
            return puntuaciones;
        }
    }
}
