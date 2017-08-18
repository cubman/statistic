package com.statistic.fileformat.xml;

import java.io.File;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;
import com.statistic.fileformat.IFormatViewer;

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