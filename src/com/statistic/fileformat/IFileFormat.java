package com.statistic.fileformat;

import java.io.File;

/**
	требование к формату поиска
 */
public interface IFileFormat
{
	// получить расширения поиска
	String [] getExtensions();
	
	// получить статистику по файлу
	AbstractStatistic getStatistic(File a_file);
	
	// получить среду разработки
	String [] getNatures();
}
