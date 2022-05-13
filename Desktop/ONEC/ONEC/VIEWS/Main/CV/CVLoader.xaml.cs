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
using ONEC.VIEWS.Main;
using ONEC.VIEWS.Main.CV;

namespace ONEC.VIEWS
{
    /// <summary>
    /// Lógica de interacción para CVLoader.xaml
    /// </summary>
    public partial class CVLoader : Page
    {
        public CVLoader(Principal principal)
        {
            InitializeComponent();
            loadCV(principal);
        }

        private async void loadCV(Principal principal)
        {
            try
            {
                CV cv = await CV.obtenerCvId(Usuario.usuarioActual._id);
                if (cv != null)
                {
                    //Mostramos la page para editar el CV
                    principal.mainFrame.Content = new EditarCV(cv);
                }else
                {
                    //Mostramos la page para crear el CV
                    principal.mainFrame.Content = new CrearCV(principal);
                }
            }catch(Exception ex) {
                //Mostramos page de error
                principal.mainFrame.Content = new ErrorCargaCV(principal);
            }
        }

    }
}
