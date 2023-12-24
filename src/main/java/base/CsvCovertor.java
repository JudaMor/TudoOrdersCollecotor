package base;
import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CsvCovertor {
	 public static void convertXlsToCsv(String xlsFilePath, String csvFilePath) {
	        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(xlsFilePath)));
	             BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(csvFilePath)))) {

	            XSSFSheet sheet = workbook.getSheetAt(0); // Assuming the first sheet is the one you want to convert

	            Iterator<Row> rowIterator = sheet.iterator();
	            while (rowIterator.hasNext()) {
	                Row row = rowIterator.next();

	                Iterator<Cell> cellIterator = row.iterator();
	                while (cellIterator.hasNext()) {
	                    Cell cell = cellIterator.next();
	                    csvWriter.write(cell.toString());
	                    if (cellIterator.hasNext()) {
	                        csvWriter.write(",");
	                    }
	                }
	                csvWriter.newLine();
	            }

	            System.out.println("Conversion complete. CSV file saved at: " + csvFilePath);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
