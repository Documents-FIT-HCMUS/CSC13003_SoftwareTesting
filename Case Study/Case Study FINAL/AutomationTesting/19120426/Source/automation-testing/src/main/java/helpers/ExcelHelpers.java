package helpers;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelHelpers {
    private FileInputStream source;
    private FileOutputStream destination;
    private Workbook workbook;
    private Sheet sheet;
    private Cell cell;
    private Row row;
    private CellStyle cellStyle;
    private  Color color;
    private String excelFilePath;
    private final Map<String, Integer> columns = new HashMap<>();

    public void setExcelFilePath(String excelPath, String sheetName) throws Exception {
        try {
            File file = new File(excelPath);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File doesn't exist, so created!");
            }

            source = new FileInputStream(excelPath);
            workbook = WorkbookFactory.create(source);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            this.excelFilePath = excelPath;

            sheet.getRow(0).forEach(c -> {
                columns.put(c.getStringCellValue(), c.getColumnIndex());
            });
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public int getRowCount() throws Exception{
        return sheet.getLastRowNum() - sheet.getFirstRowNum();
    }

    public String getCellDataHelpers(int rowNumber, int columnNumber) throws Exception {
        try {
            cell = sheet.getRow(rowNumber).getCell(columnNumber);
            String cellData = "";
            switch (cell.getCellType()) {
                case STRING:
                    cellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellData = String.valueOf(cell.getDateCellValue());
                    }
                    else {
                        cellData = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    cellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    cellData = "";
                    break;
            }
            return cellData;
        }
        catch (Exception exception) {
            return "";
        }
    }

    public String getCellData(String columnName, int rowNumber) throws Exception {
        return getCellDataHelpers(rowNumber, columns.get(columnName));
    }

    public void setCellData(String content, int rowNumber, String columnName) throws Exception {
        try {
            row = sheet.getRow(rowNumber);
            if (row == null) {
                row = sheet.createRow(rowNumber);
            }

            int columnNumber = columns.get(columnName);
            cell = row.getCell(columnNumber);
            if (cell == null) {
                cell = row.createCell(columnNumber);
            }
            cell.setCellValue(content);

            destination = new FileOutputStream(excelFilePath);
            workbook.write(destination);
            destination.flush();
            destination.close();
        }
        catch (Exception exception) {
            throw (exception);
        }
    }
}
