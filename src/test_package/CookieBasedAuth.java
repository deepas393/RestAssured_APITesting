package test_package;
import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.CommonMethods;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CookieBasedAuth {

	Properties property= new Properties();
	CommonMethods common = new CommonMethods();
	
	@BeforeMethod
	public void setup() throws IOException {
		FileInputStream file = new FileInputStream("//Users//manishlalsangi//Documents//workspace//com.restassured.learnings//src//resources//config.properties");
		property.load(file);
	}
	
	
	@Test
	public void testJiraCookieBasedAuth_createIssue()
	{
		
		Response res =given().header("Content-Type","application/json")
				.header("Cookie","JSESSIONID="+ CommonMethods.getSessionKey())
				.body("{\n" + 
						"    \"fields\": {\n" + 
						"       \"project\":\n" + 
						"       {\n" + 
						"          \"key\": \"RA\"\n" + 
						"       },\n" + 
						"       \"summary\": \"API tetsing defect.\",\n" + 
						"       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" + 
						"       \"issuetype\": {\n" + 
						"          \"name\": \"Bug\"\n" + 
						"       }\n" + 
						"   }\n" + 
						"}")
				.when()
				.post("/rest/api/2/issue/")
				.then().statusCode(201)
				.extract().response();
		JsonPath json = CommonMethods.convertRawToJson(res);
		String id =json.get("id");
		System.out.println(id);
	}
	
	@Test
	public void testJiraCookieBasedAuth_deleteIssue() {
		given().header("Content-Type","application/json")
		.header("Cookie","JSESSIONID="+ CommonMethods.getSessionKey())
		.when()
		.delete("/rest/api/2/issue/10002")
		.then().statusCode(204).extract().response();
		
	}
}
