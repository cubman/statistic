package com.statistic.folders;

import java.util.List;

import com.statistic.file.count.AbstractStatistic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DirecroryStructure
{
	// ссылка на родителя
	public DirecroryStructure m_parent = null;
	
	// список дочерних директорий
	public List<DirecroryStructure> m_lds = new ArrayList<>();
	
	// список файлов с форматом	
	public List<AbstractStatistic> m_las = new ArrayList<>();
	
	// количество файлов с указанным форматом
	public int m_amountOfFiles = 0;
	
	// наименование директории
	public String m_directoryName;
	
	// создание дерева папок
	public static DirecroryStructure createDirectory(File head, FileFormat a_fileFormat)
	{
		DirecroryStructure direcroryStructure = new DirecroryStructure();
		direcroryStructure.m_directoryName = head.getName();
		
		
		if (head.isDirectory()) {
		    search(head, direcroryStructure, a_fileFormat);
		} 
		
		return direcroryStructure;
	}
	
	// формирование списка
	private static int search(File a_file, DirecroryStructure a_direcroryStructure, FileFormat a_fileFormat)
	{
		// если файл - папка
		if (a_file.isDirectory()) {
			  System.out.println("Searching directory ... " + a_file.getAbsoluteFile());

		            //do you have permission to read this directory?
			    if (a_file.canRead()) {
				for (File temp : a_file.listFiles()) {
				    if (temp.isDirectory()) {
				    	// оформление структуры
				    	DirecroryStructure child = new DirecroryStructure();
				    	child.m_parent = a_direcroryStructure;
				    	child.m_directoryName = temp.getName();
				    	a_direcroryStructure.m_lds.add(child);
				    	// подсчет количества папок содержащих файлы
				    	a_direcroryStructure.m_amountOfFiles += search(temp, child, a_fileFormat);
				    } else {
				    	// файл имеет указанный формат
					if (temp.getName().endsWith(FileFormat.toFormat(a_fileFormat))) {
						a_direcroryStructure.m_las.add(FileFormat.toAbstractStatistic(a_fileFormat, temp) );
				    }

				  }
			    }
				a_direcroryStructure.m_amountOfFiles += a_direcroryStructure.m_las.size();
			 } else {
				System.out.println(a_file.getAbsoluteFile() + "Permission Denied");
			 }
			    
		   }
		
		return a_direcroryStructure.m_amountOfFiles;
	}
}
