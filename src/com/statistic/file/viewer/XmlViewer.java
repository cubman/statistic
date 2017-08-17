package com.statistic.file.viewer;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;

import com.statistic.count.Activator;
import com.statistic.file.count.AbstractStatistic;

public class XmlViewer implements IFormatViewer
{

	@Override
	public void setAndPrintDirectory(List<AbstractStatistic> a_list)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setAndPrintFolder(AbstractStatistic a_list)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ImageDescriptor getFileImage()
	{
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"/icons/xmldoc.gif");
	}

}
