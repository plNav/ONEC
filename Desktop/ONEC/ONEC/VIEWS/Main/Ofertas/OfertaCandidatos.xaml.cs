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

namespace ONEC.VIEWS.Main.Ofertas
{
    /// <summary>
    /// Lógica de interacción para OfertaCandidatos.xaml
    /// </summary>
    public partial class OfertaCandidatos : Page
    {
        Principal principal;
        public List<API_MODELS.Ofertas> ofertas;
        API_MODELS.Ofertas oferta;
        public List<API_MODELS.CV> cvs;
        public List<CandidatosOfertas> candidatos;
        public OfertaCandidatos(Principal principal, List<API_MODELS.Ofertas> ofertas, API_MODELS.Ofertas oferta, List<API_MODELS.CV> cvs, List<CandidatosOfertas> candidatos)
        {
            InitializeComponent();
            this.principal = principal;
            this.ofertas = ofertas;
            this.oferta = oferta;
            this.cvs = cvs;
            this.candidatos = candidatos;
            pintarCandidatos(cvs, candidatos);
        }

        public void pintarCandidatos(List<API_MODELS.CV> cvs, List<CandidatosOfertas> candidatos)
        {
            if (cvs.Count > 0)
            {
                rowTxt.Height = new GridLength(0);
                txtCandidatos.Visibility = Visibility.Collapsed;
                rowStack.Height = new GridLength(1, GridUnitType.Star);
                borderStack.Visibility = Visibility.Visible;
                stackCandidatos.Visibility = Visibility.Visible;


                foreach (API_MODELS.CV cv in cvs)
                {
                    Border border = new Border
                    {
                        Padding = new Thickness(10),
                        CornerRadius = new CornerRadius(5),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#EDEEFF")),
                        Margin = new Thickness(0,5,0,5)
                    };

                    Grid grid = new Grid();

                    ColumnDefinition col1 = new ColumnDefinition();
                    ColumnDefinition col2 = new ColumnDefinition
                    {
                        Width = GridLength.Auto
                    };

                    grid.ColumnDefinitions.Add(col1);
                    grid.ColumnDefinitions.Add(col2);

                    StackPanel stack = new StackPanel();

                    TextBlock nombre = new TextBlock
                    {
                        Text = cv.nombre,
                        FontSize = 19,
                        TextTrimming = TextTrimming.WordEllipsis,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#1B1B27")),
                        Margin = new Thickness(0, 0, 0, 3)
                    };
                    stack.Children.Add(nombre);

                    Border separator = new Border
                    {
                        Height = 2,
                        CornerRadius = new CornerRadius(3),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#BCBDD8")),
                        Margin = new Thickness(0,0,0,10)
                    };
                    stack.Children.Add(separator);

                    TextBlock tituTitulacion = new TextBlock
                    {
                        Text = "Titulación",
                        FontWeight = FontWeights.SemiBold,
                        FontSize = 15,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#1D1C1C")),
                        Margin = new Thickness(0,0,0,3)
                    };
                    stack.Children.Add(tituTitulacion);

                    TextBlock titulo = new TextBlock
                    {
                        Text = cv.especialidad != null ? cv.especialidad : cv.titulo != null ? cv.titulo : "Sin titulación",
                        FontSize = 16,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#215A77")),
                        Margin = new Thickness(0, 0, 0, 5)
                    };
                    
                    stack.Children.Add(titulo);
                    TextBlock tituExp = new TextBlock
                    {
                        Text = "Experiencia",
                        FontWeight = FontWeights.SemiBold,
                        FontSize = 15,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#1D1C1C")),
                        Margin = new Thickness(0, 0, 0, 3)
                    };
                    stack.Children.Add(tituExp);

                    TextBlock exp = new TextBlock
                    {
                        Text = cv.experiencia > 0 ? cv.experiencia.ToString() + " años." : "Sin experiencia.",
                        FontSize = 16,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#215A77")),
                        Margin = new Thickness(0,0,0,8)
                    };
                    stack.Children.Add(exp);

                    TextBlock verMas = new TextBlock
                    {
                        Text = "Ver más...",
                        FontSize = 17,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#151721")),
                        FontWeight = FontWeights.Bold,
                        Cursor = Cursors.Hand
                    };

                    verMas.MouseLeftButtonDown += async (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                    {
                        Loading.Loading loading = new Loading.Loading();
                        try
                        {
                            loading.Show();
                            Usuario u = await Usuario.obtenerUsuarioId(cv.id_user);
                            loading.Close();
                            principal.mainFrame.Content = new OfertaDetallesCandidato(principal, ofertas, oferta, cvs, candidatos, cv, u.email, candidatos[cvs.IndexOf(cv)]);
                        }
                        catch (Exception ex)
                        {
                            loading.Close();
                            ErrorPopUp err = new ErrorPopUp("Error al cargar candidato");
                            err.ShowDialog();
                        }
                      
                    };

                    stack.Children.Add(verMas);
                    Grid.SetColumn(stack, 0);
                    grid.Children.Add(stack);

                    Image borrar = new Image
                    {
                        Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\delete.png"),
                        Height = 40,
                        Width = 40,
                        VerticalAlignment = VerticalAlignment.Center,
                        Margin = new Thickness(10, 0, 10, 0),
                        Cursor = Cursors.Hand
                    };

                    borrar.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                    {
                        eliminarCandidato(cv, candidatos[cvs.IndexOf(cv)], cvs, candidatos);
                    };
                    Grid.SetColumn(borrar, 1);
                    grid.Children.Add(borrar);
                    border.Child = grid;
                    stackCandidatos.Children.Add(border);
                }
                
            }else
            {
                rowTxt.Height = new GridLength(1, GridUnitType.Star);
                txtCandidatos.Visibility = Visibility.Visible;
                rowStack.Height = new GridLength(0);
                borderStack.Visibility = Visibility.Collapsed;
                stackCandidatos.Visibility = Visibility.Collapsed;
            }
        }

        private async void eliminarCandidato(API_MODELS.CV cv, CandidatosOfertas candidatosOfertas, List<API_MODELS.CV> cvs, List<CandidatosOfertas> candidatos)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                if(await CandidatosOfertas.eliminarCandidatoID(candidatosOfertas._id))
                {
                    loading.Close();
                    cvs.Remove(cv);
                    candidatos.Remove(candidatosOfertas);
                    stackCandidatos.Children.Clear();
                    pintarCandidatos(cvs, candidatos);
                }else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al eliminar candidato");
                    err.ShowDialog();
                }
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al eliminar candidato");
                err.ShowDialog();
            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new OfertaDetalles(principal, ofertas, oferta);
        }

        private void Image_MouseLeftButtonDown_1(object sender, MouseButtonEventArgs e)
        {
            BuscarCandidatosOferta buscar = new BuscarCandidatosOferta(oferta, this);
            buscar.ShowDialog();
        }
    }
}
