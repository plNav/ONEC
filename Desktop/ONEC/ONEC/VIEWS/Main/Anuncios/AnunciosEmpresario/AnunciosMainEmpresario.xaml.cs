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
    /// Lógica de interacción para AnunciosMainEmpresario.xaml
    /// </summary>
    public partial class AnunciosMainEmpresario : Page
    {
        Principal principal;
        List<AnunciosGuardados> anunciosGuardados;
        public AnunciosMainEmpresario(Principal principal, List<Anuncio> anuncios, List<Usuario> usuarios, List<float> puntuaciones, List<AnunciosGuardados> anunciosG)
        {
            InitializeComponent();
            this.principal = principal;
            this.anunciosGuardados = anunciosG;
            cargarAnunciosFav(principal, anuncios, usuarios, puntuaciones, anunciosG);
        }

        private void cargarAnunciosFav(Principal principal, List<Anuncio> anuncios, List<Usuario> usuarios, List<float> puntuaciones, List<AnunciosGuardados> anunciosG)
        {
            for(int x = 0; x < anuncios.Count(); x++)
            {
                Grid grid = new Grid
                {
                    Cursor = Cursors.Hand,
                    Tag = x
                };


                grid.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                {
                    //cargamos vista de cargar anuncio
                    principal.mainFrame.Content = new AnuncioEmpresarioLoader(principal, anunciosG[int.Parse(grid.Tag.ToString())], anunciosG);
                };
                RowDefinition row = new RowDefinition();
                RowDefinition row2 = new RowDefinition();



                Border border = new Border
                {
                    CornerRadius = new CornerRadius(5,5,0,0),
                    Margin = new Thickness(0,5,0,0),
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
                    Margin = new Thickness(0,0,0,3)
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
                TextBlock descTitu = new TextBlock
                {
                    Text = "Descripción",
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#E0E3EB")),
                    FontSize = 14,
                    Margin = new Thickness(0, 0, 0, 3)
                };
                stackPanel.Children.Add(descTitu);
                TextBlock txtDesc = new TextBlock
                {
                    Text = anuncios[x].descripcion,
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                    FontSize = 15,
                    Margin = new Thickness(5, 0, 0, 10)
                };
                stackPanel.Children.Add(txtDesc);
                TextBlock precioTitu = new TextBlock
                {
                    Text = "Precio",
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#E0E3EB")),
                    FontSize = 14,
                    Margin = new Thickness(0, 0, 0, 3)
                };
                stackPanel.Children.Add(precioTitu);
                TextBlock txtPrecio = new TextBlock
                {
                    Text = anuncios[x].precioPorHora ? anuncios[x].precio + "€ Hora" : anuncios[x].precio + "€",
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                    FontSize = 15,
                    Margin = new Thickness(5, 0, 0, 10)
                };
                stackPanel.Children.Add(txtPrecio);
                TextBlock correoTitu = new TextBlock
                {
                    Text = "Correo",
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#E0E3EB")),
                    FontSize = 14,
                    Margin = new Thickness(0, 0, 0, 3)
                };
                stackPanel.Children.Add(correoTitu);
                TextBlock txtCorreo = new TextBlock
                {
                    Text = usuarios[x].email,
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                    FontSize = 15,
                    Margin = new Thickness(5, 0, 0, 10)
                };
                stackPanel.Children.Add(txtCorreo);
                border.Child = stackPanel;
                grid.RowDefinitions.Add(row);
                grid.RowDefinitions.Add(row2);
                Grid.SetRow(stackPanel, 0);
                grid.Children.Add(border);
                Border border2 = new Border
                {
                    Padding = new Thickness(10),
                    Background = (Brush)(new BrushConverter().ConvertFrom("#266E86")),
                    CornerRadius = new CornerRadius(0,0,5,5),
                    Margin = new Thickness(0,0,0,10)
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
                panelAnuncios.Children.Add(grid);

            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            List<string> ids = anunciosGuardados.Select(an => an.id_anuncio).ToList();
            principal.mainFrame.Content = new AnuncioEmpresarioBuscar(principal, ids, null);
        }
    }
}
