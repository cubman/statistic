package com.statistic.file.viewer;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;

import com.statistic.file.count.AbstractStatistic;

public interface IFormatViewer
{	
	// метод отображения содержимого в таблицу для директории
	public void setAndPrintDirectory(List<AbstractStatistic> a_list);

	// метод отображения содержимого в таблицу для файла
	public void setAndPrintFolder(AbstractStatistic a_list);

	// получить изображение, в зависимости от формата файла
	public ImageDescriptor getFileImage();
}
