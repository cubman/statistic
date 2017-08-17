package com.statistic.views;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.file.viewer.IFormatViewer;

public class DiscroptionView extends ViewPart
{

	public static String	ID	= "com.statistic.count.discription";

	public TableViewer		m_tableViewer;
	public IFormatViewer	m_iTableViewer;

	public DiscroptionView()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite a_parent)
	{
		setPartName("Статистика");
		m_tableViewer = new TableViewer(a_parent,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);

		Table m_table = m_tableViewer.getTable();
		m_table.setLinesVisible(true);
		m_table.setHeaderVisible(true);

		String[] titles = { "Наименование", "Описание" };

		for(int i = 0; i < titles.length; i++)
		{
			TableColumn tableColumn = new TableColumn(m_table, SWT.LEFT);
			tableColumn.setText(titles[i]);
			tableColumn.setWidth(250);
		}
	}

	public void changeName(String a_string)
	{
		setPartName(a_string);
	}

	// отобразить статистику для директории
	public void printDirectoryStatistic(List<AbstractStatistic> a_abstractStatistics)
	{
		m_iTableViewer.setAndPrintDirectory(a_abstractStatistics);
	}

	// отобразить статистику по файлу
	public void printFileStatistic(AbstractStatistic a_statistic)
	{
		m_iTableViewer.setAndPrintFolder(a_statistic);
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}
}
