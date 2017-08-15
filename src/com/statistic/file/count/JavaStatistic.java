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
		double allLines = 0, codeLines = 0, commentLines = 0, importLines = 0, emptyLines = 0;
		boolean bigComment = false;
		try {

			br = new BufferedReader(new FileReader(file.getAbsolutePath()));

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				
				if (bigComment) {
					if (isBigCommentEnd(sCurrentLine))			
						bigComment = false;
					
					++commentLines;
				}
				else 
					if (isBigCommentStart(sCurrentLine))
					{
						bigComment = true;
						if (isBigCommentEnd(sCurrentLine))
							bigComment = false;
					} 
					else
						if (isEmptyString(sCurrentLine))
							++emptyLines;
						else
							if (isImport(sCurrentLine))
								++importLines;
							else 
								if (isSmallComment(sCurrentLine))
									++commentLines;
								else
									++codeLines;
					
				++allLines;
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
		
		m_statisticForDirectory.put("все строки", allLines);
		m_statisticForDirectory.put("кодовые", codeLines);
		m_statisticForDirectory.put("комментарии", commentLines);
		m_statisticForDirectory.put("код/коментарий", codeLines / commentLines);
		
		m_statisticForFile.put("Все строки", allLines);
		m_statisticForFile.put("Зависимости", importLines);
		m_statisticForFile.put("Кодовые", codeLines);
		m_statisticForFile.put("Комментарии", codeLines);
		m_statisticForFile.put("Пустые", emptyLines);
		
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
	
	private boolean isOpenBracket(String a_string)
	{
		return a_string.matches("^\\s*\\{\\s*$");
	}
	
	private boolean isCloseBracket(String a_string)
	{
		return a_string.matches("^\\s*\\}\\s*$");
	}
	
	private boolean isMethod(String a_string)
	{
		return a_string.matches("^(\\t|\\s*)(private|public|protected).*\\)\\s*$"); 
	}
	
	private boolean isImport(String a_string)
	{
		return a_string.matches("^(\\t|\\s*)import .*;$"); 
	}
	
	private boolean isEmptyString(String a_string)
	{
		return a_string.matches("^\\s*$"); 
	}
	
	private boolean isSmallComment(String a_string)
	{
		return a_string.matches(".*//.*"); 
	}
	
	private boolean isBigCommentStart(String a_string)
	{
		return a_string.matches(".*/\\*.*"); 
	}
	
	private boolean isBigCommentEnd(String a_string)
	{
		return a_string.matches(".*\\*/.*"); 
	}
}

