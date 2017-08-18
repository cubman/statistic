package com.statistic.views;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.statistic.table.StatisticStructure;

//отображение статистики
public class TableLabelProvider implements ITableLabelProvider
{

	@Override
	public void addListener(ILabelProviderListener a_listener)
	{
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public boolean isLabelProperty(Object a_element, String a_property)
	{
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener a_listener)
	{
	}

	@Override
	public Image getColumnImage(Object a_element, int a_columnIndex)
	{
		return null;
	}

	@Override
	public String getColumnText(Object a_element, int a_columnIndex)
	{
		StatisticStructure ss = (StatisticStructure) a_element;
		switch(a_columnIndex)
		{
		case 0:
			return ss.getDiscription();
		case 1:
			return ss.getValue().toString();
		default:
			return null;
		}

	}
}