package com.statistic.folders;

import java.io.File;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.count.JavaStatistic;
import com.statistic.file.count.XmlStatistic;

public enum FileFormat
{
	// Java | Xml
	   Java , Xml;
	
	// преобразование к текстовому виду
	public static FileFormat toString(String foramt)
	{
		switch(foramt)
		{
		case ".java":
			return FileFormat.Java;
		
			case ".xml" :
				return Xml;

		default:
			System.out.println("Format was not recognised");
			return null;
		}
	}
	
	// преобразование к формату перечисления
	public static String toFormat(FileFormat a_fileFormat)
	{
		switch(a_fileFormat)
		{
		case Java:
			return ".java";
		
		case Xml  :
			return ".xml";

		default:
			System.out.println("Format was not recognised");
			return null;
		}
	}
	
	// преобразование к формату класса-статистика
	public static AbstractStatistic toAbstractStatistic(FileFormat a_fileFormat, File a_file)
	{
		switch(a_fileFormat)
		{
		case Java:
			return new JavaStatistic(a_file);
		
		case Xml  :
			return new XmlStatistic(a_file);

		default:
			System.out.println("Format was not recognised");
			return null;
		}
	}
}
