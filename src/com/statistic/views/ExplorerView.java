package com.statistic.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.statistic.folders.FileFormat;

public class ExplorerView extends ViewPart
{

	public static String ID = "com.statistic.count.Explorer";
	
	public TreeViewer m_table;
	public Combo comboDropDown;
	
	public ExplorerView()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite a_parent)
	{	
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		a_parent.setLayout(fillLayout);
		
		m_table = new TreeViewer(a_parent, SWT.H_SCROLL | SWT.V_SCROLL);
		comboDropDown = new Combo(a_parent, SWT.DROP_DOWN | SWT.BORDER);
		
		/*GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		m_table.(gridData);*/
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		comboDropDown.setLayoutData(gridData);
		
		for (FileFormat fileFormat : FileFormat.getAllPossibleFileFormat())
			comboDropDown.add(FileFormat.toFormat(fileFormat));
		
		comboDropDown.select(0);
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}

}
