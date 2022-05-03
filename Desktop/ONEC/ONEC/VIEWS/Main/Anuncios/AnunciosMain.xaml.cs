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
using ONEC.VIEWS.Loading;
using ONEC.VIEWS.Main.Anuncios;

namespace ONEC.VIEWS.Main.Anuncios
{
    /// <summary>
    /// Lógica de interacción para AnunciosMain.xaml
    /// </summary>
    public partial class AnunciosMain : Page
    {
        Principal principal;
        public AnunciosMain(Principal principal, List<Anuncio> anuncios)
        {
            InitializeComponent();
            this.principal = principal;
            cargarAnuncios(anuncios);
        }

        private void cargarAnuncios(List<Anuncio>anuncios)
        {
            foreach(Anuncio anuncio in anuncios)
            {
                Border border = new Border
                {
                    Background = (Brush)(new BrushConverter().ConvertFrom("#141624")),
                    CornerRadius = new CornerRadius(7),
                    Margin = new Thickness(0,0,0,20),
                };

                Grid grid = new Grid();
                RowDefinition row = new RowDefinition
                {
                    Height = GridLength.Auto
                };
                RowDefinition row2 = new RowDefinition
                {
                    Height = new GridLength(1,GridUnitType.Star)
                };

                grid.RowDefinitions.Add(row);
                grid.RowDefinitions.Add(row2);

                TextBlock textCategoria = new TextBlock
                {
                    TextWrapping = TextWrapping.NoWrap,
                    FontSize = 15,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#fcffff")),
                    VerticalAlignment = VerticalAlignment.Center,
                    Text = anuncio.categoria,
                    Margin = new Thickness(5,3,0,3),
                    TextTrimming = TextTrimming.WordEllipsis
                };

                Border borderCuerpo = new Border
                {
                    Background = (Brush)(new BrushConverter().ConvertFrom("#fcffff")),
                    CornerRadius = new CornerRadius(0, 0, 7, 7),
                    Padding = new Thickness(0,0,0,10)
                };

                Grid gridCuerpo = new Grid();
                ColumnDefinition col = new ColumnDefinition
                {
                    Width = new GridLength(1, GridUnitType.Star)
                };
                ColumnDefinition col2 = new ColumnDefinition
                {
                    Width = GridLength.Auto
                };
                RowDefinition rowcuerpo = new RowDefinition
                {
                    Height = GridLength.Auto
                };

                RowDefinition rowcuerpo2 = new RowDefinition
                {
                    Height = GridLength.Auto
                };

                RowDefinition rowcuerpo3 = new RowDefinition
                {
                    Height = GridLength.Auto
                };

                RowDefinition rowcuerpo4 = new RowDefinition
                {
                    Height = GridLength.Auto
                };

                StackPanel stackCategoria = new StackPanel
                {
                    VerticalAlignment = VerticalAlignment.Center,
                    Orientation = Orientation.Horizontal,
                    Margin = new Thickness(5,3,0,0)
                };

                TextBlock categoriaTitu = new TextBlock
                {
                    FontSize = 14,
                    Foreground = Brushes.Black,
                    Text = "Categoria"
                };

                TextBlock categoria = new TextBlock
                {
                    FontSize = 14,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#22596B")),
                    TextWrapping = TextWrapping.NoWrap,
                    TextTrimming = TextTrimming.WordEllipsis,
                    Text = anuncio.categoria,
                    Margin = new Thickness(3,0,0,0)
                };

                StackPanel stackPrecio = new StackPanel
                {
                    VerticalAlignment = VerticalAlignment.Center,
                    Orientation = Orientation.Horizontal,
                    Margin = new Thickness(5, 3, 0, 0)
                };

                TextBlock precioTitu = new TextBlock
                {
                    FontSize = 14,
                    Foreground = Brushes.Black,
                    Text = "Precio"
                };

                TextBlock precio = new TextBlock
                {
                    FontSize = 14,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#22596B")),
                    TextWrapping = TextWrapping.NoWrap,
                    TextTrimming = TextTrimming.WordEllipsis,
                    Text = anuncio.precioPorHora ? anuncio.precio + "€ Hora" : anuncio.precio +"€",
                    Margin = new Thickness(3, 0, 0, 0)
                };

                StackPanel stackDesc = new StackPanel
                {
                    VerticalAlignment = VerticalAlignment.Center,
                    Orientation = Orientation.Horizontal,
                    Margin = new Thickness(5, 3, 0, 0)
                };

                TextBlock descTitu = new TextBlock
                {
                    FontSize = 14,
                    Foreground = Brushes.Black,
                    Text = "Descripción"
                };

                TextBlock descripcion = new TextBlock
                {
                    FontSize = 14,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#22596B")),
                    TextWrapping = TextWrapping.NoWrap,
                    TextTrimming = TextTrimming.WordEllipsis,
                    Text = anuncio.descripcion,
                    Margin = new Thickness(3, 0, 0, 0)
                };

               
                Image borrar = new Image
                {
                    Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\delete.png"),
                    Height = 40,
                    Width = 40,
                    VerticalAlignment = VerticalAlignment.Center,
                    Margin = new Thickness(0, 0, 2, 0),
                    Cursor = Cursors.Hand
                };

                RenderOptions.SetBitmapScalingMode(borrar, BitmapScalingMode.HighQuality);

                TextBlock detalles = new TextBlock
                {
                    FontSize = 15,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#1a4654")),
                    Text = "Más detalles...",
                    Cursor = Cursors.Hand,
                    Margin = new Thickness(5, 3, 0, 0),
                    VerticalAlignment = VerticalAlignment.Center,
                    HorizontalAlignment = HorizontalAlignment.Left,
                    FontWeight = FontWeights.Bold
                };

                detalles.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                {
                    principal.mainFrame.Content = new EditarAnuncio(anuncio, principal);
                };

                borrar.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                {
                    eliminarAnuncio(border, anuncio);
                };

                gridCuerpo.ColumnDefinitions.Add(col);
                gridCuerpo.ColumnDefinitions.Add(col2);
                gridCuerpo.RowDefinitions.Add(rowcuerpo);
                gridCuerpo.RowDefinitions.Add(rowcuerpo2);
                gridCuerpo.RowDefinitions.Add(rowcuerpo3);
                gridCuerpo.RowDefinitions.Add(rowcuerpo4);


                stackCategoria.Children.Add(categoriaTitu);
                stackCategoria.Children.Add(categoria);
                stackPrecio.Children.Add(precioTitu);
                stackPrecio.Children.Add(precio);
                stackDesc.Children.Add(descTitu);
                stackDesc.Children.Add(descripcion);

                Grid.SetColumn(stackCategoria, 0);
                Grid.SetColumn(stackPrecio, 0);
                Grid.SetColumn(stackDesc, 0);
                Grid.SetColumn(borrar, 1);
                Grid.SetColumn(detalles, 0);
                Grid.SetRow(detalles, 3);
                Grid.SetRow(stackCategoria, 0);
                Grid.SetRow(stackPrecio, 1);
                Grid.SetRow(stackDesc, 2);
                Grid.SetRow(borrar, 0);
                Grid.SetRowSpan(borrar, 4);
                gridCuerpo.Children.Add(stackCategoria);
                gridCuerpo.Children.Add(stackPrecio);
                gridCuerpo.Children.Add(stackDesc);
                gridCuerpo.Children.Add(borrar);
                gridCuerpo.Children.Add(detalles);

                borderCuerpo.Child = gridCuerpo;

                Grid.SetRow(textCategoria, 0);
                Grid.SetRow(borderCuerpo, 1);
                grid.Children.Add(textCategoria);
                grid.Children.Add(borderCuerpo);
                border.Child = grid;
                panelAnuncios.Children.Add(border);
            }
        }

        private void cargarAnuncioSelect(Anuncio anuncio)
        {
            //Abrimos la ventana de detalles del anuncio
            MessageBox.Show("Abre anuncio");
        }

        private async void eliminarAnuncio(Border border, Anuncio anuncio)
        {
            Loading.Loading loading = new Loading.Loading();
            loading.Show();
            try
            {
                //Primero eliminamos las reseñas
                if (await Resenyas.eliminarResenya(anuncio._id))
                {
                    if (await AnunciosGuardados.eliminarAnunciosGuardadosDeUnAnuncio(anuncio._id))
                    {
                        if(await Visualizaciones.eliminarVisualizacionesAnuncio(anuncio._id))
                        {
                            if(await Anuncio.eliminarAnuncio(anuncio._id))
                            {
                                panelAnuncios.Children.Remove(border);
                                loading.Close();
                            }else
                            {
                                loading.Close();
                                ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                                err.ShowDialog();
                            }

                        }else
                        {
                            loading.Close();
                            ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                            err.ShowDialog();
                        }
                    }else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                        err.ShowDialog();
                    }
                }else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                    err.ShowDialog();
                }
            }
            catch (Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al eliminar el anuncio");
                err.ShowDialog();
            }

        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new CrearAnuncio(principal);
        }
    }
}
