package com.statistic.file.count;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	public void countStatistic()
	{
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(file.getAbsolutePath()));

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				
				
				//System.out.println(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		
		}
		
		m_statisticForDirectory.put("все строки", 10);
		m_statisticForDirectory.put("кодовые", 10);
		m_statisticForDirectory.put("комментарии", 10);
		
		m_statisticForFile.put("Все строки", 10);
		m_statisticForFile.put("Кодовые", 30);
		m_statisticForFile.put("Комментарии", 50);
		m_statisticForFile.put("Пустые", 70);
	}

	@Override
	public ImageDescriptor getImage()
	{
		//return new Image(Display.getDefault(), getClass().getResourceAsStream("/icons/java.png"));
		return Activator.imageDescriptorFromPlugin("org.eclipse.e4.ui.workbench.swt", "/icons/full/obj16/fldr_obj.gif");
	}

	private boolean isCodeLine(String a_string)
	{
		return true;
	}
}

