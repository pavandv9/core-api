/**
 * 
 */
package com.core.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.JSONObject;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.core.api.config.DataSourceConfig;
import com.core.api.config.NoSqlConfig;
import com.core.api.exception.DatabaseException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class DatabaseUtil implements ILogger {

	/** The system. */
	String system = "";
	
	/** The jdbc template. */
	JdbcTemplate jdbcTemplate = null;
	
	/** The mongo database. */
	MongoDatabase mongoDatabase;

	/**
	 * Instantiates a new database util.
	 */
	public DatabaseUtil() {
	}

	/**
	 * Set system.
	 *
	 * @param system the system
	 */
	public DatabaseUtil(String system) {
		this.system = system;
	}

	/**
	 * Set system.
	 *
	 * @param system the new system
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * Get system.
	 *
	 * @return system
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * Execute's sql query.
	 *
	 * @param sqlQuery the sql query
	 * @return list of data
	 */
	public List<Map<String, Object>> execute(String sqlQuery) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing execute query");
			throw new DatabaseException("Database system not found");
		}
		return execute(sqlQuery, system);
	}

	/**
	 * Update sql query.
	 *
	 * @param sqlQuery the sql query
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
	 * @param sqlQuery the sql query
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
	 * @param sqlQuery the sql query
	 * @param system the system
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
	 * @param sqlQuery the sql query
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
	 * @param sqlQuery the sql query
	 * @param system the system
	 */
	public void delete(String sqlQuery, String system) {
		if (system.isEmpty()) {
			LOG.warn("Set system before executing update query");
			throw new DatabaseException("Database system not found");
		}
		update(sqlQuery, system);
	}

	/**
	 * Get data from mongo database.
	 *
	 * @param collection the collection
	 * @param searchKey the search key
	 * @param searchValue the search value
	 * @return List of JSONObject
	 */
	public List<JSONObject> getDataFromMongo(String collection, String searchKey, String searchValue) {
		if (system.isEmpty()) {
			LOG.warn("Set system before getting collection");
			throw new DatabaseException("Database system not found");
		}
		return getDataFromMongo(system, collection, searchKey, searchValue);
	}

	/**
	 * Update data in mongo database.
	 *
	 * @param collection the collection
	 * @param searchKey the search key
	 * @param searchValue the search value
	 * @param updateKey the update key
	 * @param updateValue the update value
	 */
	public void updateDataInMongo(String collection, String searchKey, String searchValue, String updateKey,
			String updateValue) {
		if (system.isEmpty()) {
			LOG.warn("Set system before getting collection");
			throw new DatabaseException("Database system not found");
		}
		updateDataInMongo(system, collection, searchKey, searchValue, updateKey, updateValue);
	}

	/**
	 * Delete data in mongo database.
	 *
	 * @param collection the collection
	 * @param deleteKey the delete key
	 * @param deleteValue the delete value
	 */
	public void deleteDataInMongo(String collection, String deleteKey, String deleteValue) {
		if (system.isEmpty()) {
			LOG.warn("Set system before getting collection");
			throw new DatabaseException("Database system not found");
		}
		deleteDataInMongo(system, collection, deleteKey, deleteValue);
	}

	/**
	 * Insert data in mongo.
	 *
	 * @param collection the collection
	 * @param document the document
	 */
	public void insertDataInMongo(String collection, Document document) {
		if (system.isEmpty()) {
			LOG.warn("Set system before getting collection");
			throw new DatabaseException("Database system not found");
		}
		insertDataInMongo(system, collection, document);
	}

	/**
	 * Execute sql query.
	 *
	 * @param system the system
	 * @param sqlQuery the sql query
	 * @return list of data
	 */
	public List<Map<String, Object>> execute(String system, String sqlQuery) {
		if (jdbcTemplate == null || !system.equals(this.system)) {
			LOG.info("Connecting to database: " + system);
			try {
				jdbcTemplate = new JdbcTemplate(new DataSourceConfig().getDataSource(system));
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to connect to database");
			}
		}
		List<Map<String, Object>> result = null;
		try {
			LOG.info("{}:[{}]", "Executing sql query", sqlQuery);
			result = jdbcTemplate.queryForList(sqlQuery);
			LOG.info("{}{}", "Executed sql query successfully:", result);
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
	 * Update sql query.
	 *
	 * @param system the system
	 * @param sqlQuery the sql query
	 */
	public void update(String system, String sqlQuery) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(new DataSourceConfig().getDataSource(system));
		try {
			LOG.info("Connecting to database: " + system);
			int rowsAffected = jdbcTemplate.update(sqlQuery);
			LOG.info("Executed sql query successfully, number rows affected is " + rowsAffected);
			LOG.info(Logger.NEW_LINE + Logger.suffix);
		} catch (BadSqlGrammarException e) {
			LOG.error(e.getLocalizedMessage());
			throw new DatabaseException(e.getLocalizedMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			throw new DatabaseException("Unable to connect to database");
		}
	}

	/**
	 * Get data from mongo database.
	 *
	 * @param system the system
	 * @param collection the collection
	 * @param searchKey the search key
	 * @param searchValue the search value
	 * @return List of JSONObject
	 */
	public List<JSONObject> getDataFromMongo(String system, String collection, String searchKey, String searchValue) {
		if (mongoDatabase == null || !system.equals(this.system)) {
			LOG.info("Connecting to mongo database: " + system);
			try {
				mongoDatabase = new NoSqlConfig().getMongoConnection(system);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to connect to mongo database: " + e.getLocalizedMessage());
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
				throw new DatabaseException("Unable to retrive the data: " + e.getLocalizedMessage());
			}
		}
		return null;
	}

	/**
	 * Update data from mongo database.
	 *
	 * @param system the system
	 * @param collection the collection
	 * @param searchKey the search key
	 * @param searchValue the search value
	 * @param updateKey the update key
	 * @param updateValue the update value
	 */
	public void updateDataInMongo(String system, String collection, String searchKey, String searchValue,
			String updateKey, String updateValue) {
		if (mongoDatabase == null || !system.equals(this.system)) {
			LOG.info("Connecting to mongo database: " + system);
			try {
				mongoDatabase = new NoSqlConfig().getMongoConnection(system);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to connect to mongo database: " + e.getLocalizedMessage());
			}
			try {
				UpdateResult updateResult = mongoDatabase.getCollection(collection)
						.updateOne(Filters.eq(searchKey, searchValue), Updates.set(updateKey, updateValue));
				LOG.info("Number of mongo data updated: " + updateResult.getModifiedCount());
				LOG.info(Logger.NEW_LINE + Logger.suffix);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to update the data: " + e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Delete data from mongo database.
	 *
	 * @param system the system
	 * @param collection the collection
	 * @param deleteKey the delete key
	 * @param deleteValue the delete value
	 */
	public void deleteDataInMongo(String system, String collection, String deleteKey, String deleteValue) {
		if (mongoDatabase == null || !system.equals(this.system)) {
			LOG.info("Connecting to mongo database: " + system);
			try {
				mongoDatabase = new NoSqlConfig().getMongoConnection(system);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to connect to mongo database: " + e.getLocalizedMessage());
			}
			try {
				DeleteResult deleteResult = mongoDatabase.getCollection(collection)
						.deleteOne(Filters.eq(deleteKey, deleteValue));
				LOG.info("Number of mongo data deleted: " + deleteResult.getDeletedCount());
				LOG.info(Logger.NEW_LINE + Logger.suffix);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to delete the data: " + e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Insert data into mongo db.
	 *
	 * @param system the system
	 * @param collection the collection
	 * @param keyAndValue the key and value
	 */
	public void insertDataInMongo(String system, String collection, Map<String, Object> keyAndValue) {
		if (mongoDatabase == null || !system.equals(this.system)) {
			LOG.info("Connecting to mongo database: " + system);
			try {
				mongoDatabase = new NoSqlConfig().getMongoConnection(system);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to connect to mongo database: " + e.getLocalizedMessage());
			}
			try {
				Document document = new Document();
				keyAndValue.forEach((key, value) -> document.append(key, value));
				mongoDatabase.getCollection(collection).insertOne(document);
				LOG.info("Data inserted successfully");
				LOG.info(Logger.NEW_LINE + Logger.suffix);
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
				throw new DatabaseException("Unable to insert the data: " + e.getLocalizedMessage());
			}
		}
	}
}
