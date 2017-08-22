package com.statistic.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;
import com.statistic.table.StatisticStructure;

public class DescriptionView extends ViewPart
{

	public static String	ID	= "com.statistic.count.description";

	private TableViewer		m_tableViewer;
	private List<IFileFormat>	m_fileFormat;

	public DescriptionView()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite a_parent)
	{
		setPartName("Статистика");
		m_tableViewer = new TableViewer(a_parent,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		

		m_tableViewer.setContentProvider(new TableContentProvider());
		m_tableViewer.setLabelProvider(new TableLabelProvider());

		
		
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

	// получить TableViewer
	public TableViewer getTableViewer()
	{
		return m_tableViewer;
	}
	
	public void setFormatViewer(List<IFileFormat> a_fileFormat)
	{
		m_fileFormat = a_fileFormat;
	}
	
	public void changeName(String a_string, String a_toolTipPath)
	{
		setPartName(a_string);
		setTitleToolTip(a_toolTipPath);
	}

	// отобразить статистику для директории
	public void printDirectoryStatistic(List<AbstractStatistic> a_abstractStatistics, int a_minCodeLines)
	{
		Map<String, StatisticStructure> resMap = new LinkedHashMap<>();
		
		
		
		for (Map.Entry<IFileFormat, List<AbstractStatistic>> abstractStatistics : getCombinedStatistic(a_abstractStatistics).entrySet())
			if (abstractStatistics.getValue().size() > 0)
				resMap.putAll(abstractStatistics.getValue().get(0).getCountedDirectoryStatistic(abstractStatistics.getValue(), a_minCodeLines));
		
		m_tableViewer.setInput(resMap);
	}

	// отобразить статистику по файлу
	public void printFileStatistic(AbstractStatistic a_statistic)
	{
		m_tableViewer.setInput(a_statistic.getFileStatistc());
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}
	
	private Map<IFileFormat, List<AbstractStatistic>> getCombinedStatistic(List<AbstractStatistic> a_abstractStatistics)
	{
		Map<IFileFormat, List<AbstractStatistic>> resGroup = new LinkedHashMap<>();
		
		for (AbstractStatistic abstractStatistic : a_abstractStatistics) {
			IFileFormat fileFormat = abstractStatistic.getFileFormat();
			
			if (resGroup.containsKey(fileFormat))
					resGroup.get(fileFormat).add(abstractStatistic);
				else {
					List<AbstractStatistic> add = new ArrayList<>();
					add.add(abstractStatistic);
					resGroup.put(fileFormat, add);
				}
		}
		return resGroup;
	}
}
