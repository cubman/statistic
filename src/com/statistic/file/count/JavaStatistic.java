package com.statistic.file.count;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.statistic.count.Activator;


public class JavaStatistic extends AbstractStatistic
{

	public JavaStatistic(File a_file)
	{
		super(a_file);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, String> getStatistc()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void countStatistic()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ImageDescriptor getImage()
	{
		//return new Image(Display.getDefault(), getClass().getResourceAsStream("/icons/java.png"));
		return Activator.imageDescriptorFromPlugin("org.eclipse.e4.ui.workbench.swt", "/icons/full/obj16/fldr_obj.gif");
	}

	
}

