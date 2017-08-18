package com.statistic.fileformat.xml;

import java.io.File;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.viewer.IFormatViewer;
import com.statistic.fileformat.IFileFormat;

public class XmlFormat implements IFileFormat
{

	private String[]		m_extension		= new String[] { ".xml" };

	private IFormatViewer	m_XmlViewer	= new XmlViewer();

	@Override
	public String[] getExtensions()
	{
		return m_extension.clone();
	}

	@Override
	public AbstractStatistic getStatistic(File a_file)
	{
		return new XmlStatistic(a_file);
	}

	@Override
	public IFormatViewer getFormatViewer()
	{
		return m_XmlViewer;
	}

	@Override
	public String toString()
	{
		return "Xml";
	}
}