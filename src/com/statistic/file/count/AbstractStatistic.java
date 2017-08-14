package com.statistic.file.count;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractStatistic
{
	// статистика
	Map<String, String> statisticResult = new HashMap();
	
	// файл с указанным форматом
	File file;
	
	// конструктор
	public AbstractStatistic(File a_file) { 
		file = a_file;
	}
	
	// получить статистику
	abstract public Map<String, String> getStatistc();
	
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
	
	public abstract ImageDescriptor getImage();
}