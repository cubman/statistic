package com.statistic.folders;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.statistic.file.count.AbstractStatistic;

import java.io.File;
import java.util.ArrayList;

public class DirecroryStructure implements ITreeContentProvider
{
	// ссылка на родителя
	public DirecroryStructure		m_parent		= null;

	// список дочерних директорий
	public List<DirecroryStructure>	m_lds			= new ArrayList<>();

	// список файлов с форматом
	public List<AbstractStatistic>	m_las			= new ArrayList<>();

	// количество файлов с указанным форматом
	public int						m_amountOfFiles	= 0;

	// наименование директории
	public String					m_directoryName;

	// полный путь до папки
	public String fullPath;
	
	// создание дерева папок
	public static DirecroryStructure createDirectory(File head, FileFormat a_fileFormat)
	{
		DirecroryStructure direcroryStructure = new DirecroryStructure();
		direcroryStructure.m_directoryName = head.getName();
		direcroryStructure.fullPath = head.getAbsolutePath();
		
		if(head.isDirectory())
			search(head, direcroryStructure, a_fileFormat);
		

		return direcroryStructure;
	}

	// формирование списка
	private static int search(File a_file, DirecroryStructure a_direcroryStructure,
			FileFormat a_fileFormat)
	{
		// если файл - папка
		if(a_file.isDirectory())
		{
			// System.out.println("Searching directory ... " +
			// a_file.getAbsoluteFile());
			File [] listFile = a_file.listFiles();
			// do you have permission to read this directory?
			if(a_file.canRead() &&  listFile != null)
			{
				for(File temp : listFile)
				{
					if(temp.isDirectory() && !a_file.getName().startsWith("."))
					{
						// оформление структуры
						DirecroryStructure child = new DirecroryStructure();
						child.m_parent = a_direcroryStructure;
						child.m_directoryName = temp.getName();
						child.fullPath = temp.getAbsolutePath();
						a_direcroryStructure.m_lds.add(child);
						// подсчет количества папок содержащих файлы
						a_direcroryStructure.m_amountOfFiles += search(temp, child, a_fileFormat);
					}
					else
					{
						// файл имеет указанный формат
						if(temp.getName().endsWith(FileFormat.toFormat(a_fileFormat)))
						{
							AbstractStatistic abstractStatistic = FileFormat
									.toAbstractStatistic(a_fileFormat, temp);
							abstractStatistic.countStatistic();
							a_direcroryStructure.m_las.add(abstractStatistic);
						}
					}
				}
				a_direcroryStructure.m_amountOfFiles += a_direcroryStructure.m_las.size();
			}
			else
			{
				System.out.println(a_file.getAbsoluteFile() + "Permission Denied");
			}

		}

		return a_direcroryStructure.m_amountOfFiles;
	}

	// получить все элементы для текущего элемента дерева
	@Override
	public Object[] getElements(Object a_inputElement)
	{
		DirecroryStructure direcroryStructure = (DirecroryStructure) a_inputElement;
		ArrayList<Object> ret = new ArrayList<>(direcroryStructure.m_lds);

		ret.addAll(direcroryStructure.m_las);

		return ret.toArray();
	}

	// получить для корневого дочерние элементы
	@Override
	public Object[] getChildren(Object a_parentElement)
	{
		DirecroryStructure direcroryStructure = (DirecroryStructure) a_parentElement;

		ArrayList<Object> ret = new ArrayList<>(direcroryStructure.m_lds);
		ret.addAll(direcroryStructure.m_las);

		return ret.toArray();
	}

	// получить родительскую ссылку
	@Override
	public Object getParent(Object a_element)
	{
		if(a_element instanceof AbstractStatistic)
			return null;

		DirecroryStructure direcroryStructure = (DirecroryStructure) a_element;
		return direcroryStructure.m_parent;
	}

	// проверить наличие дочерних узлов
	@Override
	public boolean hasChildren(Object a_element)
	{
		if(a_element instanceof AbstractStatistic)
			return false;

		DirecroryStructure direcroryStructure = (DirecroryStructure) a_element;
		ArrayList<Object> ret = new ArrayList<>(direcroryStructure.m_lds);
		ret.addAll(direcroryStructure.m_las);
		return !ret.isEmpty();
	}

	// получить статистику для директории
	public static List<AbstractStatistic> getStatisticForSelectedFolder(
			DirecroryStructure a_direcroryStructure)
	{
		List<AbstractStatistic> result = new ArrayList<>();
		countStatisticForDirectory(a_direcroryStructure, result, a_direcroryStructure.fullPath);

		return result;
	}

	// коммулята всех файлов в указанной директории
	private static void countStatisticForDirectory(DirecroryStructure a_direcroryStructure,
			List<AbstractStatistic> a_statistic, String startedPath)
	{
		for(DirecroryStructure direcroryStructure : a_direcroryStructure.m_lds)
			if (direcroryStructure.m_amountOfFiles > 0)
				countStatisticForDirectory(direcroryStructure, a_statistic, startedPath);
		
		a_direcroryStructure.m_las.stream().forEach(element -> element.setFilePathFrom(startedPath));
				
		a_statistic.addAll(a_direcroryStructure.m_las);

	}

}
