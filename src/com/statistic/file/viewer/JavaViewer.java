package com.statistic.file.viewer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;

import com.statistic.count.Activator;
import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.count.JavaStatistic;
import com.statistic.table.StatisticBrowser;
import com.statistic.table.StatisticStructure;

public class JavaViewer implements IFormatViewer
{

	public static final String	COMMENT_TO_CODE_RATE_FILE		= "java.commentToCodeFile";

	public static final String	COMMENT_TO_CODE_RATE_DIRECTORY	= "java.commentToCodeDirectory";
	public static final String	THE_BIGGEST_FILE_DIRECTORY		= "java.theBigestFileDirectory";
	public static final String	WORST_COMMENTED_FILE_DIRECTORY	= "java.worstCommentedFileDirectory";

	TableViewer					m_tableViewer;

	public JavaViewer(TableViewer a_tableViewer)
	{
		m_tableViewer = a_tableViewer;
	}

	@Override
	public void setAndPrintDirectory(List<AbstractStatistic> a_list)
	{
		// самый плохооткомментированный
		AbstractStatistic badFormatedFile = null;
		// самый большой фаил
		AbstractStatistic theBiggestFile = null;

		int bigFile = -1;
		double badCommented = Double.MAX_VALUE;
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
			if(commentLinesLoc == 0 || badCommented > (double) allLinesLoc / commentLinesLoc)
				badCommented = commentLinesLoc == 0 ? 0 : (double) allLinesLoc / commentLinesLoc;
			badFormatedFile = abstractStatistic;
		}

		// заполнение выходных данных
		Map<String, StatisticStructure> resStat = new LinkedHashMap<>();

		resStat.put(JavaStatistic.ALL_LINES_DIRECTORY, new StatisticStructure(
				JavaStatistic.ALL_LINES_DIRECTORY, "Общее количество строк", allLines));

		resStat.put(JavaStatistic.CODE_LINES_DIRECTORY, new StatisticStructure(
				JavaStatistic.CODE_LINES_DIRECTORY, "Строки кода", codeLines));

		resStat.put(JavaStatistic.COMMENT_LINES_DIRECTORY, new StatisticStructure(
				JavaStatistic.COMMENT_LINES_DIRECTORY, "Комментирующие строки", commentLine));

		resStat.put(COMMENT_TO_CODE_RATE_DIRECTORY, new StatisticStructure(
				COMMENT_TO_CODE_RATE_DIRECTORY, "Отношение кода к комментариям",
				commentLine == 0 ? "Комментарии отсутствуют" : (double) codeLines / commentLine));

		resStat.put(THE_BIGGEST_FILE_DIRECTORY, new StatisticStructure(THE_BIGGEST_FILE_DIRECTORY,
				"Самый большой файл", theBiggestFile.comeFromPath()));

		resStat.put(WORST_COMMENTED_FILE_DIRECTORY,
				new StatisticStructure(WORST_COMMENTED_FILE_DIRECTORY,
						"Плохо откомментированный файл", badFormatedFile.comeFromPath()));

		// отображение данных на экране статистики
		new StatisticBrowser(m_tableViewer).createControls(resStat);
	}

	@Override
	public void setAndPrintFolder(AbstractStatistic a_list)
	{
		// заполнение выходных данных
		Map<String, StatisticStructure> statisticValue = new LinkedHashMap<>(
				a_list.getFileStatistc());

		statisticValue.put(COMMENT_TO_CODE_RATE_FILE, new StatisticStructure(
				COMMENT_TO_CODE_RATE_FILE, "Отношение кода к комментариям",
				(int) statisticValue.get(JavaStatistic.COMMENT_LINES_FILE).getValue() == 0
						? "Комментарии отсутствуют"
						: Double.valueOf(
								(int) statisticValue.get(JavaStatistic.CODE_LINES_FILE).getValue())
								/ (int) statisticValue.get(JavaStatistic.COMMENT_LINES_FILE)
										.getValue()));

		// отображение данных на экране статистики
		new StatisticBrowser(m_tableViewer).createControls(statisticValue);
	}

	@Override
	public ImageDescriptor getFileImage()
	{
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"/icons/formatter-java.gif");
	}

}
