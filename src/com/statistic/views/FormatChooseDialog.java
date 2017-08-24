package com.statistic.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.statistic.count.Activator;
import com.statistic.count.FileRestriction;
import com.statistic.count.SpinnerFieldEditor;
import com.statistic.fileformat.FileFormatManager;
import com.statistic.fileformat.IFileFormat;

public class FormatChooseDialog extends Dialog
{

	private FileRestriction		m_fileRestriction;
	private Table				m_table;
	private Spinner				m_spinner;
	private FileFormatManager	fileFormatManager	= FileFormatManager.getInstance();

	public FormatChooseDialog(Shell a_parent)
	{
		super(a_parent);
		// TODO Auto-generated constructor stub
	}

	public FileRestriction openDialog()
	{
		Shell shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		shell.setSize(500, 190);
		Display display = getParent().getDisplay();
		Point point = display.getActiveShell().getLocation();
		Point mainWindow = getParent().getSize();
		Point paramWindow = shell.getSize();

		shell.setLocation(point.x + (mainWindow.x - point.x) / 2 - paramWindow.x / 2,
				point.y + (mainWindow.y - point.y) / 2 - paramWindow.y / 2);
		
		shell.setText("Выберите параметры поиска");
		createContents(shell);
		shell.open();

		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}

		return m_fileRestriction;
	}

	private void createContents(final Shell shell)
	{
		GridLayout gridLayout = new GridLayout(2, false);

		FillLayout fillLayout = new FillLayout();
		shell.setLayout(fillLayout);
		
		
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(gridLayout);
		
		//* label up
		Label mainMessage = new Label(composite, SWT.NONE);
		mainMessage.setText("Выберите параметры поиска:");
		GridData data = new GridData();
		data.horizontalSpan = 2;
		mainMessage.setLayoutData(data);
		
		//* table left
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		m_table = new Table(composite, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		data.heightHint = 10;
		data.horizontalSpan = 1;
		m_table.setLayoutData(data);

		//* right colomn
		Composite composite2 = new Composite(composite, SWT.NONE);
		composite2.setLayout(new GridLayout(1, false));
		data = new GridData(SWT.FILL, SWT.FILL, false, true);
		data.widthHint = 100;
		composite2.setLayoutData(data);
		
		// spinner
		data = new GridData(SWT.FILL, SWT.LEFT, true, true);
		m_spinner = new Spinner(composite2, SWT.LEFT);
		m_spinner.setIncrement(SpinnerFieldEditor.INCREMENT_VALUE);
		m_spinner.setMinimum(SpinnerFieldEditor.MIN_VALUE);
		m_spinner.setMaximum(SpinnerFieldEditor.MAX_VALUE);
		m_spinner.setToolTipText("Минимальное значение кодовых строк");
		m_spinner.setSelection(Activator.getDefault().getPreferenceStore().getInt("SPINNER"));
		m_spinner.setLayoutData(data);

		
		// label of amount of checked elements
		data = new GridData(SWT.FILL, SWT.FILL, true, false);	
		
		Label chosenFile = new Label(composite2, SWT.FILL);
		chosenFile.setVisible(false);

		Font initialFont = chosenFile.getFont();
		FontData[] fontData = initialFont.getFontData();
		for(int i = 0; i < fontData.length; i++)
		{
			fontData[i].setHeight(8);
			fontData[i].setStyle(SWT.ITALIC);
		}

		Font newFont = new Font(shell.getDisplay(), fontData);
		chosenFile.setFont(newFont);
		chosenFile.setLayoutData(data);

		m_table.addListener(SWT.Selection, lis ->
			{
				int cnt = 0;

				for(int i = 0; i < m_table.getItemCount(); ++i)
					if(m_table.getItem(i).getChecked())
						++cnt;

				chosenFile.setVisible(cnt > 0);
				chosenFile.setText(String.format("* Вы выбрали: %d", cnt));
			});

		List<IFileFormat> list = fileFormatManager.getFileFormats();

		for(int i = 0; i < list.size(); i++)
		{
			TableItem item = new TableItem(m_table, SWT.NONE);
			item.setText(list.get(i).toString());
		}

		
		Composite composite3 = new Composite(composite, SWT.None);
		composite3.setLayout(new GridLayout(2, false));
		data = new GridData(SWT.RIGHT, SWT.FILL, true, false);
		data.widthHint = 240;
		data.horizontalSpan = 2;
		composite3.setLayoutData(data);
		
		Button ok = new Button(composite3, SWT.PUSH | SWT.CENTER);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addListener(SWT.Selection, event ->
			{
				m_fileRestriction = new FileRestriction(Integer.parseInt(m_spinner.getText()),
						getSelectedFileFormats());
				if(m_fileRestriction.getSelectedFormats().isEmpty())
					MessageDialog.openError(shell, "Выберите формат",
							"Не было выбрано ниодного формата");
				else
					shell.close();
			});

		Button cancel = new Button(composite3, SWT.PUSH | SWT.CENTER);
		cancel.setText("Cancel");
		data = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(data);
		cancel.addListener(SWT.Selection, event ->
			{
				m_fileRestriction = null;
				shell.close();
			});

		shell.setDefaultButton(ok);
	}

	private List<IFileFormat> getSelectedFileFormats()
	{
		List<IFileFormat> res = new ArrayList<>();

		for(int i = 0; i < m_table.getItemCount(); ++i)
			if(m_table.getItem(i).getChecked())
				res.add(fileFormatManager.getFileFormats().get(i));

		return res;
	}
	
	
}
