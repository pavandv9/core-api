/**
 * 
 */
package com.core.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.core.api.config.DataSourceConfig;
import com.core.api.config.NoSqlConfig;
import com.core.api.exception.DatabaseException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mysql.cj.exceptions.CJCommunicationsException;

/**
 * @author Pavan.DV
 *
 */
public class DatabaseUtil implements ILogger {

	String system = "";
	JdbcTemplate jdbcTemplate = null;
	MongoDatabase mongoDatabase;

	public DatabaseUtil() {
	}

	/**
	 * Set system
	 * 
	 * @param system
	 */
	public DatabaseUtil(String system) {
		this.system = system;
	}

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
		update(sqlQuery, system);
	}

	/**
	 * Insert data to the database.
	 * 
	 * @param sqlQuery
	 */
	public void insert(String sqlQuery) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing update query");
			throw new DatabaseException("Database system not found");
		}
		update(sqlQuery, system);
	}

	/**
	 * Insert data to the database.
	 * 
	 * @param sqlQuery
	 */
	public void insert(String sqlQuery, String system) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing update query");
			throw new DatabaseException("Database system not found");
		}
		update(sqlQuery, system);
	}

	/**
	 * Delete data from the database.
	 * 
	 * @param sqlQuery
	 */
	public void delete(String sqlQuery) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing update query");
			throw new DatabaseException("Database system not found");
		}
		update(sqlQuery, system);
	}

	/**
	 * Delete data from the database.
	 * 
	 * @param sqlQuery
	 */
	public void delete(String sqlQuery, String system) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing update query");
			throw new DatabaseException("Database system not found");
		}
		update(sqlQuery, system);
	}

	/**
	 * Update sql query
	 * 
	 * @param sqlQuery
	 * @param system
	 */
	public void update(String sqlQuery, String system) {
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

	public List<JSONObject> getDataFromMongo(String collection, String searchKey, String searchValue) {
		if (system.isEmpty()) {
			LOG.warn("Set system before getting collection");
			throw new DatabaseException("Database system not found");
		}
		return getDataFromMongo(system, collection, searchKey, searchValue);
	}

	public List<JSONObject> getDataFromMongo(String system, String collection, String searchKey, String searchValue) {
		if (mongoDatabase == null || !system.equals(this.system)) {
			LOG.info("Connecting to mongo database: " + system);
			try {
				mongoDatabase = new NoSqlConfig().getMongoConnection(system);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to connect to mongo database");
			}
			try {
				FindIterable<Document> filerableDocument = mongoDatabase.getCollection(collection)
						.find(new Document(searchKey, searchValue));
				MongoCursor<Document> mongoCursor = filerableDocument.iterator();
				List<JSONObject> document = new ArrayList<JSONObject>();
				while (mongoCursor.hasNext())
					document.add(new JSONObject(mongoCursor.next().toJson()));
				LOG.info("Executed mongo query successfully: " + document);
				LOG.info(Logger.NEW_LINE + Logger.suffix);
				return document;
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to retrive the data");
			}
		}
		return null;
	}

	public static void main(String[] args) {
		DatabaseUtil databaseUtil = new DatabaseUtil();
		databaseUtil.setSystem("user");
		List<JSONObject> l = databaseUtil.getDataFromMongo("sampleCollection", "title", "MongoDB");
		System.err.println(l);
	}
}
