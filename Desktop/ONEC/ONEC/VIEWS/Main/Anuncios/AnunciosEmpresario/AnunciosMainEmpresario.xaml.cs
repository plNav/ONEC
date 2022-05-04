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
        public AnunciosMainEmpresario(Principal principal, List<Anuncio> anuncios, List<Usuario> usuarios, List<float> puntuaciones)
        {
            InitializeComponent();
            cargarAnunciosFav(principal, anuncios, usuarios, puntuaciones);
        }

        private void cargarAnunciosFav(Principal principal, List<Anuncio> anuncios, List<Usuario> usuarios, List<float> puntuaciones)
        {
            for(int x = 0; x < anuncios.Count(); x++)
            {
                Grid grid = new Grid();
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
            }
        }
    }
}
