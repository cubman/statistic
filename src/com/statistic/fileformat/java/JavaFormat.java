package com.statistic.fileformat.java;

import java.io.File;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.viewer.IFormatViewer;
import com.statistic.fileformat.IFileFormat;

public class JavaFormat implements IFileFormat
{

	private String[]		m_extension		= new String[] { ".java" };

	private IFormatViewer	m_JavaViewer	= new JavaViewer();

	@Override
	public String[] getExtensions()
	{
		return m_extension.clone();
	}

	@Override
	public AbstractStatistic getStatistic(File a_file)
	{
		return new JavaStatistic(a_file);
	}

	@Override
	public IFormatViewer getFormatViewer()
	{
		return m_JavaViewer;
	}

	@Override
	public String toString()
	{
		return "Java";
	}
}
