package com.statistic.file.count;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.statistic.count.Activator;
import com.statistic.table.StatisticStructure;

public class JavaStatistic extends AbstractStatistic
{
	public static final String	ALL_LINES_FILE			= "java.allLinesFile";
	public static final String	INCLUDED_LIBRARY_FILE	= "java.libraryFile";
	public static final String	CODE_LINES_FILE			= "java.codeLinesFile";
	public static final String	COMMENT_LINES_FILE		= "java.commentLinesFile";
	public static final String	EMPTY_LINES_FILE		= "java.emptyLinesFile";
	public static final String	AMOUNT_OF_METHODS_FILE	= "java.amountOfMethodsFile";

	public static final String	ALL_LINES_DIRECTORY		= "java.allLinesDirectory";
	public static final String	CODE_LINES_DIRECTORY	= "java.codeLinesDirectory";
	public static final String	COMMENT_LINES_DIRECTORY	= "java.commentLinesDirectory";

	public JavaStatistic(File a_file)
	{
		super(a_file);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void countStatistic()
	{
		BufferedReader br = null;
		int allLines = 0, codeLines = 0, commentLines = 0, importLines = 0, emptyLines = 0,
				methodLines = 0;
		boolean bigComment = false;
		try
		{
			// инициализация потока чтения
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			// строка в тексте
			String sCurrentLine;

			while((sCurrentLine = br.readLine()) != null)
			{
				/*
				 * проверка на принадлежность многострочному коментарию пока не
				 * закончится проверка на пустоту строки импорты однострочные
				 * комментарии открывающая / закрывающая строка / package
				 */
				if(bigComment)
				{
					if(isBigCommentEnd(sCurrentLine))
						bigComment = false;

					++commentLines;
				}
				else
					if(isBigCommentStart(sCurrentLine))
					{
						bigComment = true;
						if(isBigCommentEnd(sCurrentLine))
							bigComment = false;

						++commentLines;
					}
					else
						if(isEmptyString(sCurrentLine))
							++emptyLines;
						else
							if(isImport(sCurrentLine))
								++importLines;
							else
								if(isSmallComment(sCurrentLine))
									++commentLines;
								else
									if(isMethod(sCurrentLine))
										++methodLines;
									else
										if(!isOpenBracket(sCurrentLine)
												&& !isCloseBracket(sCurrentLine)
												&& !isPackage(sCurrentLine))
											++codeLines;

				++allLines;
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(br != null)
					br.close();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}

		}

		// статистика для дирректории
		m_statisticForDirectory.put(ALL_LINES_DIRECTORY,
				new StatisticStructure(ALL_LINES_DIRECTORY, "Общее количество строк", allLines));
		m_statisticForDirectory.put(CODE_LINES_DIRECTORY,
				new StatisticStructure(CODE_LINES_DIRECTORY, "Строки кода", codeLines));
		m_statisticForDirectory.put(COMMENT_LINES_DIRECTORY, new StatisticStructure(
				COMMENT_LINES_DIRECTORY, "Комментирующие строки", commentLines));

		// статистика для конкретного файла
		m_statisticForFile.put(ALL_LINES_FILE,
				new StatisticStructure(ALL_LINES_FILE, "Общее количество строк", allLines));
		m_statisticForFile.put(INCLUDED_LIBRARY_FILE, new StatisticStructure(INCLUDED_LIBRARY_FILE,
				"Подключенные библиотеки", importLines));
		m_statisticForFile.put(CODE_LINES_FILE,
				new StatisticStructure(CODE_LINES_FILE, "Строки кода", codeLines));
		m_statisticForFile.put(COMMENT_LINES_FILE,
				new StatisticStructure(COMMENT_LINES_FILE, "Комментирующие строки", commentLines));
		m_statisticForFile.put(EMPTY_LINES_FILE,
				new StatisticStructure(EMPTY_LINES_FILE, "Пустые строки", emptyLines));
		m_statisticForFile.put(AMOUNT_OF_METHODS_FILE,
				new StatisticStructure(AMOUNT_OF_METHODS_FILE, "Количество методов", methodLines));
	}

	// открывающая скобка фигурная
	private boolean isOpenBracket(String a_string)
	{
		return a_string.matches("^\\s*\\{\\s*$");
	}

	// закрывающая скобка фигурная
	private boolean isCloseBracket(String a_string)
	{
		return a_string.matches("^\\s*\\}\\s*$");
	}

	// является методом
	private boolean isMethod(String a_string)
	{
		return a_string.matches("^(\\t|\\s*)(private|public|protected).*\\)\\s*$");
	}

	// строка импорта
	private boolean isImport(String a_string)
	{
		return a_string.matches("^(\\t|\\s*)import .*;$");
	}

	// является название пакета
	private boolean isPackage(String a_string)
	{
		return a_string.matches("^(\\t|\\s*)package .*;$");
	}

	// пустая строка
	private boolean isEmptyString(String a_string)
	{
		return a_string.matches("^\\s*$");
	}

	// однострочный комментарий
	private boolean isSmallComment(String a_string)
	{
		return a_string.matches(".*//.*");
	}

	// начало многострочного комментария
	private boolean isBigCommentStart(String a_string)
	{
		return a_string.matches(".*/\\*.*");
	}

	// окончание многострочного комментария
	private boolean isBigCommentEnd(String a_string)
	{
		return a_string.matches(".*\\*/.*");
	}
}
