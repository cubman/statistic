package com.statistic.fileformat.java;

import java.io.File;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;

/**
 * 
 * Формат файла : JAVA
 *
 */
public class JavaFormat implements IFileFormat
{

	private String[]	m_extension	= new String[] { ".java" };

	private String[]	m_nature	= new String[] { "org.eclipse.jdt.core.javanature" };

	@Override
	public String[] getExtensions()
	{
		return m_extension.clone();
	}

	@Override
	public AbstractStatistic getStatistic(File a_file)
	{
		return new JavaStatistic(a_file, this);
	}

	@Override
	public String toString()
	{
		return "Java";
	}

	@Override
	public String[] getNatures()
	{
		return m_nature;
	}
}
