package common;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CommonMethods {

	
	public static JsonPath convertRawToJson(Response res) {
		
		String response = res.asString();
		JsonPath json = new JsonPath(response);
		return json;
	}

	public static String getSessionKey() {
		
		RestAssured.baseURI= "http://localhost:8080";
		Response stringResponse=given().header("Content-Type","application/json").body("{\"username\":\"deepas393\", \"password\": \"Deepa@1989\"}")
		.when().post("/rest/auth/1/session").then().statusCode(200).extract().response();
		
		JsonPath jsonResponse=convertRawToJson(stringResponse);
		String sessio_id=jsonResponse.get("session.value");
		return sessio_id;
	}
}
