package com.statistic.file.viewer;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;

import com.statistic.count.Activator;
import com.statistic.file.count.AbstractStatistic;
import com.statistic.table.StatisticStructure;

public class XmlViewer implements IFormatViewer
{

	@Override
	public ImageDescriptor getFileImage()
	{
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"/icons/xmldoc.gif");
	}

	@Override
	public Map<String, StatisticStructure> getCountedDirectoryStatistic(
			List<AbstractStatistic> a_list)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, StatisticStructure> getCountedFileStatistic(AbstractStatistic a_list)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
