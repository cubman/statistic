package com.statistic.count;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class Preference extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
	public Preference()
	{
		super(GRID);

	}

	@Override
	public void init(IWorkbench a_workbench)
	{
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
	}

	@Override
	protected void createFieldEditors()
	{
		addField(new DirectoryFieldEditor("PATH", "Путь изначального поиска:",
				getFieldEditorParent()));

		addField(new SpinnerFieldEditor("SPINNER", "Количество кодовых строк, входящие в поиск",
				getFieldEditorParent()));

	}

}
