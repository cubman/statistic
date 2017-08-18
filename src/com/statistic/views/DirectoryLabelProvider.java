package com.statistic.views;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.folders.DirecroryStructure;

// клсс иконка - текст файла
public class DirectoryLabelProvider extends LabelProvider
/* implements IStyledLabelProvider */ {

	// изображение директории
	private ImageDescriptor	directoryImage;
	// изображение файла
	private ImageDescriptor	fileImage;
	// объект ресурсов проета
	private ResourceManager	resourceManager;

	public DirectoryLabelProvider(ImageDescriptor directoryImage, ImageDescriptor a_fileDescriptor)
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
			return String.format("%s ( %d )", direcroryStructure.getDirectoryName(),
					direcroryStructure.getAmountOfFile());
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