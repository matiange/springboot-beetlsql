package com.matiange;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.matiange.entity.A;
import com.matiange.entity.B;
import com.matiange.utils.QRCodeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description:
 * @date 2020/7/4 12:22
 */
public class MainTest {
    public static void main(String[] args) throws WriterException {
        try {
//            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\kevin\\Desktop\\临时文件\\ss.jpg");
//            BitMatrix bitMatrix = QRCodeUtils.createCode("ssss");
            // 生成二维码矩阵
            BitMatrix bitMatrix = QRCodeUtils.doGenImage("yxtk20191007", 1200, 1200, 5);
//            MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , outputStream);//写入流中
            Path path = FileSystems.getDefault().getPath("C:\\Users\\kevin\\Desktop\\临时文件\\yxtk.jpg");
            MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);//写入文件中
            System.out.println("========二维码生成成功=======");
        } catch (IOException e) {
            e.printStackTrace();
        }

       	/*	//获取一个二维码图片
		BixtMatrix bitMatrix = QRCodeUtils.createCode(content);
		//以流的形式输出到前端
		MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);*/
    }
}
