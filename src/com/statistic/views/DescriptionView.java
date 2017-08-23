package com.statistic.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.ViewPart;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.IFileFormat;
import com.statistic.fileformat.TreeOutFormat;

public class DescriptionView extends ViewPart
{

	public static String	ID	= "com.statistic.count.description";
	private TreeViewer		m_treeViewer;

	@Override
	public void createPartControl(Composite a_parent)
	{
		Tree addressTree = new Tree(a_parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		addressTree.setHeaderVisible(true);
		m_treeViewer = new TreeViewer(addressTree);

		TreeColumn column1 = new TreeColumn(addressTree, SWT.LEFT);
		addressTree.setLinesVisible(true);
		column1.setAlignment(SWT.LEFT);
		column1.setText("Наименование");
		column1.setWidth(260);
		
		TreeColumn column2 = new TreeColumn(addressTree, SWT.RIGHT);
		column2.setAlignment(SWT.LEFT);
		column2.setText("Значение");
		column2.setWidth(400);

		m_treeViewer.setContentProvider(new TableContentProvider());
		m_treeViewer.setLabelProvider(new TableLabelProvider());
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}

	

	

	public void changeName(String a_string, String a_toolTipPath)
	{
		setPartName(a_string);
		setTitleToolTip(a_toolTipPath);
	}

	// отобразить статистику для директории
	public void printDirectoryStatistic(List<AbstractStatistic> a_abstractStatistics,
			int a_minCodeLines)
	{

		List<TreeOutFormat> treeOutFormats = new ArrayList<>();

		for(Map.Entry<IFileFormat, List<AbstractStatistic>> abstractStatistics : getCombinedStatistic(
				a_abstractStatistics).entrySet())
			if(abstractStatistics.getValue().size() > 0)
				treeOutFormats.add(new TreeOutFormat(abstractStatistics.getKey(),
						abstractStatistics.getValue().get(0).getCountedDirectoryStatistic(
								abstractStatistics.getValue(), a_minCodeLines)));

		m_treeViewer.setInput(treeOutFormats);
	}

	// отобразить статистику по файлу
	public void printFileStatistic(AbstractStatistic a_statistic)
	{
		m_treeViewer.setInput(
				new TreeOutFormat(a_statistic.getFileFormat(), a_statistic.getCountedFileStatistic()));
	}

	private Map<IFileFormat, List<AbstractStatistic>> getCombinedStatistic(
			List<AbstractStatistic> a_abstractStatistics)
	{
		Map<IFileFormat, List<AbstractStatistic>> resGroup = new LinkedHashMap<>();

		for(AbstractStatistic abstractStatistic : a_abstractStatistics)
		{
			IFileFormat fileFormat = abstractStatistic.getFileFormat();

			if(resGroup.containsKey(fileFormat))
				resGroup.get(fileFormat).add(abstractStatistic);
			else
			{
				List<AbstractStatistic> add = new ArrayList<>();
				add.add(abstractStatistic);
				resGroup.put(fileFormat, add);
			}
		}
		return resGroup;
	}

	/*
	 * private TableViewer m_tableViewer;
	 * 
	 * public DescriptionView() { // TODO Auto-generated constructor stub }
	 * 
	 * @Override public void createPartControl(Composite a_parent) {
	 * setPartName("Статистика"); m_tableViewer = new TableViewer(a_parent,
	 * SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
	 * 
	 * 
	 * m_tableViewer.setContentProvider(new TableContentProvider());
	 * m_tableViewer.setLabelProvider(new TableLabelProvider());
	 * 
	 * 
	 * 
	 * Table m_table = m_tableViewer.getTable(); m_table.setLinesVisible(true);
	 * m_table.setHeaderVisible(true);
	 * 
	 * String[] titles = { "Наименование", "Описание" };
	 * 
	 * for(int i = 0; i < titles.length; i++) { TableColumn tableColumn = new
	 * TableColumn(m_table, SWT.LEFT); tableColumn.setText(titles[i]);
	 * tableColumn.setWidth(250); } }
	 * 
	 * // получить TableViewer public TableViewer getTableViewer() { return
	 * m_tableViewer; }
	 * 
	 * public void changeName(String a_string, String a_toolTipPath) {
	 * setPartName(a_string); setTitleToolTip(a_toolTipPath); }
	 * 
	 * // отобразить статистику для директории public void
	 * printDirectoryStatistic(List<AbstractStatistic> a_abstractStatistics, int
	 * a_minCodeLines) { Map<String, StatisticStructure> resMap = new
	 * LinkedHashMap<>();
	 * 
	 * 
	 * 
	 * for (Map.Entry<IFileFormat, List<AbstractStatistic>> abstractStatistics :
	 * getCombinedStatistic(a_abstractStatistics).entrySet()) if
	 * (abstractStatistics.getValue().size() > 0)
	 * resMap.putAll(abstractStatistics.getValue().get(0).
	 * getCountedDirectoryStatistic(abstractStatistics.getValue(),
	 * a_minCodeLines));
	 * 
	 * m_tableViewer.setInput(resMap); }
	 * 
	 * // отобразить статистику по файлу public void
	 * printFileStatistic(AbstractStatistic a_statistic) {
	 * m_tableViewer.setInput(a_statistic.getFileStatistc()); }
	 * 
	 * @Override public void setFocus() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * private Map<IFileFormat, List<AbstractStatistic>>
	 * getCombinedStatistic(List<AbstractStatistic> a_abstractStatistics) {
	 * Map<IFileFormat, List<AbstractStatistic>> resGroup = new
	 * LinkedHashMap<>();
	 * 
	 * for (AbstractStatistic abstractStatistic : a_abstractStatistics) {
	 * IFileFormat fileFormat = abstractStatistic.getFileFormat();
	 * 
	 * if (resGroup.containsKey(fileFormat))
	 * resGroup.get(fileFormat).add(abstractStatistic); else {
	 * List<AbstractStatistic> add = new ArrayList<>();
	 * add.add(abstractStatistic); resGroup.put(fileFormat, add); } } return
	 * resGroup; }
	 */
}
