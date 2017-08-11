package Views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.ViewPart;

public class ExplorerView extends ViewPart
{

	public static String ID = "com.statistic.count.Explorer";
	
	Table m_table;
	
	public ExplorerView()
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
