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

namespace ONEC.VIEWS.Loading
{
    /// <summary>
    /// Lógica de interacción para Loading.xaml
    /// </summary>
    public partial class Loading : Window
    {
        public Loading()
        {
            InitializeComponent();
            StaticResources.main.Opacity = 0.5;
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            StaticResources.main.Opacity = 1;
        }
    }
}
