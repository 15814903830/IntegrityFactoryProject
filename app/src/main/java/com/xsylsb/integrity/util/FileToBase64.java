package com.xsylsb.integrity.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/21:26
 * @Description:
 */
public class FileToBase64 {
    public static String best64yimg(String file) {
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(file);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }







    /**
     * 质量压缩
     *
     * @param srcPath 原图路径
     * @param context
     * @param max 要压缩到多大以内（单位：kb）
     * @return
     */
    public static  String compressReSave(String srcPath, Context context, int max) {
        String filePath = "";
        Bitmap image = BitmapFactory.decodeFile(srcPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > max) { // 循环判断如果压缩后图片是否大于maxkb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            if (options > 10){
                options -= 10;// 每次都减少10
            } else {
                options -= 5;
            }

            if (options == 5){//这里最多压缩到5，options不能小于0
                break;
            }
        }
        FileOutputStream outStream = null;
        filePath = createFile(context, "myImg");
        try {
            outStream = new FileOutputStream(filePath);
            // 把数据写入文件
            outStream.write(baos.toByteArray());
            // 记得要关闭流！
            outStream.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outStream != null) {
                    // 记得要关闭流！
                    outStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String createFile(Context context, String pathName) {
        String path = "";
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + pathName);
        } else {
            file = new File(context.getFilesDir().getPath() + File.separator + pathName);
        }
        if (file != null) {
            if (!file.exists()) {
                file.mkdir();
            }
            File output = new File(file, System.currentTimeMillis() + ".png");
            try {
                if (output.exists()) {
                    output.delete();
                } else {
                    output.createNewFile();
                }
                path = output.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }









}
