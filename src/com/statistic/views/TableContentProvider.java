package com.statistic.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.statistic.table.StatisticStructure;

//полуить данные из словаря
public class TableContentProvider implements IStructuredContentProvider
{
	public Object[] getElements(Object inputElement)
	{
		List<StatisticStructure> result = new ArrayList<>();

		for(Map.Entry<String, StatisticStructure> enter : ((Map<String, StatisticStructure>) inputElement)
				.entrySet())
			result.add(enter.getValue());

		return result.toArray();
	}
}
