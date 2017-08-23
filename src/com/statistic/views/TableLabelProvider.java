package com.statistic.views;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.statistic.fileformat.TreeOutFormat;
import com.statistic.table.StatisticStructure;

//отображение статистики
class TableLabelProvider implements ITableLabelProvider
{

	public Image getColumnImage(Object element, int columnIndex)
	{
		return null;
	}

	public String getColumnText(Object element, int columnIndex)
	{
		switch(columnIndex)
		{
		case 0:
			if(element instanceof TreeOutFormat)
				return ((TreeOutFormat) element).getFileFormat();
			else
				if(element instanceof StatisticStructure)
					return ((StatisticStructure) element).getDiscription();
				else
					return "error";

		case 1:
			if(element instanceof List<?> || element instanceof TreeOutFormat)
				return "";
			else
				return ((StatisticStructure) element).getValue().toString();
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener)
	{
	}

	public void dispose()
	{
	}

	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

	public void removeListener(ILabelProviderListener listener)
	{
	}
}