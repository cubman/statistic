package com.statistic.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.statistic.folders.DirecroryStructure;
import com.statistic.folders.FileBrowser;
import com.statistic.folders.FileFormat;
import com.statistic.views.ExplorerView;

public class openDialog extends AbstractHandler implements IHandler
{

	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{
		// TODO Auto-generated method stub
		DirecroryStructure direcroryStructure = DirecroryStructure.createDirectory(new File("D:\\EclipseWorkDirectory\\git project\\statistic\\src\\com"), FileFormat.Java);
		// direcroryStructure.m_parent = null;
		
		try
		{
			ExplorerView explorerView = (ExplorerView)HandlerUtil.getActiveWorkbenchWindow(a_event).getActivePage().showView(ExplorerView.ID);
			FileBrowser fileBrowser = new FileBrowser(explorerView.m_table);
			fileBrowser.setPrivider(direcroryStructure);
			fileBrowser.createControls(null);
		}
		catch(PartInitException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}