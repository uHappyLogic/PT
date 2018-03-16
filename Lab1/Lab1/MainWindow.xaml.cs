using System.IO;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Forms;
using TreeView = System.Windows.Controls.TreeView;

namespace Lab1
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {
            var dlg = new FolderBrowserDialog()
            {
                Description = "Select directory to open",
                ShowNewFolderButton = false
            };

            var result = dlg.ShowDialog();

            if (result == System.Windows.Forms.DialogResult.OK)
            {
                DirectoryTreeView.Items.Clear();
                AddToTree(DirectoryTreeView.Items, dlg.SelectedPath);
            }
        }

        private void AddToTree(ItemCollection items, string path)
        {
            TreeViewItem currentDirectory = new TreeViewItem
            {
                Header = Path.GetFileName(path),
                Tag = path
            };

            items.Add(currentDirectory);

            if ((File.GetAttributes(path) & FileAttributes.Directory) != 0)
            {
                DirectoryInfo directoryInfo = new DirectoryInfo(path);

                foreach (var subdirectory in directoryInfo.GetDirectories())
                {
                    AddToTree(currentDirectory.Items, subdirectory.FullName);
                }

                foreach (var files in directoryInfo.GetFiles())
                {
                    AddToTree(currentDirectory.Items, files.FullName);
                }
            }
        }
    }
}
