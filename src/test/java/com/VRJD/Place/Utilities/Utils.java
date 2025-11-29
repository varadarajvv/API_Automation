package com.VRJD.Place.Utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ResourceBundle;

import org.json.JSONObject;
import org.json.JSONTokener;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification requestSpecification;
	public static Response response;
	public static ResourceBundle resourceBundle;

	public RequestSpecification requestSpecification() throws IOException {
		if (requestSpecification == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpecification = new RequestSpecBuilder().setBaseUri(getBaseURI("BaseURI"))
					.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return requestSpecification;
		}
		return requestSpecification;
	}

	public RequestSpecification requestSpecification(String baseURI) throws IOException {
		if (requestSpecification == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpecification = new RequestSpecBuilder().setBaseUri(getBaseURI(baseURI))
					.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return requestSpecification;
		}
		return requestSpecification;
	}

	public static String getBaseURI(String key) {
		resourceBundle = ResourceBundle.getBundle("config");
		return resourceBundle.getString(key);
	}

	public String getJsonPath(Response response, String key) {
		JsonPath jsonPath = new JsonPath(response.asString());
		return jsonPath.get(key).toString();
	}

	public static String getRequestBody(String filePath) throws FileNotFoundException {
		FileReader fileReader = new FileReader(filePath);
		JSONTokener jsonTokener = new JSONTokener(fileReader);
		JSONObject data = new JSONObject(jsonTokener);
		return data.toString();
	}

}
