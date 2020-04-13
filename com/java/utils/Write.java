package com.java.utils;

import com.java.pojo.User;
import org.apache.poi.hssf.usermodel.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * @ClassName write
 * @Description 在Excel表格中写入图片
 * @Author LiYan
 * @Date 2020/4/4 3:00
 * @Version 1.0
 */
public class Write {

    public static String write(List<User> productList, HttpServletRequest request) {
        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        try {
            //创建工作簿
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建工作表
            HSSFSheet sheet1 = wb.createSheet("sheet1");
            sheet1.setColumnWidth(2, 256 * 40);
            HSSFRow row = sheet1.createRow(0);
            row.createCell(0).setCellValue("编号");
            row.createCell(1).setCellValue("姓名");
            row.createCell(2).setCellValue("截图");
            HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
            String str = Long.toString(System.currentTimeMillis());
            String path = request.getServletContext().getRealPath("download") + "/" + str + ".xls";
            for (int i = 0; i < productList.size(); i++) {
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                HSSFRow row1 = sheet1.createRow(i + 1);
                row1.setHeightInPoints(250);
                row1.createCell(0).setCellValue(i + 1);
                row1.createCell(1).setCellValue(productList.get(i).getUserName());
                String name = productList.get(i).getImageName();
                if (name == null || name == "") {
                    //结束本次循环
                    continue;
                }
                System.out.println(name);
                bufferImg = ImageIO.read(new File(name));
                ImageIO.write(bufferImg, "jpg", byteArrayOut);
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 2, i + 1, (short) 3, i + 2);
                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                anchor = null;
            }

            fileOut = new FileOutputStream(path);

            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
            // 输出文件
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
