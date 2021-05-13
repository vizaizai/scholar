package com.github.vizaizai.scholar.infrastructure.excel;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCell;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCells;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author liaochongwei
 * @date 2021/5/11 15:41
 */
public class Demo1 {
    public static void main(String[] args) throws IOException {

        long s = System.currentTimeMillis();
        //创建工作薄对象
        XSSFWorkbook workbook=new XSSFWorkbook();//这里也可以设置sheet的Name
        //创建工作表对象
        XSSFSheet sheet = workbook.createSheet();
        for (int i = 0; i < 50000; i++) {
            //创建工作表的行
            XSSFRow row = sheet.createRow(i);//设置第一行，从零开始
            row.createCell(0).setCellValue("单元格");//第一行第三列为aaaaaaaaaaaa
            row.createCell(1).setCellValue(new Date());//第一行第一列为日期
        }
        workbook.setSheetName(0,"sheet的Name");//设置sheet的Name

        // 合并
        for (int i = 0; i < 50000; i++) {
            //addMergedRegion(sheet.getCTWorksheet(),new CellRangeAddress(i, i, 0, 1));
            sheet.addMergedRegionUnsafe(new CellRangeAddress(i, i, 0, 1));
        }


        //文档输出
        FileOutputStream out = new FileOutputStream("D:\\f.xlsx");
        workbook.write(out);
        out.close();

        System.out.println("耗时:" + (System.currentTimeMillis() - s) +"ms");

    }

    private static void addMergedRegion(CTWorksheet sheetX, CellRangeAddress cellRangeAddress) {
        CTMergeCells ctMergeCells;
        if (sheetX.isSetMergeCells()) {
            ctMergeCells = sheetX.getMergeCells();
        } else {
            ctMergeCells = sheetX.addNewMergeCells();
        }

        CTMergeCell ctMergeCell = ctMergeCells.addNewMergeCell();
        ctMergeCell.setRef(cellRangeAddress.formatAsString());
    }
}
