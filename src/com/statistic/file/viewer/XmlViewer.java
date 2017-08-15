package com.statistic.file.viewer;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Table;

import com.statistic.count.Activator;
import com.statistic.file.count.AbstractStatistic;

public class XmlViewer implements IFormatViewer
{

	@Override
	public void setAndPrintDirectory(List<AbstractStatistic> a_list, Table a_table)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setAndPrintFolder(AbstractStatistic a_list, Table a_table)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ImageDescriptor getFileImage()
	{
		return Activator.imageDescriptorFromPlugin("org.eclipse.e4.ui.workbench.swt", "/icons/full/obj16/fldr_obj.gif");
	}

}
