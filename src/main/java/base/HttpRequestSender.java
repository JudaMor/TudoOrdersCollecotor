package base;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class HttpRequestSender {
	
	public void sendWebhookAndCsvFile(String webhookUrl, String csvPath) {
//	     RestAssured.baseURI = "https://hook.eu2.make.com/uwf6gbaohxhcab5upuc649fkhrtgbeq8";

	        // Specify the path to the CSV file
	        File csvFile = new File(csvPath);
	     RestAssured.given()
         .multiPart("file", csvFile, "text/csv")

	     .contentType(ContentType.MULTIPART)
         .when()
         .post(webhookUrl)
         .then()
         .statusCode(200);
	        System.out.println("Webhook sent successfully!");

	        // Send the POST request with the CSV file as the request body
//	        RestAssured.given()
//	                .multiPart("file", csvFile, "text/csv")
//	                .when()
//	                .post()
//	                .then()
//	                .statusCode(200) // Replace with the expected status code
//	                .log().all();
	}
	

}
