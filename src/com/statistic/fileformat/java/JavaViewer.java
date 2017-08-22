package com.statistic.fileformat.java;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;

import com.statistic.count.Activator;
import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFormatViewer;
import com.statistic.table.StatisticStructure;

public class JavaViewer implements IFormatViewer
{

	public static final String	COMMENT_TO_CODE_RATE_FILE		= "java.commentToCodeFile";

	public static final String	COMMENT_TO_CODE_RATE_DIRECTORY	= "java.commentToCodeDirectory";
	public static final String	THE_BIGGEST_FILE_DIRECTORY		= "java.theBigestFileDirectory";
	public static final String	WORST_COMMENTED_FILE_DIRECTORY	= "java.worstCommentedFileDirectory";
	public static final String	BEST_COMMENTED_FILE_DIRECTORY	= "java.bestCommentedFileDirectory";

	@Override
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
			if((commentLinesLoc == 0 || badCommented > (double) commentLinesLoc / allLinesLoc) && codeLinesLoc > a_minCodeLines)
			{
				badCommented = commentLinesLoc == 0 ? 0 : (double) commentLinesLoc / allLinesLoc;
				badFormatedFile = abstractStatistic;
			}

			// лучшеоткомментированный код
			if(commentLinesLoc != 0 && bestCommented < (double) commentLinesLoc / allLinesLoc && codeLinesLoc > a_minCodeLines)
			{
				bestCommented = (double) commentLinesLoc / allLinesLoc;
				bestFormatedFile = abstractStatistic;
			}

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

	@Override
	public Map<String, StatisticStructure> getCountedFileStatistic(AbstractStatistic a_list)
	{
		// заполнение выходных данных
		Map<String, StatisticStructure> statisticValue = new LinkedHashMap<>(
				a_list.getFileStatistc());

		statisticValue.put(COMMENT_TO_CODE_RATE_FILE, new StatisticStructure(
				COMMENT_TO_CODE_RATE_FILE, "Отношение кода к комментариям",
				(int) statisticValue.get(JavaStatistic.COMMENT_LINES_FILE).getValue() == 0
						? "Комментарии отсутствуют"
						: String.format("%.2f %%",
								Double.valueOf((int) statisticValue
										.get(JavaStatistic.COMMENT_LINES_FILE).getValue())
										/ (int) statisticValue.get(JavaStatistic.CODE_LINES_FILE)
												.getValue() * 100)));

		// отображение данных на экране статистики
		return statisticValue;
	}

	@Override
	public ImageDescriptor getFileImage()
	{
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"/icons/formatter-java.gif");
	}

}
