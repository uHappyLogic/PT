using System;
using System.Collections.Generic;
using System.IO;
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

namespace Lab1
{
    /// <summary>
    /// Interaction logic for CreateDirectoryForm.xaml
    /// </summary>
    public partial class CreateDirectoryForm : Window
    {
        public CreateDirectoryForm()
        {
            InitializeComponent();
            radioButtonFile.IsChecked = true;
        }

        private void radioButtonFile_Checked(object sender, RoutedEventArgs e)
        {
            if (radioButtonFile.IsChecked == true)
            {
                radioButtonDirectory.IsChecked = false;
                enableCheckButtons(true);
            }
        }

        private void radioButtonDirectory_Checked(object sender, RoutedEventArgs e)
        {
            if (radioButtonDirectory.IsChecked == true)
            {
                radioButtonFile.IsChecked = false;
                enableCheckButtons(false);
            }
        }

        private void enableCheckButtons(bool areEnabled)
        {
            checkBoxArchive.IsEnabled = areEnabled;
            checkBoxHidden.IsEnabled = areEnabled;
            checkBoxReadonyl.IsEnabled = areEnabled;
            checkBoxSystem.IsEnabled = areEnabled;
        }

        private void buttonCancel_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }

        private void buttonOk_Click(object sender, RoutedEventArgs e)
        {
            string element = radioButtonDirectory.IsChecked.Value ? "Directory" : "Filename";

            try
            {
                string filename = textBoxName.Text;

                if (string.IsNullOrEmpty(filename))
                {
                    MessageBox.Show("Name should not be empty", "Information", MessageBoxButton.OK);
                    return;
                }

                var fullPath = System.IO.Path.Combine(FilePath, filename);

                if (radioButtonDirectory.IsChecked == true)
                {
                    Directory.CreateDirectory(fullPath);
                }
                else
                {
                    File.Create(fullPath).Close();

                    FileAttributes fa = 0x0;
                    
                    if (checkBoxArchive.IsChecked == true)
                        fa |= FileAttributes.Archive;

                    if (checkBoxHidden.IsChecked == true)
                        fa |= FileAttributes.Hidden;

                    if (checkBoxReadonyl.IsChecked == true)
                        fa |= FileAttributes.ReadOnly;
                    
                    if (checkBoxSystem.IsChecked == true)
                        fa |= FileAttributes.System;

                    File.SetAttributes(fullPath, fa);
                }

                MessageBox.Show($"{ element } with name { filename } successfully created with current name and attributes", "Information", MessageBoxButton.OK);
                
                Close();
            }
            catch (Exception)
            {
                MessageBox.Show($"{ element } cannot be created with current name and attributes", "Information", MessageBoxButton.OK);
            }
        }

        public string FilePath;
    }
}
