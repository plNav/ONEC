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
using ONEC.VIEWS.Loading;
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Ofertas
{
    /// <summary>
    /// Lógica de interacción para OfertasMain.xaml
    /// </summary>
    public partial class OfertasMain : Page
    {
        Principal principal;
        List<API_MODELS.Ofertas> ofertas;
        public OfertasMain(Principal principal, List<API_MODELS.Ofertas> ofertas)
        {
            InitializeComponent();
            this.principal = principal;
            this.ofertas = ofertas;
            pintarOfertas(this.principal, this.ofertas);
        }

        private void pintarOfertas(Principal principal, List<API_MODELS.Ofertas> ofertas)
        {
            if (ofertas.Count() > 0)
            {

                //Primero ocultamos el row que contiene el mensaje que se muestra, en caso de que no exista ninguna oferta
                rowNocreado.Height = new GridLength(0);
                txtNoCreado.Visibility = Visibility.Collapsed;
                rowPanel.Height = new GridLength(98, GridUnitType.Star);

                //Una vez actualizada la vista, pintamos los elementos
                foreach(API_MODELS.Ofertas oferta in ofertas)
                {
                    Border border = new Border
                    {
                        Background = (Brush)(new BrushConverter().ConvertFrom("#E1E3EE")),
                        CornerRadius = new CornerRadius(7),
                        Padding = new Thickness(10),
                        Margin = new Thickness(0,5,0,5),
                        Tag = oferta._id
                    };

                    Grid grid = new Grid();

                    ColumnDefinition col1 = new ColumnDefinition();
                    ColumnDefinition col2 = new ColumnDefinition
                    {
                        Width = GridLength.Auto
                    };
                    grid.ColumnDefinitions.Add(col1);
                    grid.ColumnDefinitions.Add(col2);
                    StackPanel st = new StackPanel();
                    TextBlock nombre = new TextBlock
                    {
                        Text = oferta.nombre,
                        FontSize = 19,
                        FontWeight = FontWeights.Bold,
                        TextTrimming = TextTrimming.WordEllipsis,
                        Margin = new Thickness(0,0,0,3),
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),

                    };
                    st.Children.Add(nombre);
                    Border separator = new Border
                    {
                        Height = 2,
                        Background = (Brush)(new BrushConverter().ConvertFrom("#404246")),
                        CornerRadius = new CornerRadius(3)
                    };
                    st.Children.Add(separator);
                    TextBlock tituTitul = new TextBlock
                    {
                        Text = "Titulación",
                        FontSize = 15,
                        FontWeight = FontWeights.SemiBold,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#434C5E")),
                        Margin = new Thickness(0,10,0,2)
                    };
                    st.Children.Add(tituTitul);
                    TextBlock titulo = new TextBlock
                    {
                        Text = oferta.especialidad != null ? oferta.especialidad : oferta.titulo != null ? oferta.titulo : "No requiere",
                        FontSize = 16,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#6E81A5")),
                        Margin = new Thickness(0, 0, 0, 5)
                    };
                    st.Children.Add(titulo);
                    TextBlock tituExp = new TextBlock
                    {
                        Text = "Experiencia",
                        FontSize = 15,
                        FontWeight = FontWeights.SemiBold,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#434C5E")),
                        Margin = new Thickness(0, 0, 0, 2)
                    };
                    st.Children.Add(tituExp);
                    TextBlock experiencia = new TextBlock
                    {
                        Text = oferta.experiencia > 0 ? oferta.experiencia.ToString() + " años." : "No requiere", 
                        FontSize = 16,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#6E81A5"))
                    };
                    st.Children.Add(experiencia);
                    TextBlock ver = new TextBlock
                    {
                        Text = "Ver oferta...",
                        FontSize = 18,
                        FontWeight = FontWeights.Bold,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#232936")),
                        VerticalAlignment = VerticalAlignment.Top,
                        HorizontalAlignment = HorizontalAlignment.Left,
                        Cursor = Cursors.Hand,
                        Margin = new Thickness(0, 5, 0, 0)
                    };

                    ver.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                    {
                        principal.mainFrame.Content = new OfertaDetalles(principal, ofertas, oferta);
                    };

                    st.Children.Add(ver);
                    Grid.SetColumn(st, 0);
                    grid.Children.Add(st);
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
                        eliminarOferta(principal, ofertas, oferta);
                    };


                    RenderOptions.SetBitmapScalingMode(borrar, BitmapScalingMode.HighQuality);
                    Grid.SetColumn(borrar, 1);
                    grid.Children.Add(borrar);
                    border.Child = grid;
                    panelOfertas.Children.Add(border);
                }
            }else
            {
                rowPanel.Height = new GridLength(0);
                panelOfertas.Visibility = Visibility.Collapsed;
                rowNocreado.Height = new GridLength(1, GridUnitType.Star);
                txtNoCreado.Visibility = Visibility.Visible;
            }
        }

        private async void eliminarOferta(Principal principal, List<API_MODELS.Ofertas> ofertas, API_MODELS.Ofertas oferta)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                if (await API_MODELS.CandidatosOfertas.eliminarCandidatosOferta(oferta._id))
                {
                    if (await API_MODELS.Ofertas.eliminarOferta(oferta._id))
                    {
                        loading.Close();
                        ofertas.Remove(oferta);
                        panelOfertas.Children.Clear();
                        pintarOfertas(principal, ofertas);
                    }
                    else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al eliminar oferta");
                        err.ShowDialog();
                    }
                }else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al eliminar oferta");
                    err.ShowDialog();
                }
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al eliminar oferta");
                err.ShowDialog();
            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new CrearOfertas(principal, ofertas);
        }
    }
}
