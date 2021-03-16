package com.core.api.config;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.core.api.ConfigManager;
import com.core.api.constants.ConfigProperty;
import com.core.api.constants.DatabaseConstants;
import com.core.api.constants.ResourceFile;
import com.core.api.utils.JsonUtil;
import com.core.api.utils.PropertyUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

public class NoSqlConfig implements DatabaseConstants {

	public MongoDatabase getMongoConnection(String system) {
		JSONArray jsonArr = JsonUtil.readJsonFile(new PropertyUtil().getResourceFile(ResourceFile.DB_NOSQL_FILE));
		String env = ConfigManager.get(ConfigProperty.ENV);
		MongoDatabase mongoDatabase = null;
		for (Object obj : jsonArr) {
			JSONObject json = new JSONObject(obj.toString());
			MongoClient mongoClient = new MongoClient(
					json.getJSONObject(env).getJSONObject(system).get(HOST).toString(),
					Integer.parseInt(json.getJSONObject(env).getJSONObject(system).get(PORT).toString()));
			MongoCredential.createCredential(json.getJSONObject(env).getJSONObject(system).get(USERNAME).toString(),
					json.getJSONObject(env).getJSONObject(system).get(DATABASE).toString(),
					json.getJSONObject(env).getJSONObject(system).get(PASSWORD).toString().toCharArray());
			mongoDatabase = mongoClient
					.getDatabase(json.getJSONObject(env).getJSONObject(system).get(DATABASE).toString());
		}
		return mongoDatabase;
	}
}
