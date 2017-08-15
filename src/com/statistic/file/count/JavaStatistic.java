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
		int allLines = 0, codeLines = 0, commentLines = 0, importLines = 0, emptyLines = 0;
		boolean bigComment = false;
		try
		{

			br = new BufferedReader(new FileReader(file.getAbsolutePath()));

			String sCurrentLine;

			while((sCurrentLine = br.readLine()) != null)
			{

				if(bigComment)
				{
					if(isBigCommentEnd(sCurrentLine))
						bigComment = false;

					++commentLines;
				}
				else
					if(isBigCommentStart(sCurrentLine))
					{
						bigComment = true;
						if(isBigCommentEnd(sCurrentLine))
							bigComment = false;

						++commentLines;
					}
					else
						if(isEmptyString(sCurrentLine))
							++emptyLines;
						else
							if(isImport(sCurrentLine))
								++importLines;
							else
								if(isSmallComment(sCurrentLine))
									++commentLines;
								else
									if(!isOpenBracket(sCurrentLine) && !isCloseBracket(sCurrentLine)
											&& !isPackage(sCurrentLine))
										++codeLines;

				++allLines;
				// System.out.println(sCurrentLine);
			}

		}
		catch(IOException e)
		{

			e.printStackTrace();

		}
		finally
		{

			try
			{

				if(br != null)
					br.close();

			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}

		}

		m_statisticForDirectory.add(allLines);
		m_statisticForDirectory.add(codeLines);
		m_statisticForDirectory.add(commentLines);
		//m_statisticForDirectory.add(commentLines == 0 ? "Комментарии отсутствуют" : (double)codeLines / commentLines);

		m_statisticForFile.add(allLines);
		m_statisticForFile.add(importLines);
		m_statisticForFile.add(codeLines);
		m_statisticForFile.add(commentLines);
		m_statisticForFile.add(emptyLines);
		

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

	private boolean isPackage(String a_string)
	{
		return a_string.matches("^(\\t|\\s*)package .*;$");
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
