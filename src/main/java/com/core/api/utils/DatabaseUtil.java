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

	String system = "";
	JdbcTemplate jdbcTemplate = null;

	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * Execute's sql query.
	 * 
	 * @param sqlQuery
	 * @return
	 */
	public List<Map<String, Object>> execute(String sqlQuery) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing execute query");
			throw new DatabaseException("Database system not found");
		}
		return execute(sqlQuery, system);
	}

	/**
	 * Execute sql query.
	 * 
	 * @param sqlQuery
	 * @param system
	 * @return
	 */
	public List<Map<String, Object>> execute(String sqlQuery, String system) {
		if (jdbcTemplate == null || !system.equals(this.system)) {
			LOG.info("Connecting to database: " + system);
			try {
				jdbcTemplate = new JdbcTemplate(new DataSourceConfig().getDataSource(system));
			} catch (CJCommunicationsException e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Database connection failed. " + e.getLocalizedMessage());
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to connect to database");
			}
		}
		List<Map<String, Object>> result = null;
		try {
			result = jdbcTemplate.queryForList(sqlQuery);
			LOG.info("Executed sql query successfully");
			LOG.info(Logger.NEW_LINE + Logger.suffix);
		} catch (BadSqlGrammarException e) {
			LOG.error(e.getLocalizedMessage());
			throw new DatabaseException("Check the SQL query, " + e.getLocalizedMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			throw new DatabaseException("Failed to execute query, " + e.getLocalizedMessage());
		}
		return result;
	}

	/**
	 * Update sql query
	 * 
	 * @param sqlQuery
	 */
	public void update(String sqlQuery) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing update query");
			throw new DatabaseException("Database system not found");
		}
		execute(sqlQuery, system);
	}

	/**
	 * Update sql query
	 * 
	 * @param sqlQuery
	 * @param system
	 */
	public static void update(String sqlQuery, String system) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(new DataSourceConfig().getDataSource(system));
		try {
			LOG.info("Connecting to database: " + system);
			int rowsAffected = jdbcTemplate.update(sqlQuery);
			LOG.info("Executed sql query successfully, number rows affected is " + rowsAffected);
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
	}
}
