package base;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class HttpRequestSender {
	
	public void sendWebhookAndCsvFile(String webhookUrl, String csvPath) {
	     RestAssured.baseURI = "https://example.com/api";

	        // Specify the path to the CSV file
	        File csvFile = new File(csvPath);

	        // Send the POST request with the CSV file as the request body
	        RestAssured.given()
	                .multiPart("file", csvFile, "text/csv")
	                .when()
	                .post()
	                .then()
	                .statusCode(200) // Replace with the expected status code
	                .contentType(ContentType.JSON) // Replace with the expected content type
	                .log().all();
	}
	

}
