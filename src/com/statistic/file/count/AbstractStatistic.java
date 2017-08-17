package com.statistic.file.count;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import com.statistic.table.StatisticStructure;

public abstract class AbstractStatistic
{
	// статистика
	Map<String, StatisticStructure>	m_statisticForDirectory	= new HashMap<>();
	// статистика
	Map<String, StatisticStructure>	m_statisticForFile		= new HashMap<>();

	// файл с указанным форматом
	File			file;

	// Откуда добрались до файла
	String comeFrom = null;
	
	// конструктор
	public AbstractStatistic(File a_file)
	{
		file = a_file;
	}

	// получить статистику общую в поиске директорий
	public Map<String, StatisticStructure> getDirectoryStatistc()
	{
		return m_statisticForDirectory;
	}

	// получить статистику общую о файле
	public Map<String, StatisticStructure> getFileStatistc()
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
	
	// путь от указанной директории
	public String comeFromPath()
	{
		return comeFrom == null ? getShortFileName() : file.getAbsolutePath().substring(comeFrom.length());
	}
	
	public void setFilePathFrom(String a_string)
	{
		comeFrom = a_string;
	}
}