/**
 * 
 */
package com.core.api.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pavan.DV
 *
 */
@Configuration
public class DataSourceConfig {

	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(DatabaseDriver.MYSQL.getDriverClassName());
		dataSourceBuilder.url("jdbc:mysql://db4free.net:3306/dummy_api_db");
		dataSourceBuilder.username("pavandv9");
		dataSourceBuilder.password("98765p@P");
		return dataSourceBuilder.build();
	}
}