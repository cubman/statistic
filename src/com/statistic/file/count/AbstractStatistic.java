package com.statistic.file.count;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStatistic
{
	Map<String, String> statisticResult = new HashMap();
	
	File file;
	
	public AbstractStatistic(File a_file) { 
		file = a_file;
	}
	
	abstract public Map<String, String> getStatistc();
	
	abstract public void countStatistic();
	
	public String getShortFileName() {
		return file.getName();
	}
	
	public String getLongFileName()
	{
		return file.getPath();
	}
}