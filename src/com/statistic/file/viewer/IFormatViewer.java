package com.statistic.file.viewer;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Table;

import com.statistic.file.count.AbstractStatistic;

public interface IFormatViewer
{
	public void setAndPrintDirectory(List<AbstractStatistic> a_list, Table a_table);

	public void setAndPrintFolder(AbstractStatistic a_list, Table a_table);
	
	public ImageDescriptor getFileImage();
}
