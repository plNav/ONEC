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
using ONEC.VIEWS.Did;

namespace ONEC.VIEWS.Main.Anuncios.AnunciosEmpresario
{
    /// <summary>
    /// Lógica de interacción para AnuncioCrearReview.xaml
    /// </summary>
    public partial class AnuncioCrearReview : Page
    {
        Principal principal;
        AnunciosGuardados anuncioG;
        List<AnunciosGuardados> anunciosGuardados;
        int puntuacion;
        public AnuncioCrearReview(Principal principal, AnunciosGuardados anuncioG, List<AnunciosGuardados> anunciosGuardados)
        {
            InitializeComponent();
            this.principal = principal;
            this.anuncioG = anuncioG;
            this.anunciosGuardados = anunciosGuardados;
        }

        private void btnCancelar_Click(object sender, RoutedEventArgs e)
        {
            principal.mainFrame.Content = new AnuncioEmpresarioLoader(principal, anuncioG, anunciosGuardados);
        }

        private void loadVista(int puntuacion)
        {
            switch(puntuacion)
            {
                case 1:
                    {
                        star1.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star2.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        star3.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        star4.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        star5.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        imgV.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\enfadado.png");
                        txtTitu.Text = "Muy insatisfecho";
                        break;
                    }
                case 2:
                    {
                        star1.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star2.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star3.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        star4.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        star5.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        imgV.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\pocosatis.png");
                        txtTitu.Text = "Insatisfecho";
                        break;
                    }
                case 3:
                    {
                        star1.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star2.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star3.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star4.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        star5.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        imgV.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\feliz.png");
                        txtTitu.Text = "Satisfecho";
                        break;
                    }
                case 4:
                    {
                        star1.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star2.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star3.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star4.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star5.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\emptystar.png");
                        imgV.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\muyfeliz.png");
                        txtTitu.Text = "Muy satisfecho";
                        break;
                    }
                case 5:
                    {
                        star1.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star2.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star3.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star4.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        star5.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\fullstarY.png");
                        imgV.Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\pro.png");
                        txtTitu.Text = "Otro nivel";
                        break;
                    }
                default: break;
            }
        }

        private void star1_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            puntuacion = 1;
            loadVista(puntuacion);
        }

        private void star2_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            puntuacion = 2;
            loadVista(puntuacion);
        }

        private void star3_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            puntuacion = 3;
            loadVista(puntuacion);
        }

        private void star4_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            puntuacion = 4;
            loadVista(puntuacion);
        }

        private void star5_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            puntuacion = 5;
            loadVista(puntuacion);
        }

        private async void btnPublicar_Click(object sender, RoutedEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                loading.Show();
                if(puntuacion <= 0 || String.IsNullOrEmpty(SearchTermTextBox.Text) || String.IsNullOrWhiteSpace(SearchTermTextBox.Text))
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Faltan campos por introducir.");
                    err.ShowDialog();
                }
                else
                {
                    
                    Resenyas res = new Resenyas(Usuario.usuarioActual._id, anuncioG.id_anuncio, puntuacion, SearchTermTextBox.Text);
                    if (await Resenyas.crearResenya(res))
                    {
                        loading.Close();
                        Did.Did did = new Did.Did("Reseña Creada","Se ha añadido su reseña.");
                        did.ShowDialog();
                        principal.mainFrame.Content = new AnuncioEmpresarioLoader(principal,anuncioG,anunciosGuardados);
                    }else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al publicar reseña\npor favor inténtelo más tarde");
                        err.ShowDialog();
                    }
                }
                
            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al publicar reseña\npor favor inténtelo más tarde");
                err.ShowDialog();
            }
        }
    }
}
