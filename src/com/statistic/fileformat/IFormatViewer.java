package com.statistic.fileformat;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;

import com.statistic.table.StatisticStructure;

public interface IFormatViewer
{	
	// метод отображения содержимого в таблицу для директории
	public Map<String, StatisticStructure> getCountedDirectoryStatistic(List<AbstractStatistic> a_list, int a_minCodeLines);

	// метод отображения содержимого в таблицу для файла
	public Map<String, StatisticStructure> getCountedFileStatistic(AbstractStatistic a_list);

	// получить изображение, в зависимости от формата файла
	public ImageDescriptor getFileImage();
}
