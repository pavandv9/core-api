[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pavandv9/core-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pavandv9/core-api)

# core-api
### API Automation Framework
API Framework is developed using HttpClients for api handliing, SpringFramework for Database connection and sending Mail.

## Initial Setup
1. Clone the core-api and import to any IDE `git clone https://github.com/pavandv9/core-api.git`.
2. Create your testing maven project, add testng dependency, add below dependency and build it.
``` maven
<dependency>
	<groupId>dv</groupId>
	<artifactId>core-api</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```
3. Install lombok.
	* Download here [Download Lombok](https://projectlombok.org/downloads/lombok.jar) goto the jar location from command prompt and run the following command 	`java -jar lombok-1.16.18.jar`
	* Now click on the “Specify Location” button and locate the eclipse.exe path under eclipse installation folder like this.
	* Now we need to finally install this by clicking the “Install/Update” button and we should finished installing lombok in eclipse
	* Quit and relaunch the IDE(eclipse) then build the project.
	Reference link: [Mac](https://nawaman.net/blog/2017-11-05), [Windows/Ubuntu](https://howtodoinjava.com/automation/lombok-eclipse-installation-examples/)
***

## Usage:
**Creating Request**
1. Implement ServiceHelper interface to class(SampleServiceHelper.class).
2. In method Create HttpRequest`(import from com.core.api)` object.
3. Add all required methods found in HttpRequest object.
4. To send request → `getHttpClient().execute(httpRequestObject)` will return HttpResponse.
[Sample Screenshot](https://drive.google.com/open?id=1PY5yg7bwNzuzwg73CKLSeg7FtzFZuHHH)

**Reading Response**
1. HttpResponse object has status code, status message, status line, body, response headers, and parse response body.
2. To get value from response pass json path to `parse` method.
[Sample Screenshot](https://drive.google.com/open?id=1lbIPr9wE-4bEDfbS-Fq1cSRNlwdq1JP8)

***

## Property Utilities
### Creating property files:
1. Create `config.properties` in `src/main/resources` and set below property to read it by default, no need to set in tests.
		* example : `env=prod`
	* Create an `env-file` folder under `src/main/resources`.
	* Create `env.properties` like `prod.properties` under `env-file` and add below common properties.
		* base_url
		* authorization etc...

2. Create `mail.properties` in `src/main/resources` to send mail and add below mandatory properties.
	* send_mail=true
	* host=gmail
	* from=abc@gmail.com
	* password=1234
	* to=xyz@gmail.com
	* cc=pmn@gmail.com,def@gmail.com
	* subject=Subject for mail
	* text=Text for mail

> send_mail to be either true or false, if true will send mail

> host to be either gmail or outlook or office365

> comma separated values for to and cc if it is more than 1 address

> cc, subject & text can be empty 

**Important note:** 

To send mail add `@Listeners(MailListner.class)` to all test class like below
``` java
@Listeners(MailListner.class)
public class TestApiDemo{

}
```
OR
Add below listener in `testng.xml` file under `<suite>` tag.
	
``` maven
<listeners>
	<listener class-name="com.core.api.listners.MailListner" />
</listeners>
```

### Usage:
* To get properties from `config.properties` file 
``` java
ConfigManager.get(String key);
```

* To load and read from other property files 
``` java
PropertyUtil.loadProperties(File file);
PropertyUtil.loadProperties(String filepath);
PropertyUtil.get(String key);
```


***

## Database Utility
### Creating database file:
1. Create `db.json` in `src/main/resources` and follow the below schema to add the attributes.
``` json
[
	{
		"environment": {
			"system": {
				"datbase_type": "",
				"host": "",
				"database": "",
				"port": "",
				"username": "",
				"password": "”
			}
		}
	}
]
```
[Sample Screenshot](https://drive.google.com/file/d/1yiNu5qCWC8YjrbxbRhoBuNP2bUMg6PAA/view?usp=sharing)

### Usage
1. Configuration will be based on the env found in the `config.property` file. 

Option 1:
* Need to pass sql query and system all the time 
```java
DatabaseUtil.execute(String sqlQuery, String system);
```


Option 2:
To Avoid setting system all the setSytem globally once the execute sql query.
``` java
DatabaseUtil.setSytem(String system);
DatabaseUtil.execute(String sqlQuery);
```

***
_Feel free to contact for any concerns [here](https://docs.google.com/forms/d/e/1FAIpQLSetfdBIBUXuDFQQ8hn0cmVgqontNmsYTA6sS13YGXmG6ATVUg/viewform)_ or
_mail to : pavandv9@gmail.com_
