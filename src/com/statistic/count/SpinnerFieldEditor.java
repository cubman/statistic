package com.statistic.count;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class SpinnerFieldEditor extends FieldEditor
{
	private Spinner spinner;
	
	public static final int INCREMENT_VALUE = 5;
	public static final int MIN_VALUE = 10;
	public static final int MAX_VALUE = 200;
	public static final int START_VALUE = 10;
	
	public SpinnerFieldEditor(String name, String labelText, Composite parent)
	{
		init(name, labelText);
		//errorMessage = JFaceResources.getString("IntegerFieldEditor.errorMessage");//$NON-NLS-1$
		//widthInChars = width;
		createControl(parent);
	}
	
	@Override
	protected void adjustForNumColumns(int a_numColumns)
	{
		GridData gd = (GridData) spinner.getLayoutData();
		gd.horizontalSpan = a_numColumns - 1;
		gd.grabExcessHorizontalSpace = false;
	}

	@Override
	protected void doFillIntoGrid(Composite a_parent, int a_numColumns)
	{
		getLabelControl(a_parent);

		spinner = getSpinnerControl(a_parent);
		GridData gd = new GridData();
		gd.horizontalSpan = a_numColumns - 1;
		
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
	
		spinner.setLayoutData(gd);
	}

    protected Spinner getSpinnerControl() {
        return spinner;
    }

	/**
	 * Returns this field editor's text control.
	 * <p>
	 * The control is created if it does not yet exist
	 * </p>
	 * 
	 * @param parent
	 *            the parent
	 * @return the text control
	 */
	public Spinner getSpinnerControl(Composite parent) {
		if (spinner == null) {
			spinner = new Spinner(parent, SWT.SINGLE | SWT.BORDER);
			spinner.setFont(parent.getFont());
			spinner.setIncrement(INCREMENT_VALUE);
			spinner.setMinimum(MIN_VALUE);
			spinner.setMaximum(MAX_VALUE);
			spinner.setSelection(START_VALUE);
		} else {
			checkParent(spinner, parent);
		}
		return spinner;
	}
	
	@Override
	protected void doLoad()
	{
		if (spinner != null) {
            int value = getPreferenceStore().getInt(getPreferenceName());
            spinner.setSelection(value);
		}

	}

	@Override
	protected void doLoadDefault()
	{
		if (spinner != null) {
            int value = getPreferenceStore().getDefaultInt(getPreferenceName());
            spinner.setSelection(value);
        }
	}

	@Override
	protected void doStore()
	{
		getPreferenceStore().setValue(getPreferenceName(), spinner.getSelection());

	}

	@Override
	public int getNumberOfControls()
	{
		// TODO Auto-generated method stub
		return 1;
	}

}
