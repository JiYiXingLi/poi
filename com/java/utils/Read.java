package com.java.utils;

import com.java.pojo.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Read
 * @Description 读取Excel表格中的所有数据
 * @Author LiYan
 * @Date 2020/4/8 13:10
 * @Version 1.0
 */
public class Read {

    public static List<User> read(String path) throws IOException {
        List<User> userList = new ArrayList<>();
        //.获取工作簿
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(path);
        //获取工作表
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        System.out.println(lastRowNum + "<------------------------------------");
        for (int i = 1; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                List<String> list = new ArrayList<>();
                for (Cell cell : row) {

                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String value = cell.getStringCellValue();
                    System.out.println(value + "<------");
                    if (value != null && !"".equals(value)) {
                        list.add(value);
                    }
                }
                System.out.println(list + "<-----------------------");
                if (list.size() > 0) {
                    User user = new User(Integer.parseInt(list.get(0)), list.get(1), list.get(2), null,null);
                    userList.add(user);
                }
            }
        }
        return userList;
    }
}
