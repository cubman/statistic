package com.statistic.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
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
	private ResourceManager m_resourceManager;
	
	@Override
	public void createPartControl(Composite a_parent)
	{
		Tree addressTree = new Tree(a_parent,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		addressTree.setHeaderVisible(true);
		m_treeViewer = new TreeViewer(addressTree);

		// устанавливаем свойства колонок таблицы
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
		
		m_resourceManager = new LocalResourceManager(JFaceResources.getResources());
	}

	@Override
	public void setFocus()
	{
	}

	// очистить таблицу
	public void clearTable()
	{
		m_treeViewer.setInput(null);
	}

	// наименование заголовка вкладки
	public void changeTitle(String a_string, String a_toolTipPath, ImageDescriptor a_descriptor)
	{
		setPartName(a_string);
		setTitleToolTip(a_toolTipPath);
		setTitleImage( m_resourceManager.createImage(a_descriptor));
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
		m_treeViewer.expandAll();
	}

	// отобразить статистику по файлу
	public void printFileStatistic(AbstractStatistic a_statistic)
	{
		m_treeViewer.setInput(new TreeOutFormat(a_statistic.getFileFormat(),
				a_statistic.getCountedFileStatistic()));
	}

	// вернуть сгруппированные коллекции элементов одного формата
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
}
