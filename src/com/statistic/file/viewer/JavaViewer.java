package com.statistic.file.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.w3c.dom.ranges.RangeException;

import com.statistic.count.Activator;
import com.statistic.file.count.AbstractStatistic;

public class JavaViewer implements IFormatViewer
{

	@Override
	public void setAndPrintDirectory(List<AbstractStatistic> a_list, Table a_table)
	{
		// очистить от старых значений
		a_table.removeAll();

		AbstractStatistic badFormatedFile = null;
		AbstractStatistic theBiggestFile = null;

		int count = 0, bigFile = -1;
		double badCommented = Double.MAX_VALUE;
		int allLines = 0, codeLines = 0, commentLine = 0;

		String[] statisticKeys = new String[] { "Общее количество строк", // 0
				"Строки кода", // 1
				"Комментирующие строки", // 2
				"Отношение кода к комментариям" };

		for(AbstractStatistic abstractStatistic : a_list)
		{
			List<Object> statisticValue = abstractStatistic.getDirectoryStatistc();

			int allLinesLoc = (int) statisticValue.get(0),
					codeLinesLoc = (int) statisticValue.get(1),
					commentLinesLoc = (int) statisticValue.get(2);

			allLines += allLinesLoc;
			codeLines += codeLinesLoc;
			commentLine += commentLinesLoc;

			// самый большой файл
			if(bigFile < allLines)
			{
				bigFile = allLines;
				theBiggestFile = abstractStatistic;
			}

			// самый плохооткомментированный код
			if(commentLinesLoc == 0 || badCommented > (double) allLinesLoc / commentLinesLoc)
				badCommented = commentLinesLoc == 0 ? 0 : (double) allLinesLoc / commentLinesLoc;
			badFormatedFile = abstractStatistic;
		}

		List<Object> readyStatistic = new ArrayList();
		readyStatistic.add(allLines);
		readyStatistic.add(codeLines);
		readyStatistic.add(commentLine);
		readyStatistic.add(
				commentLine == 0 ? "Комментарии отсутствуют" : (double) codeLines / commentLine);

		try
		{

			if(statisticKeys.length != readyStatistic.size())
				throw new Exception("statistc is not equal amount of fields");

			for(Object abstractStatistic : readyStatistic)
				addElemtntToTable(a_table, statisticKeys[count],
						readyStatistic.get(count).toString(), ++count);

			addElemtntToTable(a_table, "Плохооткомментированный файл",
					badFormatedFile.getShortFileName(), ++count);
			addElemtntToTable(a_table, "Самый большой файл", theBiggestFile.getShortFileName(),
					++count);

			for(int i = 0; i < a_table.getColumnOrder().length; ++i)
				a_table.getColumn(i).pack();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void setAndPrintFolder(AbstractStatistic a_list, Table a_table)
	{
		a_table.removeAll();
		int count = 0;

		String[] statisticKeys = new String[] { "Общее количество строк", // 0
				"Подключенные библиотеки", // 1
				"Строки кода", // 2
				"Комментирующие строки", // 3
				"Пустые строки", // 4
				"Отношение кода к комментариям" };

		List<Object> statisticValue = new ArrayList<Object>(a_list.getFileStatistc());
		statisticValue.add((int) statisticValue.get(3) == 0 ? "Комментарии отсутствуют"
				: Double.valueOf((int) statisticValue.get(2)) / (int) statisticValue.get(3));
		try
		{

			if(statisticKeys.length != statisticValue.size())
				throw new Exception("statistc is not equal amount of fields");

			for(Object object : statisticValue)
				addElemtntToTable(a_table, statisticKeys[count], object.toString(), ++count);

			for(int i = 0; i < a_table.getColumnOrder().length; ++i)
				a_table.getColumn(i).pack();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	private void addElemtntToTable(Table a_table, String a_key, String a_value, int number)
	{
		TableItem item = new TableItem(a_table, SWT.NONE);
		item.setText(0, String.valueOf(number));
		item.setText(1, a_key);
		item.setText(2, a_value);
	}

	@Override
	public ImageDescriptor getFileImage()
	{
		return Activator.imageDescriptorFromPlugin("org.eclipse.e4.ui.workbench.swt",
				"/icons/full/obj16/fldr_obj.gif");
	}

}
