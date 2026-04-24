package app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

    public static List<Map<String, String>> read(String path) {
        List<Map<String, String>> data = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new File(path))) {

            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();

            for (Cell cell : headerRow) {
                headers.add(cell.toString());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null)
                    continue;

                Map<String, String> rowData = new HashMap<>();
                boolean linhaVazia = true;

                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String valor = (cell == null) ? "" : cell.toString();

                    if (!valor.isBlank()) {
                        linhaVazia = false;
                    }

                    rowData.put(headers.get(j), valor);
                }

                if (!linhaVazia) {
                    data.add(rowData);
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler Excel: " + e.getMessage());
        }

        return data;
    }
}