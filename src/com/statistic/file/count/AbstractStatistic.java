package com.statistic.file.count;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractStatistic
{
	// статистика
	List<Object>	m_statisticForDirectory	= new ArrayList<>();
	// статистика
	List<Object>	m_statisticForFile		= new ArrayList();

	// файл с указанным форматом
	File					file;

	// конструктор
	public AbstractStatistic(File a_file)
	{
		file = a_file;
	}

	// получить статистику общую в поиске директорий
	public List<Object> getDirectoryStatistc()
	{
		return m_statisticForDirectory;
	}

	// получить статистику общую о файле
	public List<Object> getFileStatistc()
	{
		return m_statisticForFile;
	}

	// посчитать статистику
	abstract public void countStatistic();

	// имя файла
	public String getShortFileName()
	{
		return file.getName();
	}

	// полный путь к файлу
	public String getLongFileName()
	{
		return file.getPath();
	}
}