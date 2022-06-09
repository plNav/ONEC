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
using ONEC.SUPPORT;
using System.Text.RegularExpressions;
using ONEC.VIEWS.Main;

namespace ONEC.VIEWS
{
    /// <summary>
    /// Lógica de interacción para CrearCV.xaml
    /// </summary>
    public partial class CrearCV : Page
    {
        List<string> habilidades = new List<string>();
        Principal principal;
        public CrearCV(Principal principal)
        {
            InitializeComponent();
           foreach(string titulo in Titulos.titulos)
            {
                cmbTitulos.Items.Add(titulo);
                this.principal = principal;
            }
            
        }

        private void cmbTitulos_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            string titulo = cmbTitulos.SelectedItem.ToString();
            switch(titulo)
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
                    switch(titulo)
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
                            foreach(string grado in Titulos.grados)
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
                if(!listLower.Contains(txtHabilidades.Text.ToLower()))
                {
                    habilidades.Add(txtHabilidades.Text);

                    Border border = new Border
                    {
                        CornerRadius = new CornerRadius(5),
                        Margin = new Thickness(5,5,0,0),
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
                        Content = "# "+ txtHabilidades.Text,
                        Tag = txtHabilidades.Text
                    };

                    Grid.SetColumn(habilidad, 0);

                    Image borrar = new Image
                    {
                          Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\delete.png"),
                          Height = 30,
                          Width = 30,
                          VerticalAlignment = VerticalAlignment.Center,
                          Margin = new Thickness(0,0,2,0),
                          Cursor = Cursors.Hand
                    };
                    RenderOptions.SetBitmapScalingMode(borrar, BitmapScalingMode.HighQuality);
                 
                    borrar.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                    {
                        wrapHabilidades.Children.Remove(border);
                        habilidades.Remove(habilidad.Tag.ToString());
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

        private async void Button_Click_3(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(txtNombre.Text) || String.IsNullOrWhiteSpace(txtNombre.Text) || String.IsNullOrEmpty(txtTelefono.Text) || String.IsNullOrWhiteSpace(txtTelefono.Text) || String.IsNullOrEmpty(txtUbicacion.Text) || String.IsNullOrWhiteSpace(txtUbicacion.Text))
            {
                //Mostrar error de nulos
                ErrorPopUp err = new ErrorPopUp("Faltan campos por introducir");
                err.ShowDialog();
            } else if (cmbTitulos.SelectedIndex == -1) {
                ErrorPopUp err = new ErrorPopUp("Debe introducir un título");
                err.ShowDialog();
            } else if (cmbEspecialidad.Visibility == Visibility.Visible && cmbEspecialidad.SelectedIndex == -1 || txtEspecialidad.Visibility == Visibility.Visible && txtEspecialidad.Text.Equals(""))
            {
                ErrorPopUp err = new ErrorPopUp("Debe introducir la especialidad");
                err.ShowDialog();
            } else if (txtExperiencia.Text.Contains(" "))
            {
                ErrorPopUp err = new ErrorPopUp("La experiencia introducida\ntiene un formato no válido");
                err.ShowDialog();
            } else
            {
                //Se han comprobado todos los campos, crear el CV y subirlo a la BD
                List<string> listLow = habilidades.Select(x => x.ToLower()).ToList();
                int experiencia = String.IsNullOrEmpty(txtExperiencia.Text) || String.IsNullOrWhiteSpace(txtExperiencia.Text) ? 0 : int.Parse(txtExperiencia.Text);
                string especialidad = cmbEspecialidad.SelectedIndex == -1 ? null : cmbEspecialidad.SelectedItem.ToString();
                CV cv = new CV(Usuario.usuarioActual._id,txtNombre.Text,txtTelefono.Text,txtUbicacion.Text,Usuario.usuarioActual.email,experiencia,cmbTitulos.SelectedItem.ToString(),especialidad,habilidades,listLow);
                try
                {
                    if (await CV.crearCV(cv))
                    {
                        Did.Did did = new Did.Did("Curriculum creado", "El curriculum ha sido creado");
                        did.ShowDialog();
                        principal.mainFrame.Content = new EditarCV(CV.cvActual);
                    }else
                    {
                        ErrorPopUp err = new ErrorPopUp("Error al crear CV\nInténtelo más tarde.");
                        err.ShowDialog();
                    }
                }catch(Exception ex)
                {
                    ErrorPopUp err = new ErrorPopUp("Error al crear CV\nInténtelo más tarde.");
                    err.ShowDialog();
                }
            }
        }
    }
}
