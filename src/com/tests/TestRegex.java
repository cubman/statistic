package com.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.statistic.file.count.JavaStatistic;

public class TestRegex
{

	@Test
	public void test()
	{
		JavaStatistic javaStatistic = new JavaStatistic(new File(
				"D:\\EclipseWorkDirectory\\git project\\statistic\\src\\com\\tests\\AbstractStatistic.java"));
		String s = "humbapumpa jim";
		assertTrue(s.matches(".*(jim|joe).*"));

		s = "{";
		assertTrue(s.matches("^[{]\\z$"));
		BufferedReader br = null;

		try
		{

			br = new BufferedReader(new FileReader(
					"D:\\EclipseWorkDirectory\\git project\\statistic\\src\\com\\tests\\AbstractStatistic.java"));

			String sCurrentLine;
			int cnt = 0;
			while((sCurrentLine = br.readLine()) != null)
			{
				if(sCurrentLine.matches(".*\\*/.*"))
					++cnt;

			}
			assertEquals(8, cnt);

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
		

	}

}
