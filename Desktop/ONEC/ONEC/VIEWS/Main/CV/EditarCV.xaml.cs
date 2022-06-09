using ONEC.API_MODELS;
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
using ONEC.SUPPORT;
using ONEC.VIEWS.Error;
using ONEC.VIEWS.Loading;
using ONEC.VIEWS.Did;

namespace ONEC.VIEWS
{
    /// <summary>
    /// Lógica de interacción para EditarCV.xaml
    /// </summary>
    public partial class EditarCV : Page
    {
        CV cv;
        List<string> habilidades = new List<string>();
        public EditarCV(CV cv)
        {
            InitializeComponent();
            this.cv = cv;
            cargarDatos();
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
                        Content = "# " + txtHabilidades.Text,
                        Tag =   txtHabilidades.Text
                    };

                    Grid.SetColumn(habilidad, 0);

                    Image borrar = new Image
                    {
                        Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\delete.png"),
                        Height = 30,
                        Width = 30,
                        VerticalAlignment = VerticalAlignment.Center,
                        Margin = new Thickness(0, 0, 2, 0),
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

        private void cmbTitulos_SelectionChanged(object sender, SelectionChangedEventArgs e)
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

        private void cargarDatos()
        {
            //Datos Personales
            txtNombre.Text = cv.nombre;
            txtTelefono.Text = cv.telefono;
            txtUbicacion.Text = cv.ubicacion;

            //Titulos
            foreach(string titulo in Titulos.titulos)
            {
                cmbTitulos.Items.Add(titulo);
            }
            cmbTitulos.SelectedItem = cv.titulo;

            //Especialidad
            switch (cv.titulo)
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
                    switch (cv.titulo)
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
            if (cv.especialidad != null)
            {
                if (cmbEspecialidad.Visibility == Visibility.Visible)
                {
                    cmbEspecialidad.SelectedItem = cv.especialidad;
                }else
                {
                    txtEspecialidad.Text = cv.especialidad;
                }
            }
            //Experiencia
            if (cv.experiencia > 0) txtExperiencia.Text = cv.experiencia.ToString();

            //Habilidades
            foreach(string habilidad in cv.habilidades)
            {
                habilidades.Add(habilidad);
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

                Label hab = new Label
                {
                    Foreground = Brushes.White,
                    FontSize = 13,
                    VerticalAlignment = VerticalAlignment.Center,
                    Content = "# " + habilidad,
                    Tag = habilidad
                };

                Grid.SetColumn(hab, 0);

                Image borrar = new Image
                {
                    Source = (ImageSource)new ImageSourceConverter().ConvertFrom("..\\..\\IMAGES\\delete.png"),
                    Height = 30,
                    Width = 30,
                    VerticalAlignment = VerticalAlignment.Center,
                    Margin = new Thickness(0, 0, 2, 0),
                    Cursor = Cursors.Hand
                };
                RenderOptions.SetBitmapScalingMode(borrar, BitmapScalingMode.HighQuality);

                borrar.MouseLeftButtonDown += (object senderMouseLegtButtonDown, MouseButtonEventArgs re) =>
                {
                    wrapHabilidades.Children.Remove(border);
                    habilidades.Remove(habilidad);
                };

                Grid.SetColumn(borrar, 1);
                grid.Children.Add(hab);
                grid.Children.Add(borrar);
                border.Child = grid;
                wrapHabilidades.Children.Add(border);
            }
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(txtNombre.Text) || String.IsNullOrWhiteSpace(txtNombre.Text) || String.IsNullOrEmpty(txtTelefono.Text) || String.IsNullOrWhiteSpace(txtTelefono.Text) || String.IsNullOrEmpty(txtUbicacion.Text) || String.IsNullOrWhiteSpace(txtUbicacion.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Faltan campos por introducir");
                err.Show();
            }else if(cmbEspecialidad.Visibility == Visibility.Visible && cmbEspecialidad.SelectedIndex == -1 || txtEspecialidad.Visibility == Visibility.Visible && String.IsNullOrEmpty(txtEspecialidad.Text) || txtEspecialidad.Visibility == Visibility.Visible && String.IsNullOrWhiteSpace(txtEspecialidad.Text))
            {
                ErrorPopUp err = new ErrorPopUp("Debe introducir una especialidad");
                err.Show();
            }else if (txtExperiencia.Text.Contains(" "))
            {
                ErrorPopUp err = new ErrorPopUp("La experiencia introducida\ntiene un formato no válido");
                err.Show();
            }else
            {
                int experiencia = txtExperiencia.Text.Equals("") ? 0 : int.Parse(txtExperiencia.Text);
                string especialidad = !String.IsNullOrEmpty(txtEspecialidad.Text)  && gridEspecialidad.Visibility == Visibility.Visible|| !String.IsNullOrWhiteSpace(txtEspecialidad.Text) && gridEspecialidad.Visibility == Visibility.Visible ? txtEspecialidad.Text : cmbEspecialidad.SelectedIndex != -1 && gridEspecialidad.Visibility == Visibility.Visible ? cmbEspecialidad.SelectedItem.ToString() : "";
                List<string> listLower = habilidades.Select(x => x.ToLower()).ToList();
                CV cv = new CV(Usuario.usuarioActual._id, txtNombre.Text, txtTelefono.Text, txtUbicacion.Text, Usuario.usuarioActual.email, experiencia,cmbTitulos.SelectedItem.ToString(), especialidad, habilidades, listLower);
                Loading.Loading loading = new Loading.Loading();
                loading.Show();
                if (CV.cvActual.titulo != null && !CV.cvActual.titulo.Equals(cv.titulo) ||CV.cvActual.especialidad != null && !CV.cvActual.especialidad.Equals(cv.especialidad))
                {
                    if (await CandidatosOfertas.eliminarOfertasCandidato(CV.cvActual._id))
                    {
                        if (await CV.actualizarCV(CV.cvActual._id, cv))
                        {
                            loading.Close();
                            CV.cvActual.nombre = cv.nombre;
                            CV.cvActual.telefono = cv.telefono;
                            CV.cvActual.ubicacion = cv.ubicacion;
                            CV.cvActual.experiencia = cv.experiencia;
                            CV.cvActual.titulo = cv.titulo;
                            CV.cvActual.especialidad = cv.especialidad;
                            CV.cvActual.habilidades = cv.habilidades;
                            CV.cvActual.habilidadesLow = cv.habilidadesLow;
                            Did.Did did = new Did.Did("Curriculum actualizado", "El curriculum\nha sido actualizado.");
                            did.ShowDialog();
                        }
                        else
                        {
                            loading.Close();
                            ErrorPopUp err = new ErrorPopUp("Error al actualizar CV");
                            err.Show();
                        }
                    }
                    else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al actualizar CV");
                        err.Show();
                    }
                }
                else
                {
                    if (await CV.actualizarCV(CV.cvActual._id, cv))
                    {
                        loading.Close();
                        CV.cvActual.nombre = cv.nombre;
                        CV.cvActual.telefono = cv.telefono;
                        CV.cvActual.ubicacion = cv.ubicacion;
                        CV.cvActual.experiencia = cv.experiencia;
                        CV.cvActual.titulo = cv.titulo;
                        CV.cvActual.especialidad = cv.especialidad;
                        CV.cvActual.habilidades = cv.habilidades;
                        CV.cvActual.habilidadesLow = cv.habilidadesLow;
                        Did.Did did = new Did.Did("Curriculum actualizado", "El curriculum\nha sido actualizado.");
                        did.ShowDialog();
                    }
                    else
                    {
                        loading.Close();
                        ErrorPopUp err = new ErrorPopUp("Error al actualizar CV");
                        err.Show();
                    }
                }
            }
            
        }
    }
}
