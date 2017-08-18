package com.statistic.file.count;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.statistic.table.StatisticStructure;

public abstract class AbstractStatistic
{
	// статистика
	protected Map<String, StatisticStructure>	m_statisticForDirectory	= new HashMap<>();
	// статистика
	protected Map<String, StatisticStructure>	m_statisticForFile		= new HashMap<>();

	// ограничение 
	protected Map<String, RestrictionPair> m_restriction = new LinkedHashMap<>();
	
	// файл с указанным форматом
	protected File			file;

	// Откуда добрались до файла
	protected String comeFrom = null;
	
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
	
	// инициализировать стартовую директорию (при поиске используетсяs)
	public void setFilePathFrom(String a_string)
	{
		comeFrom = a_string;
	}
	
	//
	public class RestrictionPair {
		int m_value = 0;
		Predicate<String> m_restriction;
		boolean m_continueSearch = false;

		public RestrictionPair(Predicate<String> a_predicates) {
			m_restriction = a_predicates;
		}
		
		public RestrictionPair(Boolean a_continueSearch, Predicate<String> a_predicates) {
			m_restriction = a_predicates;
			m_continueSearch = a_continueSearch;
		}
		
		public Predicate<String> getRestriction()
		{
			return m_restriction;
		}
		
		public int getValue()
		{
			return m_value;
		}
		
		public void addValue()
		{
			++m_value;
		}
		
		public boolean getContinueSearch()
		{
			return m_continueSearch;
		}
	}
}