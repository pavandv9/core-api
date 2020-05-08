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

	static String system = "";

	public static void setSystem(String system) {
		DatabaseUtil.system = system;
	}

	/**
	 * Execute sql query. Set system before calling this method.
	 * 
	 * @param sqlQuery
	 * @return
	 */
	public static List<Map<String, Object>> execute(String sqlQuery) {
		if (system.isEmpty()) {
			LOG.warn("Set system before calling execute method");
			throw new DatabaseException("Database system not found");
		}
		return execute(sqlQuery, system);
	}

	public static List<Map<String, Object>> execute(String sqlQuery, String system) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(new DataSourceConfig().getDataSource(system));
		List<Map<String, Object>> result = null;
		try {
			LOG.info("Connecting to database: " + system);
			result = jdbcTemplate.queryForList(sqlQuery);
			LOG.info("Executed sql query successfully");
			LOG.info(Logger.NEW_LINE + Logger.suffix);
		} catch (BadSqlGrammarException e) {
			LOG.error(e.getLocalizedMessage());
			throw new DatabaseException(e.getLocalizedMessage());
		} catch (CJCommunicationsException e) {
			LOG.error(e.getLocalizedMessage());
			throw new DatabaseException("Database connection failed. " + e.getLocalizedMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			throw new DatabaseException("Unable to connect to database");
		}
		return result;
	}
}
