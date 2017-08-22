package com.statistic.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.statistic.count.FileRestriction;
import com.statistic.fileformat.FileFormatManager;
import com.statistic.fileformat.IFileFormat;

public class FormatChooseDialog extends Dialog
{

	private FileRestriction		m_fileRestriction	= new FileRestriction(0, new ArrayList<>());
	private Table				m_table;
	private Spinner				m_spinner;
	private Button				m_button;
	private FileFormatManager	fileFormatManager;

	public FormatChooseDialog(Shell a_parent)
	{
		super(a_parent);
		// TODO Auto-generated constructor stub
	}

	public FileRestriction openDialog()
	{
		Shell parent = getParent();
		Shell dialog = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setSize(500, 500);
		GridLayout layout = new GridLayout(2, true);
		dialog.setLayout(layout);

		dialog.setText("Java Source and Support");
		dialog.open();

		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		m_table = new Table(dialog, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		m_table.setLayoutData(gridData);
		// m_comboDropDown.setLayoutData(gridData);

		gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);

		m_spinner = new Spinner(dialog, SWT.BORDER);
		m_spinner.setIncrement(5);
		m_spinner.setMinimum(10);
		m_spinner.setMaximum(200);

		m_spinner.setToolTipText("Минимальное значение кодовых строк");

		gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		m_button = new Button(dialog, SWT.BUTTON2);
		
		m_button.setLayoutData(gridData);
		
		
		fileFormatManager = FileFormatManager.getInstance();

		m_table.addListener(SWT.Selection, lis ->
			{
				m_fileRestriction.getSelectedFormats().clear();

				for(int i = 0; i < m_table.getItemCount(); ++i)
					if(m_table.getItem(i).getChecked())
						m_fileRestriction.getSelectedFormats()
								.add(fileFormatManager.getFileFormats().get(i));
			});

		List<IFileFormat> list = fileFormatManager.getFileFormats();

		for(int i = 0; i < list.size(); i++)
		{
			TableItem item = new TableItem(m_table, SWT.NONE);
			item.setText(list.get(i).toString());
		}

		Display display = parent.getDisplay();
		while(!dialog.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}

		return m_fileRestriction;
	}

}
