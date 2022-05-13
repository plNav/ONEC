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
    /// Lógica de interacción para AnuncioEmpresarioBuscar.xaml
    /// </summary>
    public partial class AnuncioEmpresarioBuscar : Page
    {
        Principal principal;
        List<string> idsAnuncios;
        List<Anuncio> anunciosEncontrados;
        string campo;
        
        public AnuncioEmpresarioBuscar(Principal principal, List<string> ids, string campo)
        {
            InitializeComponent();
            this.principal = principal;
            this.campo = campo;
            idsAnuncios = ids;
            if (!String.IsNullOrEmpty(campo) && !String.IsNullOrWhiteSpace(campo))
            {
                resetViews();
                Loading.Loading loading = new Loading.Loading();
                try
                {
                    loading.Show();
                    buscarAnuncios(ids, campo).ContinueWith(anunciosBuscados =>
                    {
                       if(anunciosBuscados.Result != null && anunciosBuscados.Result.Count() > 0)
                        {
                            obtenerPuntuacionAnuncios(anunciosBuscados.Result).ContinueWith(puntuaciones =>
                            {
                                loading.Close();
                                pintarAnuncios(anunciosBuscados.Result, puntuaciones.Result);
                            }, TaskScheduler.FromCurrentSynchronizationContext());
                        }else
                        {
                            loading.Close();
                            rowMsjPrincipal.Height = new GridLength(1, GridUnitType.Star);
                            txtMsj.Visibility = Visibility.Visible;
                        }
                    }, TaskScheduler.FromCurrentSynchronizationContext());
                    
                }catch(Exception ex)
                {
                    loading.Close();
                    rowMsjPrincipal.Height = new GridLength(1, GridUnitType.Star);
                    txtMsj.Visibility = Visibility.Visible;
                    ErrorPopUp err = new ErrorPopUp("Error al buscar los anuncios");
                    err.ShowDialog();
                }
            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new AnunciosLoader(principal);
        }

        private async void Image_MouseLeftButtonDown_1(object sender, MouseButtonEventArgs e)
        {
            resetViews();
            string campo = txtBusqueda.Text.Trim();
            if (!String.IsNullOrEmpty(campo) && !String.IsNullOrWhiteSpace(campo))
            {
                //Recorrer array que tengo arriba de strings que son los ids de los anuncios guardados
                this.campo = campo;
                Loading.Loading loading = new Loading.Loading();
                try
                {
                    loading.Show();
                    anunciosEncontrados = await Anuncio.buscarAnuncios(campo);
                    if (anunciosEncontrados == null || anunciosEncontrados.Count <= 0)
                    {
                        //No se ha encontrado ningún anuncio que coincida con la búsqueda
                        rowNoEncontrado.Height = new GridLength(1, GridUnitType.Star);
                        txtNoEncontrado.Visibility = Visibility.Visible;
                        loading.Close();
                    }
                    else
                    {
                        //La búsqueda ha encontrado anuncios
                        if (idsAnuncios.Count() > 0)
                        {
                            List<Anuncio> anunciosMostrar = new List<Anuncio>();
                            foreach(Anuncio anu in anunciosEncontrados)
                            {
                                if (!idsAnuncios.Contains(anu._id)) anunciosMostrar.Add(anu);
                            }
                            if(anunciosMostrar.Count() > 0)
                            {
                                List<float> puntuaciones = await obtenerPuntuacionAnuncios(anunciosMostrar);
                                pintarAnuncios(anunciosMostrar, puntuaciones);
                                loading.Close();
                            }else
                            {
                                rowNoEncontrado.Height = new GridLength(1, GridUnitType.Star);
                                txtNoEncontrado.Visibility = Visibility.Visible;
                                loading.Close();
                            }
                        }
                        else
                        {
                            List<float> puntuaciones = await obtenerPuntuacionAnuncios(anunciosEncontrados);
                            pintarAnuncios(anunciosEncontrados, puntuaciones);
                            loading.Close();
                        }
                    }
                }
                catch (Exception ex)
                {
                    loading.Close();
                    rowMsjPrincipal.Height = new GridLength(1, GridUnitType.Star);
                    txtMsj.Visibility = Visibility.Visible;
                    ErrorPopUp err = new ErrorPopUp("Error al buscar anuncios\npor favor inténtelo más tarde.");
                    err.ShowDialog();
                }
            }
        }

        private async void pintarAnuncios(List<Anuncio> anuncios, List<float>puntuaciones)
        {
            rowPanel.Height = new GridLength(1, GridUnitType.Star);
            stackAnuncios.Visibility = Visibility.Visible;
            for (int x = 0; x < anuncios.Count(); x++)
            {
                Grid grid = new Grid
                {
                    Cursor = Cursors.Hand,
                    Tag = x
                };


                grid.MouseLeftButtonDown += async (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                {
                    Loading.Loading loading = new Loading.Loading();
                    try
                    {
                        loading.Show();
                        List<Visualizaciones> visualizaciones = await Visualizaciones.obtenerVisualizacionesUsuarioAnuncio(anuncios[int.Parse(grid.Tag.ToString())]._id, Usuario.usuarioActual._id);
                        if (visualizaciones.Count() <= 0)
                        {
                            Anuncio anuncio = new Anuncio(anuncios[int.Parse(grid.Tag.ToString())].id_user, anuncios[int.Parse(grid.Tag.ToString())].categoria, anuncios[int.Parse(grid.Tag.ToString())].nombre, anuncios[int.Parse(grid.Tag.ToString())].descripcion, anuncios[int.Parse(grid.Tag.ToString())].precio, anuncios[int.Parse(grid.Tag.ToString())].precioPorHora,anuncios[int.Parse(grid.Tag.ToString())].numVecesVisto + 1);
                            if(await Anuncio.actualizarAnuncio(anuncios[int.Parse(grid.Tag.ToString())]._id, anuncio))
                            {
                                loading.Close();
                                anuncios[x].numVecesVisto += 1;
                                Usuario usu = await Usuario.obtenerUsuarioId(anuncios[int.Parse(grid.Tag.ToString())].id_user);
                                float puntuacion = await Resenyas.obtenerPuntuacionAnuncio(anuncios[int.Parse(grid.Tag.ToString())]._id);
                                principal.mainFrame.Content = new AnuncioBuscadoDetalles(principal, idsAnuncios ,anuncios[int.Parse(grid.Tag.ToString())], campo,usu.email, puntuacion);
                            }else
                            {
                                loading.Close();
                                ErrorPopUp err = new ErrorPopUp("Error al cargar el anuncio");
                                err.ShowDialog();
                            }
                        }else
                        {
                            //Mostrar detalles de anuncio
                            Usuario usu = await Usuario.obtenerUsuarioId(anuncios[int.Parse(grid.Tag.ToString())].id_user);
                            float puntuacion = await Resenyas.obtenerPuntuacionAnuncio(anuncios[int.Parse(grid.Tag.ToString())]._id);
                            principal.mainFrame.Content = new AnuncioBuscadoDetalles(principal, idsAnuncios, anuncios[int.Parse(grid.Tag.ToString())], campo, usu.email, puntuacion);
                            loading.Close();
                        }
                    }catch (Exception ex)
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al cargar el anuncio");
                        err.ShowDialog();
                    }
                };
                RowDefinition row = new RowDefinition();
                RowDefinition row2 = new RowDefinition();



                Border border = new Border
                {
                    CornerRadius = new CornerRadius(5, 5, 0, 0),
                    Margin = new Thickness(0, 5, 0, 0),
                    Background = (Brush)(new BrushConverter().ConvertFrom("#999dba")),
                    Padding = new Thickness(5)
                };

                StackPanel stackPanel = new StackPanel();
                TextBlock categoria = new TextBlock
                {
                    Text = anuncios[x].categoria,
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                    FontWeight = FontWeights.Bold,
                    FontSize = 17,
                    Margin = new Thickness(0, 0, 0, 3)
                };
                stackPanel.Children.Add(categoria);
                Border separator = new Border
                {
                    Height = 1,
                    CornerRadius = new CornerRadius(2),
                    Background = (Brush)(new BrushConverter().ConvertFrom("#FF646464"))
                };
                stackPanel.Children.Add(separator);
                TextBlock nombre = new TextBlock
                {
                    Text = anuncios[x].nombre,
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                    FontWeight = FontWeights.SemiBold,
                    FontSize = 15,
                    Margin = new Thickness(0, 0, 0, 10)
                };
                stackPanel.Children.Add(nombre);
                TextBlock txtDesc = new TextBlock
                {
                    Text = anuncios[x].descripcion,
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                    FontSize = 15,
                    Margin = new Thickness(5, 0, 0, 10)
                };
                stackPanel.Children.Add(txtDesc);
             
                TextBlock txtPrecio = new TextBlock
                {
                    Text = anuncios[x].precioPorHora ? anuncios[x].precio + "€ Hora" : anuncios[x].precio + "€",
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                    FontSize = 15,
                    Margin = new Thickness(5, 0, 0, 10)
                };
                stackPanel.Children.Add(txtPrecio);

                border.Child = stackPanel;
                grid.RowDefinitions.Add(row);
                grid.RowDefinitions.Add(row2);
                Grid.SetRow(stackPanel, 0);
                grid.Children.Add(border);
                Border border2 = new Border
                {
                    Padding = new Thickness(10),
                    Background = (Brush)(new BrushConverter().ConvertFrom("#266E86")),
                    CornerRadius = new CornerRadius(0, 0, 5, 5),
                    Margin = new Thickness(0, 0, 0, 10)
                };
                Grid gridImgs = new Grid();
                ColumnDefinition col = new ColumnDefinition();
                ColumnDefinition col2 = new ColumnDefinition();
                gridImgs.ColumnDefinitions.Add(col);
                gridImgs.ColumnDefinitions.Add(col2);
                StackPanel stackVis = new StackPanel
                {
                    Orientation = Orientation.Vertical,
                    HorizontalAlignment = HorizontalAlignment.Center,
                    VerticalAlignment = VerticalAlignment.Center
                };
                Image imgVis = new Image
                {
                    Height = 25,
                    Width = 25,
                    Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\numvistas.png")
                };
                RenderOptions.SetBitmapScalingMode(imgVis, BitmapScalingMode.HighQuality);
                stackVis.Children.Add(imgVis);
                TextBlock vis = new TextBlock
                {
                    Text = "Visualizaciones",
                    Foreground = Brushes.White,
                    FontSize = 12
                };
                stackVis.Children.Add(vis);
                TextBlock vistxt = new TextBlock
                {
                    Text = anuncios[x].numVecesVisto.ToString(),
                    Foreground = Brushes.White,
                    FontSize = 12,
                    TextAlignment = TextAlignment.Center
                };
                stackVis.Children.Add(vistxt);
                Grid.SetColumn(stackVis, 0);
                gridImgs.Children.Add(stackVis);

                StackPanel stackVal = new StackPanel
                {
                    Orientation = Orientation.Vertical,
                    HorizontalAlignment = HorizontalAlignment.Center,
                    VerticalAlignment = VerticalAlignment.Center
                };
                Image imgVal = new Image
                {
                    Height = 25,
                    Width = 25,
                    Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstar.png")
                };
                RenderOptions.SetBitmapScalingMode(imgVal, BitmapScalingMode.HighQuality);
                stackVal.Children.Add(imgVal);
                TextBlock val = new TextBlock
                {
                    Text = "Valoración",
                    Foreground = Brushes.White,
                    FontSize = 12
                };
                stackVal.Children.Add(val);
                TextBlock valtxt = new TextBlock
                {
                    Text = puntuaciones[x].ToString(),
                    Foreground = Brushes.White,
                    FontSize = 12,
                    TextAlignment = TextAlignment.Center
                };
                stackVal.Children.Add(valtxt);
                Grid.SetColumn(stackVal, 1);
                gridImgs.Children.Add(stackVal);
                border2.Child = gridImgs;
                Grid.SetRow(border2, 1);
                grid.Children.Add(border2);
                stackAnuncios.Children.Add(grid);

            }
        }

        private void resetViews()
        {
            rowMsjPrincipal.Height = new GridLength(0);
            rowNoEncontrado.Height = new GridLength(0);
            rowPanel.Height = new GridLength(0);
            txtMsj.Visibility = Visibility.Collapsed;
            txtNoEncontrado.Visibility = Visibility.Collapsed;
            stackAnuncios.Visibility = Visibility.Collapsed;
        }


        private async Task<List<Anuncio>> buscarAnuncios(List<string>idsAnuncioFav,string campo)
        {
            List<Anuncio> anunciosEncontrados = await Anuncio.buscarAnuncios(campo);
            if (idsAnuncioFav.Count > 0)
            {
                List<Anuncio> anunciosRetorno = new List<Anuncio>();
               foreach(Anuncio an in anunciosEncontrados)
                {
                    if(!idsAnuncioFav.Contains(an._id))
                    {
                        anunciosRetorno.Add(an);
                    }
                }
                return anunciosRetorno;
                
            }else
            {
                return anunciosEncontrados;
            }
        
        }

        private async Task<List<float>>obtenerPuntuacionAnuncios(List<Anuncio> anuncios)
        {
            List<float> puntuaciones = new List<float>();
            foreach(Anuncio an in anuncios)
            {
                puntuaciones.Add(await Resenyas.obtenerPuntuacionAnuncio(an._id));
            }
            return puntuaciones;
        }

    }
}
