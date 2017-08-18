package com.statistic.table;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.TableViewer;

import com.statistic.views.TableContentProvider;
import com.statistic.views.TableLabelProvider;

public class StatisticBrowser
{
	// таблица результатов
	TableViewer m_tableViewer;

	public StatisticBrowser(TableViewer a_tableViewer)
	{
		m_tableViewer = a_tableViewer;
	}

	// создание начального состояний структуры отображения дерева
	@PostConstruct
	public void createControls(Map<String, StatisticStructure> a_statisct)
	{
		// обновить данные
		
	}

}
