package com.mgc.sharesanalyse.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 文件处理工具
 * Create by Czg on 2018/6/6
 */
public class FileUtil {

    /**
     * 保存字节流至文件
     *
     * @param bytes 字节流
     * @param file  目标文件
     */
    public static final boolean saveBytesToFile(byte[] bytes, File file) {
        if (bytes == null) {
            return false;
        }

        ByteArrayInputStream bais = null;
        BufferedOutputStream bos = null;
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            bais = new ByteArrayInputStream(bytes);
            bos = new BufferedOutputStream(new FileOutputStream(file));

            int size;
            byte[] temp = new byte[1024];
            while ((size = bais.read(temp, 0, temp.length)) != -1) {
                bos.write(temp, 0, size);
            }

            bos.flush();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bais = null;
            }
        }
        return false;
    }

    /**
     * 复制文件夹
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     */
    public static final void copyFileFolder(String srcFile, String destFile) {
        // 新建目标目录
        (new File(destFile)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(srcFile)).listFiles();
        if (file != null) {
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    // 源文件
                    File sourceFile = file[i];
                    // 目标文件
                    File targetFile = new File(
                            new File(destFile).getAbsolutePath()
                                    + File.separator + file[i].getName());
                    copyFile(sourceFile, targetFile);
                }
            }
        }
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir)
            throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(
                        new File(targetDir).getAbsolutePath() + File.separator
                                + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 根据URL获取到下载的文件名称
     *
     * @param url 下载路径
     * @return 下载文件名称
     */
    public static String getFileNameByUrl(String url) {
        int postion = url.lastIndexOf("/");
        return url.substring(postion + 1);
    }

    /**
     * 根据URL获得全文件名
     *
     * @param url URL
     */
    public static final String getFileFullNameByUrl(String url) {
        String name = null;
        if (url != null) {
            int start = url.lastIndexOf("/");
            int end = url.lastIndexOf("?");
            name = url.substring(start == -1 ? 0 : start + 1,
                    end == -1 ? url.length() : end);
        }
        return name;
    }

    /**
     * 根据文件路径获取文件名称
     *
     * @param path 文件路径
     * @return 文件名称
     */
    public static String getFileNameByPath(String path) {
        int postion = path.lastIndexOf("/");
        return path.substring(postion + 1);
    }

    /**
     * 复制文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     */
    public static final boolean copyFile(File srcFile, File destFile) {
        if (!srcFile.exists()) {
            return false;
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();

            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(destFile));

            int size;
            byte[] temp = new byte[1024];
            while ((size = bis.read(temp, 0, temp.length)) != -1) {
                bos.write(temp, 0, size);
            }

            bos.flush();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bis = null;
            }
        }
        return false;
    }

    public static final void saveBitmapToFile(Bitmap bitmap, String savePath) {
        try {
            File f = new File(savePath);

            f.createNewFile();

            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getCodeByFilePath(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                byte[] b = new byte[1024];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                fis.close();
                bos.close();
                buffer = bos.toByteArray();

                return buffer;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void saveBytesToFile(byte[] bfile, String filePath,
                                       String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static byte[] stringToByte(String code) {
        int b_leng = 0;
        int cfgData_x = 0;
        int cfgData_t = code.length() / 2 + b_leng;
        byte[] b = new byte[cfgData_t];
        for (int x = b_leng; x < cfgData_t; x++) {
            String s = code.substring(cfgData_x, cfgData_x + 2);
            b[x] = (byte) Integer.parseInt(s, 16);
            cfgData_x = cfgData_x + 2;
            b_leng++;
        }

        return b;
    }

    public static final void saveStringToFile(String value, String fileName) {
        File file = new File(fileName);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(value);
            fw.flush();
            fw.close();

            // OutputStreamWriter osw = new OutputStreamWriter(new
            // FileOutputStream(file, true),"GB2312");
            // osw.write(fileName);
            // osw.flush();
            // osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> readControlCodeFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                HashMap<String, String> hasmap = new HashMap<String, String>();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "";
                String[] str = new String[2];
                while ((line = reader.readLine()) != null) {
                    str = line.split(":");
                    hasmap.put(str[0], str[1]);
                }
                reader.close();
                return hasmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringByFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String str = null;
                String result = "";
                while ((str = reader.readLine()) != null) {
                    result += str;
                }
                reader.close();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 写到文件
    public static void writeToFile(String content, String dir,
                                   String fileName) {
        try {
            File path = new File(dir);
            File file = new File(dir, fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file, true);
            fw.write(content);
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除文件夹及下面的子文件
     *
     * @param * public static void deleteFile(File deletedFile) {
     *          if (deletedFile.exists()) { // 判断文件是否存在
     *          if (deletedFile.isFile()) { // 判断是否是文件
     *          deletedFile.delete(); // delete()方法 你应该知道 是删除的意思;
     *          } else if (deletedFile.isDirectory()) { // 否则如果它是一个目录
     *          File files[] = deletedFile.listFiles(); // 声明目录下所有的文件 files[];
     *          for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
     *          deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
     *          }
     *          }
     *          // deletedFile.delete();
     *          }
     *          }
     */

    public static boolean checkFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 获取当前年月日
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static final String getCurrentAllTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (TextUtils.isEmpty(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 删除整个文件夹下所有文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) ;
            file.delete();
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }



    /**
     * 解压assets目录下的zip到指定的路径
     * @param zipFileString ZIP的名称，压缩包的名称：xxx.zip
     * @param outPathString 要解压缩路径
     * @throws Exception
     */
    public static void UnZipAssetsFolder(Context context, String zipFileString, String
            outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(context.getAssets().open(zipFileString));
        ZipEntry zipEntry;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            if (zipEntry.isDirectory()) {
                //获取部件的文件夹名
                File folder = new File(outPathString );
                folder.mkdirs();
            } else {
                File file = new File(outPathString);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                // 获取文件的输出流
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // 读取（字节）字节到缓冲区
                while ((len = inZip.read(buffer)) != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }


    /**
     * 解压zip文件夹相应的文件(并删除老板插件，减压完后删除zip包)
     *
     * @param zipFile
     * @param folderPath
     * @return
     * @throws ZipException
     * @throws IOException
     */
    public static int unZipFiles(File zipFile, String folderPath) throws ZipException, IOException {


//        //删除老版插件
//        File oldFile=new File(folderPath);
//        FileUtil.deleteFile(oldFile);

        //解压zip文件
        //File zipFile=new File(zipPath);
        String rootPath = folderPath + "/";
        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                String dirstr = rootPath + ze.getName();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                File file = new File(dirstr);
                if (!file.exists()) {
                    file.mkdirs();
                }
                continue;
            }
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(rootPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();

        zipFile.delete();
        return 0;

    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名
     *
     * @param baseDir
     * @param absFileName
     * @return
     */
    private static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length >= 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);
            }
            if (!ret.exists()) {
                ret.mkdirs();
            }
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            return ret;
        }
        return null;
    }

    /**
     * 复制asset中文件到内存app对应目录的plugins目录下
     */
    public static void copyAssets2Data(Context context, String rootPath) {
        String inPath = "";
        String outPath = rootPath;
        copyFiles(context, inPath, outPath);
    }

    /**
     * 复制asset中文件到内存app对应目录的plugins目录下
     */
    private static boolean copyFiles(Context context, String inPath, String outPath) {

        String[] fileNames = null;
        try {
            fileNames = context.getAssets().list(inPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (fileNames.length > 0) {//如果是目录
            File fileOutDir = new File(outPath);
            if (fileOutDir.isFile()) {//判断是否为目录
                boolean ret = fileOutDir.delete();
                if (!ret) {//删除失败处理
                }
            }
            if (!fileOutDir.exists()) {//如果文件路径不存在
                if (!fileOutDir.mkdirs()) {//创建文件夹
                    //创建失败情况处理
                }
            }
            for (String fileName : fileNames) {//递归调用复制文件夹
                String inDir = inPath;
                String outDir = outPath + File.separator;
                if (!inPath.equals("")) {//空目录下递归处理
                    inDir = inDir + File.separator;
                }
                copyFiles(context, inDir + fileName, outDir + fileName);
            }
            return true;
        } else {
            FileOutputStream fos = null;
            InputStream is = null;
            try {
                File fileOut = new File(outPath);
                if (!fileOut.exists()) {
                    boolean ret = fileOut.createNewFile();
                    //if (!ret) {
                    // }
                    //  return true;
                }

                fos = new FileOutputStream(fileOut);
                is = context.getAssets().open(inPath);
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();

                return true;
            } catch (Exception e) {
                return false;
            } finally {
                try {
                    is.close();
                    fos.close();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 读取file
     */
    public static String readFile(String filepath) {
        StringBuffer content = new StringBuffer();
        //开始读文件
        if (filepath != null) {
            File fileOut;
            FileInputStream is = null;
            BufferedReader reader = null;
            try {
                fileOut = new File(filepath);
                is = new FileInputStream(fileOut);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                try {
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    public static void unZipStream(InputStream in, String outPutDierctory) throws IOException {
        File file = new File(outPutDierctory);
        if (!file.exists()) {
            file.mkdirs();
        }
        ZipInputStream zipInputStream = new ZipInputStream(in);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        int count = 0;
        byte[] buffer = new byte[1024 * 1024];
        while (zipEntry != null) {
            if (zipEntry.isDirectory()) {
                file = new File(outPutDierctory + File.separator + zipEntry.getName());
                file.mkdir();
            } else {
                file = new File(outPutDierctory + File.separator + zipEntry.getName());
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);

                while ((count = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, count);
                }
                outputStream.close();
            }
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    //创建文件夹
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if (file.getParentFile().exists()) {
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.mkdir();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }

    //创建文件
    public static void createFile(File file) throws IOException {
        if (file.getParentFile().exists()) {
            file.createNewFile();
        } else {
            //创建目录之后再创建文件
            createDir(file.getParentFile().getAbsolutePath());
            file.createNewFile();
        }
    }

    public static void copyAssets2Sdcard(Context context, String assertsFileName, String sdcardFileName) {
        try {
            InputStream inputStream = context.getAssets().open(assertsFileName);
            OutputStream outputStream = new FileOutputStream(sdcardFileName);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
