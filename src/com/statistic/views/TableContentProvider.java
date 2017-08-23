package com.statistic.views;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.statistic.fileformat.TreeOutFormat;

//полуить данные из словаря
class TableContentProvider implements ITreeContentProvider
{
	public Object[] getChildren(Object parentElement)
	{
		if(parentElement instanceof List)
			return ((List<?>) parentElement).toArray();
		if(parentElement instanceof TreeOutFormat)
			return ((TreeOutFormat) parentElement).getStatistic().toArray();
		
		return new Object[0];
	}

	public Object getParent(Object element)
	{
		if(element instanceof Map<?, ?>)
			return (TreeOutFormat) element;
		return null;
	}

	public boolean hasChildren(Object element)
	{
		if(element instanceof List)
			return ((List<?>) element).size() > 0;
		if(element instanceof TreeOutFormat)
			return ((TreeOutFormat) element).getStatistic().size() > 0;
		
		return false;
	}

	public Object[] getElements(Object cities)
	{
		return getChildren(cities);
	}
}