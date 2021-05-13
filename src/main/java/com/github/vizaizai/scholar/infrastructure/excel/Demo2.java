package com.github.vizaizai.scholar.infrastructure.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author liaochongwei
 * @date 2021/5/12 14:19
 */
public class Demo2 {
    public static void main(String[] args) throws IOException {
        String templateFileName ="D:\\data\\order_template.xlsx";
        String fileName = "D:\\data\\order_data.xlsx";


        XSSFWorkbook workbook1 = new XSSFWorkbook(templateFileName);
        XSSFWorkbook workbook2 = new XSSFWorkbook(fileName);

        XSSFSheet sheetAt1 = workbook1.getSheetAt(0);
        XSSFSheet sheetAt2 = workbook2.getSheetAt(0);

        int lastRowNum1 = sheetAt1.getLastRowNum();

        int firstRowNum2 = sheetAt2.getFirstRowNum();
        int lastRowNum2 = sheetAt2.getLastRowNum();


        int firstIndex = lastRowNum1 + 1;
        for (int i = firstRowNum2; i < lastRowNum2; i++) {
            XSSFRow row = sheetAt2.getRow(i);
            short firstCellNum = row.getFirstCellNum();
            short lastCellNum = row.getLastCellNum();

            for (int j = firstCellNum; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                CellType cellType = cell.getCellType();
                XSSFRow newRow = sheetAt1.getRow(firstIndex);
                if (newRow == null) {
                    newRow = sheetAt1.createRow(firstIndex);
                }
                switch (cellType) {
                    case NUMERIC:
                        double numericCellValue = cell.getNumericCellValue();
                        newRow.createCell(j).setCellValue(numericCellValue);
                        break;
                    case STRING:
                        String stringCellValue = cell.getStringCellValue();
                        newRow.createCell(j).setCellValue(stringCellValue);
                        break;
                    case BOOLEAN:
                        boolean booleanCellValue = cell.getBooleanCellValue();
                        newRow.createCell(j).setCellValue(booleanCellValue);
                        break;
                    default:
                        break;
                }


            }
            firstIndex ++;

        }

        FileOutputStream out = new FileOutputStream("D:\\data\\merge.xlsx");
        workbook1.write(out);
        out.close();
    }
}
