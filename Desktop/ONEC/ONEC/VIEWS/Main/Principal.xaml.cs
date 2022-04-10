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

namespace ONEC.VIEWS.Main
{
    /// <summary>
    /// Lógica de interacción para Main.xaml
    /// </summary>
    public partial class Principal : Page
    {
        string selectedPage = "ofertas";
        public Principal()
        {
            InitializeComponent();
            setModo();
            imgOfertas.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ofertas_b.png");
            txtOferta.Foreground = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
        }

        private void Grid_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            Grid elemento = sender as Grid;
            switch(elemento.Tag)
            {
                case "ofertas":
                    if (!selectedPage.Equals("ofertas"))
                    {
                        cleanElements();
                        imgOfertas.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ofertas_b.png");
                        txtOferta.Foreground = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
                        selectedPage = "ofertas";
                    }
                    break;

                case "anuncios":
                    if (!selectedPage.Equals("anuncios"))
                    {
                        cleanElements();
                        imgAnuncios.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\anuncios_b.png");
                        txtAnuncios.Foreground = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
                        selectedPage = "anuncios";
                    }
                    break;

                case "cv":
                    if (!selectedPage.Equals("cv"))
                    {
                        cleanElements();
                        imgCv.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\cv_b.png");
                        txtCv.Foreground = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
                        selectedPage = "cv";
                    }
                    break;

                case "perfil":
                    if (!selectedPage.Equals("perfil"))
                    {
                        cleanElements();
                        imgPerfil.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\perfil_b.png");
                        txtPerfil.Foreground = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
                        selectedPage = "perfil";
                    }
                    break;
                
                //Establecemos un default que apunte a ofertas, esto en teoría nunca tendría que entrar, pero en caso de error, siempre nos mostraría la página default, que en este caso es ofertas
                default:
                    imgOfertas.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ofertas_b.png");
                    txtOferta.Foreground = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
                    selectedPage = "ofertas";
                    break;
            }

        }

        public void cleanElements()
        {
            //Ponemos todas las imagenes en blanco
            imgOfertas.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\ofertas.png");
            imgAnuncios.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\anuncios.png");
            imgCv.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\cv.png");
            imgPerfil.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\perfil.png");

            //ponemos todos los textos en blanco
            txtOferta.Foreground = (Brush)(new BrushConverter().ConvertFrom("#fcffff"));
            txtAnuncios.Foreground = (Brush)(new BrushConverter().ConvertFrom("#fcffff"));
            txtCv.Foreground = (Brush)(new BrushConverter().ConvertFrom("#fcffff"));
            txtPerfil.Foreground = (Brush)(new BrushConverter().ConvertFrom("#fcffff"));
        }

        public void setModo()
        {
            if (StaticResources.modoEmpresario)
            {
                columnaCV.Width = new GridLength(0);
            }else
            {
                columnaCV.Width = new GridLength(1, GridUnitType.Star);
            }
        }
    }
}
