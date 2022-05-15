using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
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
using ONEC.SUPPORT;
using ONEC.VIEWS.Error;

namespace ONEC.VIEWS.Main.Ofertas
{
    /// <summary>
    /// Lógica de interacción para CrearOfertas.xaml
    /// </summary>
    public partial class CrearOfertas : Page
    {
        List<string> habilidades = new List<string>();
        Principal principal;
        List<API_MODELS.Ofertas> ofertas;
        public CrearOfertas(Principal principal, List<API_MODELS.Ofertas> ofertas)
        {
            InitializeComponent();
            this.principal = principal;
            this.ofertas = ofertas;
        }

        private void chckTitulo_Checked(object sender, RoutedEventArgs e)
        {
            gridTitulos.Visibility = Visibility.Visible;
            foreach (string titulo in Titulos.titulos)
            {
                cmbTitulos.Items.Add(titulo);
            }
        }

        private void chckExp_Checked(object sender, RoutedEventArgs e)
        {
            stackExp.Visibility = Visibility.Visible;
        }

        private void chckHab_Checked(object sender, RoutedEventArgs e)
        {
            gridHabilidades.Visibility = Visibility.Visible;
        }

        private void chckTitulo_Unchecked(object sender, RoutedEventArgs e)
        {
            gridTitulos.Visibility = Visibility.Collapsed;
            cmbTitulos.SelectedItem = null;
            cmbTitulos.Text = "Seleccione un título";
            cmbEspecialidad.SelectedItem = null;
            cmbEspecialidad.Text = "Seleccione una especialidad";
            gridEspecialidad.Visibility = Visibility.Collapsed;
        }

        private void chckExp_Unchecked(object sender, RoutedEventArgs e)
        {
            stackExp.Visibility = Visibility.Collapsed;
            txtExp.Text = "";
        }

        private void chckHab_Unchecked(object sender, RoutedEventArgs e)
        {
            gridHabilidades.Visibility = Visibility.Collapsed;
            habilidades.Clear();
            wrapHabilidades.Children.Clear();
        }
        private void cmbTitulos_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbTitulos.SelectedItem != null)
            {
                string titulo = cmbTitulos.SelectedItem.ToString();
                switch (titulo)
                {
                    case "ESO":
                    case "Bachiller":
                        gridEspecialidad.Visibility = Visibility.Collapsed;
                        txtEspecialidad.Text = "";
                        txtEspecialidad.Visibility = Visibility.Collapsed;
                        cmbEspecialidad.Visibility = Visibility.Collapsed;
                        break;

                    case "Postgrado":
                    case "Máster":
                    case "Otros títulos, certificaciones y carnés":
                    case "Otros cursos y certificación no reglada":
                        //Mostramos el textField, para introducir
                        gridEspecialidad.Visibility = Visibility.Visible;
                        txtEspecialidad.Visibility = Visibility.Visible;
                        cmbEspecialidad.Visibility = Visibility.Collapsed;
                        break;

                    default:
                        //Nos muestra el textBox de la especialidad
                        cmbEspecialidad.Items.Clear();
                        gridEspecialidad.Visibility = Visibility.Visible;
                        txtEspecialidad.Visibility = Visibility.Collapsed;
                        cmbEspecialidad.Visibility = Visibility.Visible;
                        switch (titulo)
                        {
                            case "FP Grado medio":
                                foreach (string fp in Titulos.fp_medio)
                                {
                                    cmbEspecialidad.Items.Add(fp);
                                }
                                break;
                            case "FP Grado superior":
                                foreach (string fp in Titulos.fp_superior)
                                {
                                    cmbEspecialidad.Items.Add(fp);
                                }
                                break;
                            case "Enseñanzas artísticas(regladas)":
                                foreach (string a in Titulos.artisticas)
                                {
                                    cmbEspecialidad.Items.Add(a);
                                }
                                break;
                            case "Enseñanzas deportivas(regladas)":
                                foreach (string d in Titulos.deportivas)
                                {
                                    cmbEspecialidad.Items.Add(d);
                                }
                                break;
                            case "Grado":
                                foreach (string grado in Titulos.grados)
                                {
                                    cmbEspecialidad.Items.Add(grado);
                                }
                                break;
                            case "Licencitura":
                                foreach (string licenciatura in Titulos.licenciatura)
                                {
                                    cmbEspecialidad.Items.Add(licenciatura);
                                }
                                break;
                            case "Diplomatura":
                                foreach (string diplomatura in Titulos.diplomatura)
                                {
                                    cmbEspecialidad.Items.Add(diplomatura);
                                }
                                break;
                            case "Ingeniería técnica":
                                foreach (string ing in Titulos.ing_tec)
                                {
                                    cmbEspecialidad.Items.Add(ing);
                                }
                                break;
                            case "Ingeniería superior":
                                foreach (string ing in Titulos.ing_sup)
                                {
                                    cmbEspecialidad.Items.Add(ing);
                                }
                                break;
                            case "Doctorado":
                                foreach (string d in Titulos.doctorado)
                                {
                                    cmbEspecialidad.Items.Add(d);
                                }
                                break;
                            case "Ciclo formativo grado medio":
                                foreach (string gm in Titulos.ciclo_gradoMed)
                                {
                                    cmbEspecialidad.Items.Add(gm);
                                }
                                break;
                            case "Ciclo formativo grado superior":
                                foreach (string gs in Titulos.ciclo_gradoSup)
                                {
                                    cmbEspecialidad.Items.Add(gs);
                                }
                                break;

                        }
                        break;
                }
            }
        }
        private void txtExperiencia_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text.Trim());
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            if (!String.IsNullOrEmpty(txtHabilidades.Text) && !String.IsNullOrWhiteSpace(txtHabilidades.Text))
            {
                var listLower = habilidades.Select(x => x.ToLower());
                if (!listLower.Contains(txtHabilidades.Text.ToLower()))
                {
                    habilidades.Add(txtHabilidades.Text);

                    Border border = new Border
                    {
                        CornerRadius = new CornerRadius(5),
                        Margin = new Thickness(5, 5, 0, 0),
                        Background = (Brush)(new BrushConverter().ConvertFrom("#41a3c4"))
                    };

                    Grid grid = new Grid();
                    ColumnDefinition colHab = new ColumnDefinition();
                    ColumnDefinition colIco = new ColumnDefinition();

                    grid.ColumnDefinitions.Add(colHab);
                    grid.ColumnDefinitions.Add(colIco);

                    Label habilidad = new Label
                    {
                        Foreground = Brushes.White,
                        FontSize = 13,
                        VerticalAlignment = VerticalAlignment.Center,
                        Content = "# " + txtHabilidades.Text
                    };

                    Grid.SetColumn(habilidad, 0);

                    Image borrar = new Image
                    {
                        Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\delete.png"),
                        Height = 30,
                        Width = 30,
                        VerticalAlignment = VerticalAlignment.Center,
                        Margin = new Thickness(0, 0, 2, 0)
                    };
                    RenderOptions.SetBitmapScalingMode(borrar, BitmapScalingMode.HighQuality);

                    borrar.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                    {
                        wrapHabilidades.Children.Remove(border);
                        habilidades.Remove(habilidad.Content.ToString());
                    };

                    Grid.SetColumn(borrar, 1);
                    grid.Children.Add(habilidad);
                    grid.Children.Add(borrar);
                    border.Child = grid;
                    wrapHabilidades.Children.Add(border);

                }
            }
            txtHabilidades.Text = "";
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(txtNombre.Text) || String.IsNullOrWhiteSpace(txtNombre.Text) || String.IsNullOrEmpty(txtDescripcion.Text) || String.IsNullOrWhiteSpace(txtDescripcion.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Faltan campos por completar");
                err.ShowDialog();
            } else if (!chckHab.IsChecked.Value && !chckTitulo.IsChecked.Value)
            {
                ErrorPopUp err = new ErrorPopUp("Debe introducir un título\no una habilidad\ncomo mínimo");
                err.ShowDialog();
            } else if (chckTitulo.IsChecked.Value && cmbTitulos.SelectedIndex == -1)
            {
                ErrorPopUp err = new ErrorPopUp("Falta introducir un título");
                err.ShowDialog();
            } else if (txtEspecialidad.IsVisible && String.IsNullOrWhiteSpace(txtEspecialidad.Text) || txtEspecialidad.IsVisible && String.IsNullOrEmpty(txtEspecialidad.Text) || cmbEspecialidad.IsVisible && cmbEspecialidad.SelectedIndex == -1) {
                ErrorPopUp err = new ErrorPopUp("Falta introducir la especialidad");
                err.ShowDialog();
            } else if (chckExp.IsChecked.Value && String.IsNullOrWhiteSpace(txtExp.Text) || chckExp.IsChecked.Value && String.IsNullOrEmpty(txtExp.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Falta introducir la experiencia");
                err.ShowDialog();
            } else if (txtExp.Text.Contains(" ")) {
                ErrorPopUp err = new ErrorPopUp("Experiencia introducida\nno válida.");
                err.ShowDialog();
            } else if (chckHab.IsChecked.Value && habilidades.Count() <= 0)
            {
                ErrorPopUp err = new ErrorPopUp("Falta introducir habilidades");
                err.ShowDialog();
            } else
            {
                //Están todos los campos comprobados, crear oferta
                Loading.Loading loading = new Loading.Loading();
                try
                {
                    loading.Show();
                    int experiencia = chckExp.IsChecked.Value ? int.Parse(txtExp.Text) : 0;
                    string titulo = chckTitulo.IsChecked.Value ? cmbTitulos.SelectedItem.ToString() : null;
                    string especialidad = txtEspecialidad.IsVisible ? txtEspecialidad.Text : cmbEspecialidad.IsVisible ? cmbEspecialidad.SelectedItem.ToString() : null;
                    List<string> listLow = habilidades.Select(x => x.ToLower()).ToList();
                    API_MODELS.Ofertas oferta = new API_MODELS.Ofertas(Usuario.usuarioActual._id, txtNombre.Text, txtDescripcion.Text, experiencia, titulo, especialidad, habilidades, listLow, chckReqHab.IsChecked.Value);
                    if (await API_MODELS.Ofertas.crearOferta(oferta))
                    {
                        loading.Close();
                        Did.Did did = new Did.Did("Oferta creada", "La oferta ha sido creada");
                        did.ShowDialog();
                        principal.mainFrame.Content = new OfertasLoader(principal);
                    }else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al crear oferta");
                        err.ShowDialog();
                    }
                }catch(Exception ex)
                {
                    loading.Close();
                    ErrorPopUp err = new ErrorPopUp("Error al crear oferta");
                    err.ShowDialog();
                }
            }
        }

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            principal.mainFrame.Content = new OfertasMain(principal, ofertas);
        }
    }
}
