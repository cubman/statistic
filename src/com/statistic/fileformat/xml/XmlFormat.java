package com.statistic.fileformat.xml;

import java.io.File;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;

public class XmlFormat implements IFileFormat
{

	private String[]		m_extension		= new String[] { ".xml" };

	@Override
	public String[] getExtensions()
	{
		return m_extension.clone();
	}

	@Override
	public AbstractStatistic getStatistic(File a_file)
	{
		return new XmlStatistic(a_file, this);
	}

	@Override
	public String toString()
	{
		return "Xml";
	}

	@Override
	public String[] getNatures()
	{
		// TODO Auto-generated method stub
		return null;
	}
}