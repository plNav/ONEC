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

namespace ONEC.VIEWS.Did
{
    /// <summary>
    /// Lógica de interacción para Did.xaml
    /// </summary>
    public partial class Did : Window
    {
        public Did(string tituloVentana, string msg)
        {
            InitializeComponent();
            Owner = StaticResources.main;
            tituloV.Content = tituloVentana;
            txtMsg.Text = msg;
            StaticResources.main.Opacity = 0.5;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnAceptar_MouseEnter(object sender, MouseEventArgs e)
        {
            btnAceptar.Background = (Brush)(new BrushConverter().ConvertFrom("#1c4a59"));
        }

        private void btnAceptar_MouseLeave(object sender, MouseEventArgs e)
        {
            btnAceptar.Background = (Brush)(new BrushConverter().ConvertFrom("#266E86"));
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            StaticResources.main.Opacity = 1;
        }
    }
}
