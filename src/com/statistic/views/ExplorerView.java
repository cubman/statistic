package com.statistic.views;

import java.util.Map;

import javax.lang.model.element.Element;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;

import com.statistic.file.count.AbstractStatistic;
import com.statistic.folders.DirecroryStructure;
import com.statistic.folders.FileFormat;

public class ExplorerView extends ViewPart
{

	public static String	ID	= "com.statistic.count.Explorer";

	public TreeViewer		aTreeViewer;
	public Combo			comboDropDown;
	public DiscroptionView	m_discroptionView;

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

		aTreeViewer = new TreeViewer(a_parent,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		comboDropDown = new Combo(a_parent, SWT.DROP_DOWN | SWT.BORDER);

		/*
		 * GridData gridData = new GridData(); gridData.verticalAlignment =
		 * GridData.FILL; m_table.(gridData);
		 */
		aTreeViewer.addDoubleClickListener(listener ->
			{
				IStructuredSelection thisSelection = (IStructuredSelection) listener.getSelection();
				Object selectedNode = thisSelection.getFirstElement();

				if(m_discroptionView == null)
				{
					System.out.println("Описание пусто");

					return;
				}
				if(selectedNode instanceof DirecroryStructure)
				{
					DirecroryStructure direcroryStructure = (DirecroryStructure) selectedNode;

					m_discroptionView.printDirectoryStatistic(
							DirecroryStructure.getStatisticForSelectedFolder(direcroryStructure));
					System.out.println(direcroryStructure.m_directoryName);
				}
				else
				{
					AbstractStatistic abstractStatistic = (AbstractStatistic) selectedNode;

					m_discroptionView.printFileStatistic(abstractStatistic);
					System.out.println(abstractStatistic.getLongFileName());
				}

			});

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		comboDropDown.setLayoutData(gridData);

		for(FileFormat fileFormat : FileFormat.getAllPossibleFileFormat())
			comboDropDown.add(FileFormat.toFormat(fileFormat));

		comboDropDown.select(0);
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}

}
