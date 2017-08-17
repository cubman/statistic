package com.statistic.table;

public class StatisticStructure
{
	String m_ID;
	String m_discription;
	Object m_value;
	
	public StatisticStructure(String a_id, String a_discription, Object a_value)
	{
		m_ID = a_id;
		m_discription = a_discription;
		m_value = a_value;
	}
	
	public String getID()
	{
		return m_ID;
	}
	
	public String getDiscription()
	{
		return m_discription;
	}
	
	public Object getValue()
	{
		return m_value;
	}
}

