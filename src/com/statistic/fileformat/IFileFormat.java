package com.statistic.fileformat;

import java.io.File;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.viewer.IFormatViewer;

/**
	требование к формату поиска
 */
public interface IFileFormat
{
	// получить расширения поиска
	String [] getExtensions();
	
	// получить статистику по файлу
	AbstractStatistic getStatistic(File a_file);
	
	// отображение для формата
	IFormatViewer getFormatViewer();
}
