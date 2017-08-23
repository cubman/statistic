package com.statistic.fileformat.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;

import com.statistic.count.Activator;
import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;
import com.statistic.table.StatisticStructure;

public class JavaStatistic extends AbstractStatistic
{
	public static final String	ALL_LINES_FILE					= "java.allLinesFile";
	public static final String	INCLUDED_LIBRARY_FILE			= "java.libraryFile";
	public static final String	CODE_LINES_FILE					= "java.codeLinesFile";
	public static final String	COMMENT_LINES_FILE				= "java.commentLinesFile";
	public static final String	EMPTY_LINES_FILE				= "java.emptyLinesFile";
	public static final String	AMOUNT_OF_METHODS_FILE			= "java.amountOfMethodsFile";
	public static final String	COMMENT_TO_CODE_RATE_FILE		= "java.commentToCodeFile";

	public static final String	COMMENT_TO_CODE_RATE_DIRECTORY	= "java.commentToCodeDirectory";
	public static final String	THE_BIGGEST_FILE_DIRECTORY		= "java.theBigestFileDirectory";
	public static final String	WORST_COMMENTED_FILE_DIRECTORY	= "java.worstCommentedFileDirectory";
	public static final String	BEST_COMMENTED_FILE_DIRECTORY	= "java.bestCommentedFileDirectory";
	public static final String	FILES_AMOUNT_DIRECTORY			= "java.amountOfFileDetectedDirectory";
	public static final String	ALL_LINES_DIRECTORY				= "java.allLinesDirectory";
	public static final String	CODE_LINES_DIRECTORY			= "java.codeLinesDirectory";
	public static final String	COMMENT_LINES_DIRECTORY			= "java.commentLinesDirectory";

	public JavaStatistic(File a_file, IFileFormat a_javaFormat)
	{
		super(a_file, a_javaFormat);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ImageDescriptor getImage()
	{
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"/icons/formatter-java.gif");
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

		m_restriction.put(CODE_LINES_FILE, new RestrictionPair(string -> !isOpenBracket(string)
				&& !isCloseBracket(string) && !isPackage(string) && !isOnlySmallComment(string)));

		// инициализация потока чтения
		try(BufferedReader bufferedReader = new BufferedReader(
				new FileReader(m_file.getAbsolutePath())))
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
						//
						for(Map.Entry<String, RestrictionPair> mEntry : m_restriction.entrySet())
							if(mEntry.getValue().getRestriction().test(currentLine))
							{
								RestrictionPair restrictionPair = mEntry.getValue();
								restrictionPair.addValue();

								if(!restrictionPair.getContinueSearch())
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

	public Map<String, StatisticStructure> getCountedDirectoryStatistic(
			List<AbstractStatistic> a_list, int a_minCodeLines)
	{
		// самый плохооткомментированный
		AbstractStatistic badFormatedFile = null;
		// хорошо откомментированный
		AbstractStatistic bestFormatedFile = null;
		// самый большой фаил
		AbstractStatistic theBiggestFile = null;

		int bigFile = -1;
		double badCommented = Double.MAX_VALUE, bestCommented = 0.0;
		int allLines = 0, codeLines = 0, commentLine = 0;

		// для каждой статистики о файле
		for(AbstractStatistic abstractStatistic : a_list)
		{
			// получить статистику о дерриктории
			Map<String, StatisticStructure> statisticValue = abstractStatistic
					.getDirectoryStatistc();

			// получение готовых данных
			int allLinesLoc = (int) statisticValue.get(JavaStatistic.ALL_LINES_DIRECTORY)
					.getValue(),
					codeLinesLoc = (int) statisticValue.get(JavaStatistic.CODE_LINES_DIRECTORY)
							.getValue(),
					commentLinesLoc = (int) statisticValue
							.get(JavaStatistic.COMMENT_LINES_DIRECTORY).getValue();

			// сбор данных о текущем файле
			allLines += allLinesLoc;
			codeLines += codeLinesLoc;
			commentLine += commentLinesLoc;

			// самый большой файл
			if(bigFile < allLinesLoc)
			{
				bigFile = allLinesLoc;
				theBiggestFile = abstractStatistic;
			}

			// самый плохооткомментированный код
			if((commentLinesLoc == 0 || badCommented > (double) commentLinesLoc / allLinesLoc)
					&& codeLinesLoc > a_minCodeLines)
			{
				badCommented = commentLinesLoc == 0 ? 0 : (double) commentLinesLoc / allLinesLoc;
				badFormatedFile = abstractStatistic;
			}

			// лучшеоткомментированный код
			if(commentLinesLoc != 0 && bestCommented < (double) commentLinesLoc / allLinesLoc
					&& codeLinesLoc > a_minCodeLines)
			{
				bestCommented = (double) commentLinesLoc / allLinesLoc;
				bestFormatedFile = abstractStatistic;
			}

		}

		// заполнение выходных данных
		Map<String, StatisticStructure> resStat = new LinkedHashMap<>();

		resStat.put(FILES_AMOUNT_DIRECTORY, new StatisticStructure(FILES_AMOUNT_DIRECTORY,
				"Количество исследованных файлов", a_list.size()));

		resStat.put(JavaStatistic.ALL_LINES_DIRECTORY, new StatisticStructure(
				JavaStatistic.ALL_LINES_DIRECTORY, "Общее количество строк", allLines));

		resStat.put(JavaStatistic.CODE_LINES_DIRECTORY, new StatisticStructure(
				JavaStatistic.CODE_LINES_DIRECTORY, "Строки кода", codeLines));

		resStat.put(JavaStatistic.COMMENT_LINES_DIRECTORY, new StatisticStructure(
				JavaStatistic.COMMENT_LINES_DIRECTORY, "Комментирующие строки", commentLine));

		resStat.put(COMMENT_TO_CODE_RATE_DIRECTORY, new StatisticStructure(
				COMMENT_TO_CODE_RATE_DIRECTORY, "Отношение кода к комментариям",
				commentLine == 0 ? "Комментарии отсутствуют"
						: String.format("%.2f %%", ((double) commentLine / codeLines) * 100)));

		resStat.put(THE_BIGGEST_FILE_DIRECTORY, new StatisticStructure(THE_BIGGEST_FILE_DIRECTORY,
				"Самый большой файл", theBiggestFile.comeFromPath()));

		resStat.put(WORST_COMMENTED_FILE_DIRECTORY,
				new StatisticStructure(WORST_COMMENTED_FILE_DIRECTORY,
						"Плохо откомментированный файл",
						badFormatedFile == null ? "Не было найдено удовлетворяющего условию файла"
								: badFormatedFile.comeFromPath()));

		resStat.put(BEST_COMMENTED_FILE_DIRECTORY,
				new StatisticStructure(BEST_COMMENTED_FILE_DIRECTORY,
						"Лучше всего откомментированный файл",
						bestFormatedFile == null ? "Не было найдено удовлетворяющего условию файла"
								: bestFormatedFile.comeFromPath()));

		return resStat;
	}

	public Map<String, StatisticStructure> getCountedFileStatistic()
	{
		// заполнение выходных данных
		Map<String, StatisticStructure> statisticValue = new LinkedHashMap<>(getFileStatistc());

		statisticValue.put(COMMENT_TO_CODE_RATE_FILE,
				new StatisticStructure(COMMENT_TO_CODE_RATE_FILE, "Отношение кода к комментариям",
						(int) statisticValue.get(COMMENT_LINES_FILE).getValue() == 0
								? "Комментарии отсутствуют"
								: String.format("%.2f %%", Double.valueOf(
										(int) statisticValue.get(COMMENT_LINES_FILE).getValue())
										/ (int) statisticValue.get(CODE_LINES_FILE).getValue()
										* 100)));

		// отображение данных на экране статистики
		return statisticValue;
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
