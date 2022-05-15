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
using System.Windows.Shapes;
using ONEC.API_MODELS;
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Ofertas
{
    /// <summary>
    /// Lógica de interacción para BuscarCandidatosOferta.xaml
    /// </summary>
    public partial class BuscarCandidatosOferta : Window
    {
        List<API_MODELS.CV> cvs;
        API_MODELS.Ofertas oferta;
        OfertaCandidatos ofertaCandidatosView;
        int numCandidato = 0;
        API_MODELS.CV candidato;
        public BuscarCandidatosOferta(API_MODELS.Ofertas oferta, OfertaCandidatos ofertaCandidatosView)
        {
            InitializeComponent();
            this.oferta = oferta;
            this.ofertaCandidatosView = ofertaCandidatosView;
            Owner = StaticResources.main;
            StaticResources.main.Opacity = 0.5;
            cargarCandidatos(oferta, ofertaCandidatosView);
        }

        private async void cargarCandidatos(API_MODELS.Ofertas oferta, OfertaCandidatos ofertaCandidatosView)
        {
            rowCargando.Height = new GridLength(1, GridUnitType.Star);
            rowError.Height = new GridLength(0);
            rowCVS.Height = new GridLength(0);
            rowNoCVS.Height = new GridLength(0);
            stackCargando.Visibility = Visibility.Visible;
            stackError.Visibility = Visibility.Collapsed;
            stackCVS.Visibility = Visibility.Collapsed;
            stackNoCVS.Visibility = Visibility.Collapsed;
            try
            {
                string reqHab = oferta.habilidadesReq ? "S" : "N";
                var listCandidatosId = ofertaCandidatosView.candidatos.Select(c => c.id_cv);
                List<API_MODELS.CV> cvs = await API_MODELS.CV.buscadorCvOferta(oferta._id, reqHab);
                List<API_MODELS.CV> cvsPintar = new List<API_MODELS.CV>();
                foreach(API_MODELS.CV cv in cvs)
                {
                    if(!listCandidatosId.Contains(cv._id))
                    {
                        cvsPintar.Add(cv);
                    }
                }
                if (cvsPintar.Count > 0)
                {
                    this.cvs = cvsPintar;
                    rowCargando.Height = new GridLength(0);
                    stackCargando.Visibility = Visibility.Collapsed;
                    rowCVS.Height = new GridLength(1, GridUnitType.Star);
                    stackCVS.Visibility = Visibility.Visible;
                    pintarCandidato(0);
                }
                else
                {
                    rowCargando.Height = new GridLength(0);
                    stackCargando.Visibility = Visibility.Collapsed;
                    rowNoCVS.Height = new GridLength(1, GridUnitType.Star);
                    stackNoCVS.Visibility = Visibility.Visible;
                }
            }catch(Exception ex)
            {
                rowCargando.Height = new GridLength(0);
                stackCargando.Visibility = Visibility.Collapsed;
                stackError.Visibility = Visibility.Visible;
                rowError.Height = new GridLength(1, GridUnitType.Star);
            }
        }

        private void pintarCandidato(int index)
        {
            wrapHab.Children.Clear();
            txtNumCandidatos.Text = "Numero de candidatos: " + cvs.Count();
            txtContadorCandidatos.Text = "Candidato " + (index + 1) + " de " + cvs.Count();
            txtNombre.Text = cvs[index].nombre;
            txtTitulo.Text = cvs[index].especialidad != null ? cvs[index].especialidad : cvs[index].titulo != null ? cvs[index].titulo : "Sin estudios";
            txtExp.Text = cvs[index].experiencia > 0 ? cvs[index].experiencia.ToString() + " años." : "Sin experiencia";
            if(cvs[index].habilidades.Count() > 0)
            {
                wrapHab.Visibility = Visibility.Visible;
                txtHab.Visibility = Visibility.Collapsed;
                foreach(string hab in cvs[index].habilidades)
                {
                    Border border = new Border
                    {
                        CornerRadius = new CornerRadius(5),
                        Margin = new Thickness(5, 5, 0, 0),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#646A8F")),
                        Padding = new Thickness(5)
                    };

                    Label habilidad = new Label
                    {
                        Foreground = Brushes.White,
                        FontSize = 13,
                        VerticalAlignment = VerticalAlignment.Center,
                        HorizontalAlignment = HorizontalAlignment.Center,
                        Content = "# " + hab
                    };
                    border.Child = habilidad;
                    wrapHab.Children.Add(border);
                }
            }
            else
            {
                wrapHab.Visibility = Visibility.Collapsed;
                txtHab.Visibility = Visibility.Visible;
            }
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            StaticResources.main.Opacity = 1;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            cargarCandidatos(oferta, ofertaCandidatosView);
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            numCandidato--;
            if (numCandidato == 0) imgLeft.Visibility = Visibility.Collapsed;
            if (imgRight.Visibility == Visibility.Collapsed) imgRight.Visibility = Visibility.Visible;
            if (imgLeft.Margin.Right == 0) imgLeft.Margin = new Thickness(0, 0, 50, 0);
            pintarCandidato(numCandidato);
        }

        private void imgRight_PreviewMouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            numCandidato++;
            if (numCandidato == cvs.Count() - 1)
            {
                imgRight.Visibility = Visibility.Collapsed;
                imgLeft.Margin = new Thickness(0);
            }else
            {
                if (imgLeft.Margin.Right == 0) imgLeft.Margin = new Thickness(0, 0, 50, 0);
            }
            if (imgLeft.Visibility == Visibility.Collapsed) imgLeft.Visibility = Visibility.Visible;
            pintarCandidato(numCandidato);
        }

        private void Image_MouseLeftButtonDown_1(object sender, MouseButtonEventArgs e)
        {
            cvs.RemoveAt(numCandidato);
            numCandidato = numCandidato > 0 ? numCandidato - 1 : 0;
            if (cvs.Count() > 0)
            {
                if(cvs.Count() == 1)
                {
                    imgLeft.Visibility = Visibility.Collapsed;
                    imgRight.Visibility = Visibility.Collapsed;
                }else if(numCandidato == 0)
                {
                    imgLeft.Visibility = Visibility.Collapsed;
                }
                pintarCandidato(numCandidato);
            }else
            {
                rowCVS.Height = new GridLength(0);
                stackCVS.Visibility = Visibility.Collapsed;
                rowNoCVS.Height = new GridLength(1, GridUnitType.Star);
                stackNoCVS.Visibility = Visibility.Visible;
            }
        }

        private async void Image_MouseLeftButtonDown_2(object sender, MouseButtonEventArgs e)
        {
            Loading.Loading loading = new Loading.Loading();
            try
            {
                API_MODELS.CandidatosOfertas c = new API_MODELS.CandidatosOfertas(Usuario.usuarioActual._id, oferta._id, cvs[numCandidato]._id);
                await API_MODELS.CandidatosOfertas.crearCandidatoOferta(c);
                if (API_MODELS.CandidatosOfertas.candidatoCreado != null)
                {
                    ofertaCandidatosView.candidatos.Add(API_MODELS.CandidatosOfertas.candidatoCreado);
                    ofertaCandidatosView.cvs.Add(cvs[numCandidato]);
                    cvs.RemoveAt(numCandidato);
                    numCandidato = numCandidato > 0 ? numCandidato - 1 : 0;
                    if (cvs.Count() > 0)
                    {
                        if (cvs.Count() == 1)
                        {
                            imgLeft.Visibility = Visibility.Collapsed;
                            imgRight.Visibility = Visibility.Collapsed;
                        }
                        else if (numCandidato == 0)
                        {
                            imgLeft.Visibility = Visibility.Collapsed;
                        }
                        pintarCandidato(numCandidato);
                    }
                    else
                    {
                        rowCVS.Height = new GridLength(0);
                        stackCVS.Visibility = Visibility.Collapsed;
                        rowNoCVS.Height = new GridLength(1, GridUnitType.Star);
                        stackNoCVS.Visibility = Visibility.Visible;
                    }
                    ofertaCandidatosView.stackCandidatos.Children.Clear();
                    ofertaCandidatosView.pintarCandidatos(ofertaCandidatosView.cvs,ofertaCandidatosView.candidatos);
                }
                else
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al guardar candidato");
                    err.ShowDialog();
                }

            }catch(Exception ex)
            {
                loading.Close();
                ErrorPopUp err = new ErrorPopUp("Error al guardar candidato");
                err.ShowDialog();
            }
        }
    }
}
