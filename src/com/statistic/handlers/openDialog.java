package com.statistic.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import com.statistic.folders.DirecroryStructure;
import com.statistic.folders.FileFormat;

public class openDialog extends AbstractHandler implements IHandler
{

	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{
		// TODO Auto-generated method stub
		DirecroryStructure direcroryStructure = DirecroryStructure.createDirectory(new File("D:\\EclipseWorkDirectory\\git project\\statistic\\src\\com"), FileFormat.Java);
		direcroryStructure.m_parent = null;
		return null;
		
	}

}
