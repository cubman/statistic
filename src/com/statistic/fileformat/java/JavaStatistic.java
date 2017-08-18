package com.statistic.fileformat.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.statistic.fileformat.AbstractStatistic;
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
		int allLines = 0;
		boolean bigComment = false;

		m_restriction.put(EMPTY_LINES_FILE, new RestrictionPair(string -> isEmptyString(string)));

		m_restriction.put(INCLUDED_LIBRARY_FILE, new RestrictionPair(string -> isImport(string)));

		m_restriction.put(COMMENT_LINES_FILE,
				new RestrictionPair(true, string -> isAfterTextSmallComment(string)));

		m_restriction.put(AMOUNT_OF_METHODS_FILE, new RestrictionPair(string -> isMethod(string)));

		m_restriction.put(CODE_LINES_FILE, new RestrictionPair(
				string -> !isOpenBracket(string) && !isCloseBracket(string) && !isPackage(string) && !isOnlySmallComment(string)));

		// инициализация потока чтения
		try(BufferedReader bufferedReader = new BufferedReader(
				new FileReader(file.getAbsolutePath())))
		{
			// строка в тексте
			String currentLine;

			while((currentLine = bufferedReader.readLine()) != null)
			{
				/*
				 * проверка на принадлежность многострочному коментарию пока не
				 * закончится проверка на пустоту строки импорты однострочные
				 * комментарии открывающая / закрывающая строка / package
				 */
				if(bigComment)
				{
					if(isBigCommentEnd(currentLine))
						bigComment = false;

					m_restriction.get(COMMENT_LINES_FILE).addValue();
				}
				else
					if(isBigCommentStart(currentLine))
					{
						bigComment = true;
						if(isBigCommentEnd(currentLine))
							bigComment = false;

						m_restriction.get(COMMENT_LINES_FILE).addValue();
					}
					else 
						for(Map.Entry<String, RestrictionPair> mEntry : m_restriction.entrySet())
							if(mEntry.getValue().getRestriction().test(currentLine))
							{
								RestrictionPair restrictionPair = mEntry.getValue();
								restrictionPair.addValue();
								
								if (!restrictionPair.getContinueSearch())
									break;								
							}
					
				++allLines;
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		// статистика для дирректории
		m_statisticForDirectory.put(ALL_LINES_DIRECTORY,
				new StatisticStructure(ALL_LINES_DIRECTORY, "Общее количество строк", allLines));

		m_statisticForDirectory.put(CODE_LINES_DIRECTORY,
				new StatisticStructure(CODE_LINES_DIRECTORY, "Строки кода",
						m_restriction.get(CODE_LINES_FILE).getValue()));

		m_statisticForDirectory.put(COMMENT_LINES_DIRECTORY,
				new StatisticStructure(COMMENT_LINES_DIRECTORY, "Комментирующие строки",
						m_restriction.get(COMMENT_LINES_FILE).getValue()));

		// статистика для конкретного файла
		m_statisticForFile.put(ALL_LINES_FILE,
				new StatisticStructure(ALL_LINES_FILE, "Общее количество строк", allLines));

		m_statisticForFile.put(INCLUDED_LIBRARY_FILE, new StatisticStructure(INCLUDED_LIBRARY_FILE,
				"Подключенные библиотеки", m_restriction.get(INCLUDED_LIBRARY_FILE).getValue()));

		m_statisticForFile.put(CODE_LINES_FILE, new StatisticStructure(CODE_LINES_FILE,
				"Строки кода", m_restriction.get(CODE_LINES_FILE).getValue()));

		m_statisticForFile.put(COMMENT_LINES_FILE, new StatisticStructure(COMMENT_LINES_FILE,
				"Комментирующие строки", m_restriction.get(COMMENT_LINES_FILE).getValue()));

		m_statisticForFile.put(EMPTY_LINES_FILE, new StatisticStructure(EMPTY_LINES_FILE,
				"Пустые строки", m_restriction.get(EMPTY_LINES_FILE).getValue()));

		m_statisticForFile.put(AMOUNT_OF_METHODS_FILE,
				new StatisticStructure(AMOUNT_OF_METHODS_FILE, "Количество методов",
						m_restriction.get(AMOUNT_OF_METHODS_FILE).getValue()));
	}

	// открывающая скобка фигурная
	private boolean isOpenBracket(String a_string)
	{
		return a_string.matches("^\\s*\\{.*$");
	}

	// закрывающая скобка фигурная
	private boolean isCloseBracket(String a_string)
	{
		return a_string.matches("^\\s*\\}.*$");
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
	private boolean isOnlySmallComment(String a_string)
	{
		return a_string.matches("^(\\t|\\s*)//.*");
	}
	
	// однострочный комментарий
	private boolean isAfterTextSmallComment(String a_string)
	{
		return a_string.matches(".*//.*");
	}

	// начало многострочного комментария
	private boolean isBigCommentStart(String a_string)
	{
		return a_string.matches(".*/\\*\\*.*");
	}

	// окончание многострочного комментария
	private boolean isBigCommentEnd(String a_string)
	{
		return a_string.matches(".*\\*/.*");
	}
}
