package com.statistic.fileformat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.statistic.table.StatisticStructure;

public class TreeOutFormat
{
	private IFileFormat m_fileFormat;
	private Map<String, StatisticStructure> m_Statistic;
	
	public TreeOutFormat(IFileFormat a_fileFormat, Map<String, StatisticStructure> a_map)
	{
		m_fileFormat = a_fileFormat;
		m_Statistic = a_map;
	}
	
	public String getFileFormat()
	{
		return m_fileFormat.toString();
	}
	
	public List <StatisticStructure> getStatistic(){
		List<StatisticStructure> rStatisticStructures = new ArrayList<>();
		
		for (Map.Entry<String, StatisticStructure> rEntry : m_Statistic.entrySet())
			rStatisticStructures.add(rEntry.getValue());
		
		return rStatisticStructures;
	}
}
