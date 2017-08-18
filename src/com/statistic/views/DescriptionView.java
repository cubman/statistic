package com.statistic.views;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;
import com.statistic.table.StatisticStructure;

public class DescriptionView extends ViewPart
{

	public static String	ID	= "com.statistic.count.description";

	private TableViewer		m_tableViewer;
	private IFileFormat	m_fileFormat;

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
	
	public void setFormatViewer(IFileFormat a_fileFormat)
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
		Map<String, StatisticStructure> dMap = m_fileFormat.getFormatViewer().getCountedDirectoryStatistic(a_abstractStatistics, a_minCodeLines);
		m_tableViewer.setInput(dMap);
	}

	// отобразить статистику по файлу
	public void printFileStatistic(AbstractStatistic a_statistic)
	{
		m_tableViewer.setInput(m_fileFormat.getFormatViewer().getCountedFileStatistic(a_statistic));
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}
}
