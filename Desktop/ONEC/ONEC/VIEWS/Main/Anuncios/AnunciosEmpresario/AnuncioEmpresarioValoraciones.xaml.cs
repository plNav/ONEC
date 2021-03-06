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
    /// Lógica de interacción para AnuncioEmpresarioValoraciones.xaml
    /// </summary>
    public partial class AnuncioEmpresarioValoraciones : Page
    {
        Principal principal;
        AnunciosGuardados anuncioG;
        List<AnunciosGuardados> anunciosGuardados;
        public AnuncioEmpresarioValoraciones(Principal principal, List<Resenyas> reviews, AnunciosGuardados anuncioG, List<string> emails, List<AnunciosGuardados> anunciosGuardados)
        {
            InitializeComponent();
            this.principal = principal;
            this.anuncioG = anuncioG;
            this.anunciosGuardados = anunciosGuardados;
           
            cargarReviews(reviews, emails);
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new AnuncioEmpresarioLoader(principal, anuncioG, anunciosGuardados);
        }

        private async void cargarReviews(List<Resenyas> reviews, List<string> emails)
        {
            if (reviews.Count() > 0)
            {
                rowTxt.Height = new GridLength(0);
                txtSinReviews.Visibility = Visibility.Collapsed;
                rowPanel.Height = new GridLength(1, GridUnitType.Star);
                panelReviews.Visibility = Visibility.Visible;
                for (int x = 0; x < reviews.Count; x++)
                {
                    Border border = new Border
                    {
                        CornerRadius = new CornerRadius(5),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#999dba")),
                        Padding = new Thickness(5),
                        Margin = new Thickness(0, 0, 0, 20)

                    };
                    StackPanel stack = new StackPanel();
                    TextBlock email = new TextBlock
                    {
                        Text = emails[x],
                        FontSize = 17,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020")),
                        Margin = new Thickness(3, 0, 0, 0)
                    };
                    stack.Children.Add(email);
                    StackPanel stackStars = new StackPanel
                    {
                        Margin = new Thickness(3, 3, 0, 0),
                        Orientation = Orientation.Horizontal
                    };
                    Image star1 = new Image
                    {
                        Source = reviews[x].puntuacion >= 1 ? (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png") : (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png"),
                        Height = 20,
                        Width = 20,
                        Margin = new Thickness(2, 0, 0, 0),
                    };
                    RenderOptions.SetBitmapScalingMode(star1, BitmapScalingMode.HighQuality);
                    stackStars.Children.Add(star1);
                    Image star2 = new Image
                    {
                        Source = reviews[x].puntuacion >= 2 ? (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png") : (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png"),
                        Height = 20,
                        Width = 20,
                        Margin = new Thickness(2, 0, 0, 0),
                    };
                    RenderOptions.SetBitmapScalingMode(star2, BitmapScalingMode.HighQuality);
                    stackStars.Children.Add(star2);
                    Image star3 = new Image
                    {
                        Source = reviews[x].puntuacion >= 3 ? (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png") : (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png"),
                        Height = 20,
                        Width = 20,
                        Margin = new Thickness(2, 0, 0, 0),
                    };
                    RenderOptions.SetBitmapScalingMode(star3, BitmapScalingMode.HighQuality);
                    stackStars.Children.Add(star3);
                    Image star4 = new Image
                    {
                        Source = reviews[x].puntuacion >= 4 ? (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png") : (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png"),
                        Height = 20,
                        Width = 20,
                        Margin = new Thickness(2, 0, 0, 0),
                    };
                    RenderOptions.SetBitmapScalingMode(star4, BitmapScalingMode.HighQuality);
                    stackStars.Children.Add(star4);
                    Image star5 = new Image
                    {
                        Source = reviews[x].puntuacion >= 5 ? (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png") : (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png"),
                        Height = 20,
                        Width = 20,
                        Margin = new Thickness(2, 0, 0, 0),
                    };
                    RenderOptions.SetBitmapScalingMode(star5, BitmapScalingMode.HighQuality);
                    stackStars.Children.Add(star5);
                    stack.Children.Add(stackStars);
                    Border borderDesc = new Border
                    {
                        Margin = new Thickness(5),
                        CornerRadius = new CornerRadius(5),
                        Padding = new Thickness(5),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#8488A2"))
                    };
                    StackPanel stackDesc = new StackPanel();
                    TextBlock detallesTit = new TextBlock
                    {
                        Text = "Detalles",
                        FontSize = 15,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#E0E3EB"))
                    };
                    stackDesc.Children.Add(detallesTit);
                    TextBlock txtDetalles = new TextBlock
                    {
                        Text = reviews[x].descripcion,
                        TextWrapping = TextWrapping.Wrap,
                        Margin = new Thickness(3, 0, 0, 0),
                        FontSize = 16,
                        Foreground = (Brush)(new BrushConverter().ConvertFrom("#202020"))
                    };
                    stackDesc.Children.Add(txtDetalles);
                    borderDesc.Child = stackDesc;
                    stack.Children.Add(borderDesc);
                    border.Child = stack;
                    panelReviews.Children.Add(border);
                }
            }else
            {
                rowTxt.Height = new GridLength(1, GridUnitType.Star);
                txtSinReviews.Visibility = Visibility.Visible;
                rowPanel.Height = new GridLength(0);
                panelReviews.Visibility = Visibility.Collapsed;
            }
        }
    }
}
