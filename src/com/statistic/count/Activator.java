package com.statistic.count;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.statistic.fileformat.FileFormatManager;
import com.statistic.fileformat.java.JavaFormat;
import com.statistic.fileformat.xml.XmlFormat;

public class Activator extends AbstractUIPlugin
{
	public static Activator		INSTANCE;

	public final static String	PLUGIN_ID	= "com.statistic.count";

	public Activator()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(BundleContext a_context) throws Exception
	{
		super.start(a_context);
		INSTANCE = this;
		System.out.println("Activator.start()");
		
		FileFormatManager fileFormatManager = FileFormatManager.getInstance();
		// формирование статистики менедера
		fileFormatManager.addFormat(new JavaFormat());
		fileFormatManager.addFormat(new XmlFormat());
	}

	@Override
	public void stop(BundleContext a_context) throws Exception
	{
		INSTANCE = null;
		super.stop(a_context);
	}

	public static Activator getDefault()
	{
		return INSTANCE;
	}
}
