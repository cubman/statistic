package com.statistic.fileformat;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.eclipse.jface.resource.ImageDescriptor;

import com.statistic.table.StatisticStructure;

/**
 * 
 * Статистика для файла
 *
 */
public abstract class AbstractStatistic
{
	// статистика
	protected Map<String, StatisticStructure>	m_statisticForDirectory	= new HashMap<>();
	// статистика
	protected Map<String, StatisticStructure>	m_statisticForFile		= new HashMap<>();

	// ограничение
	protected Map<String, RestrictionPair>		m_restriction			= new LinkedHashMap<>();

	// файл с указанным форматом
	protected File								m_file;

	// Откуда добрались до файла
	protected String							m_comeFrom				= null;

	// формат файла
	protected IFileFormat						m_fileFormat			= null;

	// конструктор
	public AbstractStatistic(File a_file, IFileFormat a_fileFormat)
	{
		m_file = a_file;
		m_fileFormat = a_fileFormat;
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
		return m_file.getName();
	}

	// полный путь к файлу
	public String getLongFileName()
	{
		return m_file.getPath();
	}

	// путь от указанной директории
	public String comeFromPath()
	{
		return m_comeFrom == null ? getShortFileName()
				: m_file.getAbsolutePath().substring(m_comeFrom.length());
	}

	// инициализировать стартовую директорию (при поиске используетсяs)
	public void setFilePathFrom(String a_string)
	{
		m_comeFrom = a_string;
	}

	public abstract ImageDescriptor getImage();

	public abstract Map<String, StatisticStructure> getCountedDirectoryStatistic(
			List<AbstractStatistic> a_list, int a_minCodeLines);

	public abstract Map<String, StatisticStructure> getCountedFileStatistic();

	public IFileFormat getFileFormat()
	{
		return m_fileFormat;
	}

	public class RestrictionPair
	{
		int					m_value				= 0;
		Predicate<String>	m_restriction;
		boolean				m_continueSearch	= false;

		public RestrictionPair(Predicate<String> a_predicates)
		{
			m_restriction = a_predicates;
		}

		public RestrictionPair(Boolean a_continueSearch, Predicate<String> a_predicates)
		{
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