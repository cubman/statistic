package com.statistic.folders;

import java.util.List;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;

import java.io.File;
import java.util.ArrayList;

public class DirecroryStructure
{
	// ссылка на родителя
	private DirecroryStructure			m_parent					= null;

	// список дочерних директорий
	private List<DirecroryStructure>	m_listDirectoryStructure	= new ArrayList<>();

	// список файлов с форматом
	private List<AbstractStatistic>		m_listAbstractStatistic		= new ArrayList<>();

	// количество файлов с указанным форматом
	private int							m_amountOfFiles				= 0;

	// наименование директории
	private String						m_directoryName;

	// полный путь до папки
	private String						m_fullPath;

	// получить количество файлов с указанным форматом
	public int getAmountOfFile()
	{
		return m_amountOfFiles;
	}

	// получить наименование директории
	public String getDirectoryName()
	{
		return m_directoryName;
	}

	// получить полный путь до папки
	public String getFullDirectoryPath()
	{
		return m_fullPath;
	}

	// вернуть предка для папки
	public DirecroryStructure getParent()
	{
		return m_parent;
	}

	// вернуть предка для папки
	public void setParent(DirecroryStructure a_directoryName)
	{
		m_parent = a_directoryName;
	}

	// получить список подпапок
	public List<DirecroryStructure> getListDirectoryStructure()
	{
		return m_listDirectoryStructure;
	}

	// получить список файлов в папке
	public List<AbstractStatistic> getListAbstractStatistic()
	{
		return m_listAbstractStatistic;
	}

	// создание дерева папок
	public DirecroryStructure(File head, List<IFileFormat> a_fileFormat)
	{
		// DirecroryStructure direcroryStructure = new DirecroryStructure();
		m_directoryName = head.getName();
		m_fullPath = head.getAbsolutePath();

		if(head.isDirectory())
			search(head, a_fileFormat);
	}

	private DirecroryStructure(DirecroryStructure a_parent, String a_directoryName,
			String a_fullPathToDirectory)
	{
		m_parent = a_parent;
		m_directoryName = a_directoryName;
		m_fullPath = a_fullPathToDirectory;

	}

	// формирование списка
	private int search(File a_file, List<IFileFormat> a_fileFormat)
	{
		// если файл - папка
		if(a_file.isDirectory())
		{
			// System.out.println("Searching directory ... " +
			// a_file.getAbsoluteFile());
			File[] listFile = a_file.listFiles();
			// do you have permission to read this directory?
			if(a_file.canRead() && listFile != null)
			{
				for(File temp : listFile)
				{
					if(temp.isDirectory() && !a_file.getName().startsWith("."))
					{
						// оформление структуры
						DirecroryStructure child = new DirecroryStructure(this, temp.getName(),
								temp.getAbsolutePath());

						m_listDirectoryStructure.add(child);
						// подсчет количества папок содержащих файлы
						m_amountOfFiles += child.search(temp, a_fileFormat);
					}
					else
					{
						for(IFileFormat fileFormat : a_fileFormat)
							for(String format : fileFormat.getExtensions())
								// файл имеет указанный формат
								if(temp.getName().endsWith(format))
								{
									AbstractStatistic abstractStatistic = fileFormat
											.getStatistic(temp);
									abstractStatistic.countStatistic();
									m_listAbstractStatistic.add(abstractStatistic);
									break;
								}
					}
				}
				m_amountOfFiles += m_listAbstractStatistic.size();
			}
			else
			{
				System.out.println(a_file.getAbsoluteFile() + "Permission Denied");
			}

		}

		return m_amountOfFiles;
	}

	// получить статистику для директории
	public static List<AbstractStatistic> getStatisticForSelectedFolder(
			DirecroryStructure a_direcroryStructure)
	{
		List<AbstractStatistic> result = new ArrayList<>();
		countStatisticForDirectory(a_direcroryStructure, result, a_direcroryStructure.m_fullPath);

		return result;
	}

	// коммулята всех файлов в указанной директории
	private static void countStatisticForDirectory(DirecroryStructure a_direcroryStructure,
			List<AbstractStatistic> a_statistic, String startedPath)
	{
		for(DirecroryStructure direcroryStructure : a_direcroryStructure.m_listDirectoryStructure)
			// если есть потомки
			if(direcroryStructure.m_amountOfFiles > 0)
				countStatisticForDirectory(direcroryStructure, a_statistic, startedPath);

		// инициализировать стартовыой директорией
		a_direcroryStructure.m_listAbstractStatistic.stream()
				.forEach(element -> element.setFilePathFrom(startedPath));

		// добавление в выходной массив структуру файла
		a_statistic.addAll(a_direcroryStructure.m_listAbstractStatistic);

	}
}
