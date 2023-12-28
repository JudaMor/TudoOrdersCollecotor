package base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
	 public  void removeFirstLineFromCSV(String inputFilePath) throws IOException {
	        Path inputFile = Path.of(inputFilePath);
	        Path tempFile = Files.createTempFile(null, null);

	        try (var lines = Files.lines(inputFile);
	             var writer = Files.newBufferedWriter(tempFile)) {

	            lines.skip(1).forEach(line -> {
	                try {
	                    writer.write(line);
	                    writer.newLine();
	                } catch (IOException e) {
	                    throw new RuntimeException(e);
	                }
	            });
	        }

	        Files.move(tempFile, inputFile, StandardCopyOption.REPLACE_EXISTING);
	    }

}
