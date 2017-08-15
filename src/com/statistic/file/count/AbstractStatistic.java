package com.statistic.file.count;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractStatistic
{
	// статистика
	Map<String, Integer> m_statisticForDirectory = new HashMap();
	// статистика
	Map<String, Integer> m_statisticForFile = new HashMap();
		
	// файл с указанным форматом
	File file;
	
	// конструктор
	public AbstractStatistic(File a_file) { 
		file = a_file;
	}
	
	// получить статистику общую в поиске директорий
	public Map<String, Integer> getDirectoryStatistc() {
		return m_statisticForDirectory;
	}
	
	// получить статистику общую о файле
	public Map<String, Integer> getFileStatistc() {
		return m_statisticForFile;
	}
	
	// посчитать статистику
	abstract public void countStatistic();
	
	// имя файла
	public String getShortFileName() {
		return file.getName();
	}
	
	// полный путь к файлу
	public String getLongFileName()
	{
		return file.getPath();
	}
	
	// получить иконку для формата
	public abstract ImageDescriptor getImage();
}