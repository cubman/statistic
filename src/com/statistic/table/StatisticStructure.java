package com.statistic.table;

public class StatisticStructure
{
	String	m_ID; // id статистики
	String	m_discription; // словесное описание статистики
	Object	m_value; // значение описанной статистики

	public StatisticStructure(String a_id, String a_discription, Object a_value)
	{
		m_ID = a_id;
		m_discription = a_discription;
		m_value = a_value;
	}

	// получить id
	public String getID()
	{
		return m_ID;
	}

	// получить словесное описание
	public String getDiscription()
	{
		return m_discription;
	}

	// получить значение
	public Object getValue()
	{
		return m_value;
	}
}
