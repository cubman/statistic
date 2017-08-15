package com.statistic.views;

import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class DiscroptionView extends ViewPart
{

	public static String ID = "com.statistic.count.discription";

	TableViewer m_table;
	
	public DiscroptionView()
	{
		// TODO Auto-generated constructor stub
	}

	 private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
	        final TableViewerColumn viewerColumn = new TableViewerColumn(m_table, SWT.NONE);
	        final TableColumn column = viewerColumn.getColumn();
	        column.setText(title);
	        column.setWidth(bound);
	        column.setResizable(true);
	        column.setMoveable(true);
	        return viewerColumn;
	    }
	 
	@Override
	public void createPartControl(Composite a_parent)
	{
		m_table = new TableViewer(a_parent, SWT.H_SCROLL | SWT.V_SCROLL);

		
	   

	}

	public void printStatistic(Map<String, Integer> a_statistic)
	{
		Table table = m_table.getTable();
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		table.removeAll();
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		
		String[] titles = {" ", "Наименование", "Описание"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
		}
		
		int count = 0;
		for (Map.Entry<String, Integer> statistic : a_statistic.entrySet()) {
			System.out.println(statistic.getKey() + "   " + statistic.getValue());
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText (0, String.valueOf(++count));
			item.setText (1, statistic.getKey());
			item.setText (2, String.valueOf(statistic.getValue()));
		}
		
		for (int i=0; i<titles.length; ++i) 
			table.getColumn (i).pack ();
		
	
		
		
		
	}
	
	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}

}
