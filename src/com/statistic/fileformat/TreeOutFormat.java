package com.statistic.fileformat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.statistic.table.StatisticStructure;

/**
 * Выходные данные в таблице
 */
public class TreeOutFormat
{
	private IFileFormat						m_fileFormat;	// формат выводимой
															// информации
	private Map<String, StatisticStructure>	m_Statistic;	// статистика по
															// указанному
															// формату

	public TreeOutFormat(IFileFormat a_fileFormat, Map<String, StatisticStructure> a_map)
	{
		m_fileFormat = a_fileFormat;
		m_Statistic = a_map;
	}

	// получить расширений файлов поиска
	public String getFileFormat()
	{
		return m_fileFormat.toString();
	}

	// список готовых результатов
	public List<StatisticStructure> getStatistic()
	{
		List<StatisticStructure> rStatisticStructures = new ArrayList<>();

		for(Map.Entry<String, StatisticStructure> rEntry : m_Statistic.entrySet())
			rStatisticStructures.add(rEntry.getValue());

		return rStatisticStructures;
	}
}
