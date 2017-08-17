package com.statistic.folders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.count.JavaStatistic;
import com.statistic.file.count.XmlStatistic;
import com.statistic.file.viewer.IFormatViewer;
import com.statistic.file.viewer.JavaViewer;
import com.statistic.file.viewer.XmlViewer;

public enum FileFormat
{
	// Java | Xml
	Java, Xml;

	// преобразование к текстовому виду
	public static FileFormat toString(String foramt)
	{
		switch(foramt)
		{
		case ".java":
			return FileFormat.Java;

		case ".xml":
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

		case Xml:
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

		case Xml:
			return new XmlStatistic(a_file);

		default:
			System.out.println("Format was not recognised");
			return null;
		}
	}

	// преобразование к формату класса-статистика
	public static IFormatViewer toTableViewer(FileFormat a_fileFormat, TableViewer a_tableViewer)
	{
		switch(a_fileFormat)
		{
		case Java:
			return new JavaViewer(a_tableViewer);

		case Xml:
			return new XmlViewer();

		default:
			System.out.println("Format was not recognised");
			return null;
		}
	}

	// Возвращает список доступных расширений
	public static List<FileFormat> getAllPossibleFileFormat()
	{
		ArrayList<FileFormat> arrayList = new ArrayList<>();
		arrayList.add(FileFormat.Java);
		arrayList.add(FileFormat.Xml);
		return arrayList;
	}
}
