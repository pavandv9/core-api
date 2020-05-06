/**
 * 
 */
package com.core.api.utils;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.core.api.config.DataSourceConfig;
import com.core.api.exception.DatabaseException;
import com.mysql.cj.exceptions.CJCommunicationsException;

/**
 * @author Pavan.DV
 *
 */
public class DatabaseUtil implements ILogger {

	public static void execute(String sqlQuery) {
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceConfig.getDataSource());
		List<Map<String, Object>> result = null;
		try {
			result = jdbcTemplate.queryForList(sqlQuery);
		} catch (BadSqlGrammarException e) {
			System.out.println("-------------------" + e.getLocalizedMessage());
			throw new DatabaseException(e);
		} catch (CJCommunicationsException e) {
			System.out.println("================================" + e.getLocalizedMessage());
		} catch (Exception e) {
			System.out.println("*******************************************************************************");
			System.err.println("exception occured " + e.getLocalizedMessage());
			System.err.println("exception occured " + e.getMessage());
			System.out.println("*******************************************************************************");
			e.printStackTrace();
		}
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
	}

	public static void main(String[] args) {
		execute("select * from dummyModel");
	}
}
