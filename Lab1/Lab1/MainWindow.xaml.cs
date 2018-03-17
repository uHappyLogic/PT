using System;
using System.Diagnostics;
using System.IO;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Forms;
using ContextMenu = System.Windows.Controls.ContextMenu;
using TreeView = System.Windows.Controls.TreeView;

namespace Lab1
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            _contextMenu = DirectoryTreeView.ContextMenu;
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

        private void DirectoryTreeView_OnSelectedItemChanged(object sender, RoutedPropertyChangedEventArgs<object> e)
        {
            try
            {
                string path = (DirectoryTreeView.SelectedItem as TreeViewItem)?.Tag as string;
                ChangeContextMenu(path);
                LoadToContentBlock(path);
                LoadFileAttributes(path);
            }
            catch (Exception)
            {
                Debug.WriteLine("TreeViewItem not selected");
            }
        }

        private void LoadFileAttributes(string path)
        {
            FileAttributes fa = File.GetAttributes(path);

            if ((fa & FileAttributes.Directory) == 0)
            {
                string attr = "";

                if ((fa & FileAttributes.ReadOnly) != 0)
                    attr += "R";

                if ((fa & FileAttributes.Archive) != 0)
                    attr += "A";

                if ((fa & FileAttributes.System) != 0)
                    attr += "S";

                if ((fa & FileAttributes.Hidden) != 0)
                    attr += "H";

                LabelAttributes.Content = attr;
            }
            else
            {
                LabelAttributes.Content = "Directory";
            }
        }

        private void MenuItem_Click_1(object sender, RoutedEventArgs e)
        {
            System.Windows.Application.Current.Shutdown();
        }

        private void AddToTree(ItemCollection items, string path)
        {
            TreeViewItem currentDirectory = new TreeViewItem
            {
                Header = Path.GetFileName(path),
                Tag = path,
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

        private void LoadToContentBlock(string path)
        {
            if ((File.GetAttributes(path) & FileAttributes.Directory) != 0)
            {
                ContentBlock.Text = "Selected item is directory";
            }
            else
            {
                try
                {
                    using (var textReader = File.OpenText(path))
                    {
                        ContentBlock.Text = "";

                        for (int i = 0; i < 300; ++i)
                        {
                            ContentBlock.Text += textReader.ReadLine() + "\n";
                        }
                    }
                }
                catch (Exception)
                {
                    ContentBlock.Text = "Selected item cannot be opened for read";
                }
            }
        }

        private void ChangeContextMenu(string path)
        {
            try
            {
                if ((File.GetAttributes(path) & FileAttributes.Directory) != 0)
                {
                    DirectoryTreeView.ContextMenu = _contextMenu;
                }
                else
                {
                    DirectoryTreeView.ContextMenu = null;
                }
            }
            catch (Exception)
            {
                DirectoryTreeView.ContextMenu = null;
            }
        }

        private void AddFolder_Click(object sender, RoutedEventArgs e)
        {
            string path = (DirectoryTreeView.SelectedItem as TreeViewItem)?.Tag as string;

            CreateDirectoryForm createDirectoryForm = new CreateDirectoryForm
            {
                Owner = this,
                FilePath = path
            };

            createDirectoryForm.ShowDialog();
        }

        private void RemoveFolder_Click(object sender, RoutedEventArgs e)
        {
            string path = (DirectoryTreeView.SelectedItem as TreeViewItem)?.Tag as string;

            try
            {
                Directory.Delete(path);
            }
            catch (Exception)
            {
                System.Windows.MessageBox.Show("Directory cannot be removed", "Information", MessageBoxButton.OK);
            }
        }

        ContextMenu _contextMenu;
    }
}
