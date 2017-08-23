package com.statistic.count;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.statistic.fileformat.FileFormatManager;
import com.statistic.fileformat.IFileFormat;
import com.statistic.fileformat.java.JavaFormat;

public class Activator extends AbstractUIPlugin
{
	public static Activator		INSTANCE;

	public final static String	PLUGIN_ID			= "com.statistic.count";

	public final static String	EXTENSION_POINT_ID	= "com.statistic.counter";

	@Override
	public void start(BundleContext a_context) throws Exception
	{
		super.start(a_context);
		INSTANCE = this;
		System.out.println("Activator.start()");
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);

		FileFormatManager fileFormatManager = FileFormatManager.getInstance();
		try
		{
			for(IConfigurationElement e : config)
			{
				System.out.println("Evaluating extension");
				final Object o = e.createExecutableExtension("class");
				if(o instanceof IFileFormat)
				{
					fileFormatManager.addFormat((IFileFormat) o);
				}
			}

			setJavaFirst();

		}
		catch(CoreException ex)
		{
			System.out.println(ex.getMessage());
		}
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

	// установить язык Java первым в списке всех языков
	private void setJavaFirst()
	{
		FileFormatManager fileFormatManager = FileFormatManager.getInstance();
		List<IFileFormat> lFileFormats = fileFormatManager.getFileFormats();

		for(int i = 0; i < lFileFormats.size(); ++i)
			if(lFileFormats.get(i) instanceof JavaFormat)
			{
				lFileFormats.set(i, lFileFormats.get(0));
				lFileFormats.set(0, new JavaFormat());
				break;
			}
	}
}
