package com.statistic.count;

import java.util.ArrayList;
import java.util.List;

import com.statistic.fileformat.IFileFormat;

public class FileRestriction
{
	private List<IFileFormat> m_selectedFormats = new ArrayList<>();
	private int m_linesAmount = 0;
	
	public FileRestriction(int a_lineAmount, List<IFileFormat> a_selectedFormats)
	{
		m_linesAmount = a_lineAmount;
		m_selectedFormats = a_selectedFormats;
	}	
	
	public int getLineAmount()
	{
		return m_linesAmount;
	}
	
	public List<IFileFormat> getSelectedFormats()
	{
		return m_selectedFormats;
	}
	
	public void setLineAmount(int a_lineAmount)
	{
		m_linesAmount = a_lineAmount;
	}
	
	public void setSelectedFormats(List<IFileFormat> a_selectedFormats )
	{
		m_selectedFormats = a_selectedFormats;
	}
}
