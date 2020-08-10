package com.matiange.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 *  <p>
 *      生成二维码的工具类
 *  </p>
 *
 * @author
 * @date 2020-04-08
 * */

public class QRCodeUtils {
    /**
     *  生成二维码
     * @param content 二维码的内容
     * @return BitMatrix对象
     * */
    public static BitMatrix createCode(String content) throws IOException {
        //二维码的宽高
        int width = 200;
        int height = 200;

        //其他参数，如字符集编码
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //容错级别为H
        hints.put(EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.H);
        //白边的宽度，可取0~4
        hints.put(EncodeHintType.MARGIN , 0);

        BitMatrix bitMatrix = null;
        try {
            //编码之后的URL，先解码
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);

           // bitMatrix = deleteWhite(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitMatrix;
    }

    /**
     *  删除生成的二维码周围的白边，根据审美决定是否删除
     * @param matrix BitMatrix对象
     * @return BitMatrix对象
     * */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    /**
     * 生成二维码对应的字节矩阵
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @return 二维码位矩阵
     * @throws WriterException 写入异常
     */
    private static BitMatrix doGenImage(String content, int width, int height) throws WriterException {
        return doGenImage(content, width, height, null);
    }

     /*
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @param margin  边距大小 单位为像素
     * @return 二维码位矩阵
     * @throws WriterException 写入异常
     */
    public static BitMatrix doGenImage(String content, int width, int height, Integer margin) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 出差纠正等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 文本编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        if (margin != null) {
            // 边缘空白大小
            hints.put(EncodeHintType.MARGIN, margin);
        }
        // 生成字节矩阵
        return qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
    }
}
