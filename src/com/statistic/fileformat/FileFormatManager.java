package com.statistic.fileformat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProjectNatureDescriptor;

/*
 * Хранитель ресурсов
 */
public class FileFormatManager
{
	// список всех форматов
	private List<IFileFormat>			m_fileFormats;

	// ссылка на себя(для синглетона)
	private static FileFormatManager	m_FileFormatManager;

	private FileFormatManager()
	{
		m_FileFormatManager = this;

		m_fileFormats = new ArrayList<>();
	}

	// добавить формат поиска
	public void addFormat(IFileFormat a_fileFormat)
	{
		for (IFileFormat format : m_fileFormats)
			if (format.equals(a_fileFormat))
				return;
		
		m_fileFormats.add(a_fileFormat);
	}

	// получить список форматов
	public List<IFileFormat> getFileFormats()
	{
		return m_fileFormats;
	}

	// получить экземпляр
	public static FileFormatManager getInstance()
	{
		if (m_FileFormatManager == null)
			m_FileFormatManager = new FileFormatManager();
		
		return m_FileFormatManager;
	}
	
	public List<IFileFormat> getFileFormatByProjectNature(String[] a_descriptor)
	{
		List<IFileFormat> resList = new ArrayList<>();
		
		for (String aDescriptor : a_descriptor)
			for (IFileFormat format : m_fileFormats)
				for (String nature : format.getNatures())
					if (nature != null && aDescriptor.equals(nature))
					{
						resList.add(format);
						break;
					}
		
		return resList;
	}
}
