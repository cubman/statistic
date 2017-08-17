package com.statistic.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;



public class StatisticBrowser
{
	TableViewer m_tableViewer;

	public StatisticBrowser(TableViewer a_tableViewer)
	{
		m_tableViewer = a_tableViewer;
	}

	// создание начального состояний структуры отображения дерева
	@PostConstruct
	public void createControls(Map<String, StatisticStructure> a_statisct)
	{
		m_tableViewer.refresh();
		m_tableViewer.setContentProvider(new TableViewProvider());
		m_tableViewer.setLabelProvider(new StatisticView());
		m_tableViewer.setInput(a_statisct);
	}

	class TableViewProvider implements IStructuredContentProvider
	{
		public Object[] getElements(Object inputElement)
		{
			List<StatisticStructure> result = new ArrayList<>();

			for(Map.Entry<String, StatisticStructure> enter : ((Map<String, StatisticStructure>) inputElement)
					.entrySet())
				result.add(enter.getValue());

			return result.toArray();
		}
	}

	public class StatisticView implements ITableLabelProvider
	{

		@Override
		public void addListener(ILabelProviderListener a_listener)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose()
		{
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isLabelProperty(Object a_element, String a_property)
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener a_listener)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public Image getColumnImage(Object a_element, int a_columnIndex)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getColumnText(Object a_element, int a_columnIndex)
		{
			StatisticStructure ss = (StatisticStructure) a_element;
			switch(a_columnIndex)
			{
			case 0:
				return ss.m_discription;
			case 1:
				return ss.m_value.toString();
			default:
				return null;
			}

		}

	}
}
