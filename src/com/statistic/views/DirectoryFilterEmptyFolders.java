package com.statistic.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.folders.DirecroryStructure;

//фильтровать папки, выводимые на экран
public class DirectoryFilterEmptyFolders extends ViewerFilter
{
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element)
	{
		if(element instanceof AbstractStatistic)
			return true;

		DirecroryStructure direcroryStructure = (DirecroryStructure) element;
		return direcroryStructure.getAmountOfFile() != 0;
	}
}
