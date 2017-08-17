package com.statistic.folders;

import javax.annotation.PostConstruct;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.graphics.Image;

import com.statistic.count.Activator;
import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.viewer.IFormatViewer;

// обозреватель файлов
public class FileBrowser
{

	// дерево файлов
	private TreeViewer viewer;

	public FileBrowser(TreeViewer a_treeViewer)
	{
		viewer = a_treeViewer;
	}

	// создание начального состояний структуры отображения дерева
	@PostConstruct
	public void createControls(DirecroryStructure a_direcroryStructure,
			IFormatViewer a_formatViewer)
	{
		viewer.setContentProvider(a_direcroryStructure);
		
		viewer.setLabelProvider(
				new ViewLabelProvider(createImageOfDirectory(), createImageOfFile(a_formatViewer)));
		viewer.setFilters(new FilterEmptyFolders());
		viewer.setInput(a_direcroryStructure);
	}

	// изображение директории
	private ImageDescriptor createImageOfDirectory()
	{
		return Activator.imageDescriptorFromPlugin("org.eclipse.e4.ui.workbench.swt",
				"/icons/full/obj16/fldr_obj.gif");
	}

	// изображение файла
	private ImageDescriptor createImageOfFile(IFormatViewer a_formatViewer)
	{
		return a_formatViewer.getFileImage();

		// return
		// ImageDescriptor.createFromImage(a_abstractStatistic.getImage());
	}

	// клсс иконка - текст файла
	class ViewLabelProvider extends LabelProvider
	/* implements IStyledLabelProvider */ {

		// изображение директории
		private ImageDescriptor	directoryImage;
		// изображение файла
		private ImageDescriptor	fileImage;
		// объект ресурсов проета
		private ResourceManager	resourceManager;

		public ViewLabelProvider(ImageDescriptor directoryImage, ImageDescriptor a_fileDescriptor)
		{
			this.directoryImage = directoryImage;
			this.fileImage = a_fileDescriptor;
		}

		// получение название файла
		@Override
		public String getText(Object a_element)
		{
			if(a_element instanceof DirecroryStructure)
			{
				DirecroryStructure direcroryStructure = ((DirecroryStructure) a_element);
				return String.format("%s ( %d )", direcroryStructure.m_directoryName,
						direcroryStructure.m_amountOfFiles);
			}
			else
				return ((AbstractStatistic) a_element).getShortFileName();

		}

		// установка изображения ждя файла
		@Override
		public Image getImage(Object element)
		{
			return element instanceof DirecroryStructure
					? getResourceManager().createImage(directoryImage)
					: getResourceManager().createImage(fileImage);
		}

		// очищение ресурсов
		@Override
		public void dispose()
		{
			// garbage collection system resources
			if(resourceManager != null)
			{
				resourceManager.dispose();

				resourceManager = null;
			}
		}

		// выделение ресурсов
		protected ResourceManager getResourceManager()
		{
			if(resourceManager == null)
			{
				resourceManager = new LocalResourceManager(JFaceResources.getResources());
			}
			return resourceManager;
		}

	}

	class FilterEmptyFolders extends ViewerFilter
	{
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element)
		{
			if(element instanceof AbstractStatistic)
				return true;

			DirecroryStructure direcroryStructure = (DirecroryStructure) element;
			return direcroryStructure.m_amountOfFiles != 0;
		}
	}
}