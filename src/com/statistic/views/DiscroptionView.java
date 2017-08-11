package com.statistic.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.ViewPart;

public class DiscroptionView extends ViewPart
{

	public static String ID = "com.statistic.count.discription";

	Table m_table;
	
	public DiscroptionView()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite a_parent)
	{
		m_table = new Table(a_parent, SWT.H_SCROLL | SWT.V_SCROLL);

	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}

}
