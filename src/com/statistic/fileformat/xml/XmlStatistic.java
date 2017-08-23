package com.statistic.fileformat.xml;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;

import com.statistic.count.Activator;
import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;
import com.statistic.table.StatisticStructure;

public class XmlStatistic extends AbstractStatistic
{

	public static final String	ALL_LINES_FILE					= "xml.allLinesFile";
	public static final String	INCLUDED_LIBRARY_FILE			= "xml.libraryFile";
	public static final String	CODE_LINES_FILE					= "xml.codeLinesFile";
	public static final String	COMMENT_LINES_FILE				= "xml.commentLinesFile";
	public static final String	EMPTY_LINES_FILE				= "xml.emptyLinesFile";
	public static final String	AMOUNT_OF_METHODS_FILE			= "xml.amountOfMethodsFile";
	public static final String	COMMENT_TO_CODE_RATE_FILE		= "xml.commentToCodeFile";

	public static final String	COMMENT_TO_CODE_RATE_DIRECTORY	= "xml.commentToCodeDirectory";
	public static final String	THE_BIGGEST_FILE_DIRECTORY		= "xml.theBigestFileDirectory";
	public static final String	WORST_COMMENTED_FILE_DIRECTORY	= "xml.worstCommentedFileDirectory";
	public static final String	BEST_COMMENTED_FILE_DIRECTORY	= "xml.bestCommentedFileDirectory";
	public static final String	FILES_AMOUNT_DIRECTORY			= "xml.amountOfFileDetectedDirectory";
	public static final String	ALL_LINES_DIRECTORY				= "xml.allLinesDirectory";
	public static final String	CODE_LINES_DIRECTORY			= "xml.codeLinesDirectory";
	public static final String	COMMENT_LINES_DIRECTORY			= "xml.commentLinesDirectory";
	
	public XmlStatistic(File a_file, IFileFormat a_xmlFormat)
	{
		super(a_file, a_xmlFormat);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void countStatistic()
	{
		// статистика для конкретного файла
				m_statisticForFile.put(ALL_LINES_FILE,
						new StatisticStructure(ALL_LINES_FILE, "Общее количество строк", 22));

				m_statisticForFile.put(INCLUDED_LIBRARY_FILE, new StatisticStructure(INCLUDED_LIBRARY_FILE,
						"Подключенные библиотеки",5));

				m_statisticForFile.put(CODE_LINES_FILE, new StatisticStructure(CODE_LINES_FILE,
						"Строки кода", 11));

				m_statisticForFile.put(COMMENT_LINES_FILE, new StatisticStructure(COMMENT_LINES_FILE,
						"Комментирующие строки", 3));

				m_statisticForFile.put(EMPTY_LINES_FILE, new StatisticStructure(EMPTY_LINES_FILE,
						"Пустые строки", 7));

				m_statisticForFile.put(AMOUNT_OF_METHODS_FILE,
						new StatisticStructure(AMOUNT_OF_METHODS_FILE, "Количество методов",
								2));

	}
	

	@Override
	public ImageDescriptor getImage()
	{
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"/icons/xmldoc.gif");
	}

	@Override
	public Map<String, StatisticStructure> getCountedDirectoryStatistic(
			List<AbstractStatistic> a_list, int a_minCodeLines)
	{
		Map<String, StatisticStructure> resStat = new LinkedHashMap<>();
		
		resStat.put(FILES_AMOUNT_DIRECTORY,
				new StatisticStructure(FILES_AMOUNT_DIRECTORY,
						"Количество исследованных файлов",
						a_list.size()));
		
		resStat.put(ALL_LINES_DIRECTORY, new StatisticStructure(
				ALL_LINES_DIRECTORY, "Общее количество строк", 22));

		resStat.put(CODE_LINES_DIRECTORY, new StatisticStructure(
				CODE_LINES_DIRECTORY, "Строки кода", 11));

		resStat.put(COMMENT_LINES_DIRECTORY, new StatisticStructure(
				COMMENT_LINES_DIRECTORY, "Комментирующие строки", 3));

		resStat.put(COMMENT_TO_CODE_RATE_DIRECTORY, new StatisticStructure(
				COMMENT_TO_CODE_RATE_DIRECTORY, "Отношение кода к комментариям",
				3 == 0 ? "Комментарии отсутствуют"
						: String.format("%.2f %%", ((double) 3 / 11) * 100)));

		
		return resStat;
	}

	@Override
	public Map<String, StatisticStructure> getCountedFileStatistic()
	{
		Map<String, StatisticStructure> statisticValue = new LinkedHashMap<>(
				getFileStatistc());

		statisticValue.put(COMMENT_TO_CODE_RATE_FILE, new StatisticStructure(
				COMMENT_TO_CODE_RATE_FILE, "Отношение кода к комментариям",
				(int) statisticValue.get(COMMENT_LINES_FILE).getValue() == 0
						? "Комментарии отсутствуют"
						: String.format("%.2f %%", Double
								.valueOf((int) statisticValue.get(COMMENT_LINES_FILE)
										.getValue())
								/ (int) statisticValue.get(CODE_LINES_FILE).getValue()
								* 100)));

		// отображение данных на экране статистики
		return statisticValue;
	}
}
