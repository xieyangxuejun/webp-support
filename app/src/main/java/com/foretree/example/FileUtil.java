package com.foretree.example;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateName() {
        return String.format("%1$s", (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDatePhotoFileName() {
        return String.format("IMG_%1$s.jpg", (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateGIFFileName() {
        return String.format("GIF_%1$s.gif", (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateAudioFileName() {
        return String.format("Audio_%1$s.mp3", (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateVideoFileName() {
        return String.format("VIDEO_%1$s.mp4", (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()));
    }

    public static void bitmap2File(Bitmap bitmap, File saveFile) {

        if (bitmap == null || saveFile == null) {
            return;
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(saveFile);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void bitmap2File(Bitmap bitmap, File saveFile, Bitmap.CompressFormat format, int quality) {
        if (bitmap == null || saveFile == null) {
            return;
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(saveFile);
            if (bitmap.compress(format, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void byte2File(byte[] buf, String filePath) {
        if (buf == null || filePath == null) {
            return;
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file;
        try {
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] File2byte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static byte[] File2byte(String filePath) {
        if (filePath == null) {
            return null;
        }
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static File inputStream2File(InputStream ins, String outPath) {
        try {
            File outFile = new File(outPath);
            OutputStream os = new FileOutputStream(outFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            return outFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeInfoToFile(String path, String data) {
        try {
            File newTextFile = new File(path);
            FileWriter fw = new FileWriter(newTextFile);
            fw.write(data);
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readFile(String path) {
        return readFile(path, Charset.defaultCharset());
    }

    public static String readFile(String path, Charset encoding) {
        return readFile(new File(path), encoding);
    }

    public static String readFile(File file) {
        return readFile(file, Charset.defaultCharset());
    }

    public static String readFile(File file, Charset encoding) {
        String resultString = "";
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                byte[] buffer = new byte[fis.available()];
                //noinspection ResultOfMethodCallIgnored
                fis.read(buffer);
                resultString = new String(buffer, encoding);
            } catch (Exception e1) {
                e1.printStackTrace();

                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultString;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File oldfile, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile.getPath()); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void copyFile(InputStream in, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                fs.write(buffer, 0, byteread);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeJPGFileToDisk(String savePath, Bitmap originalBitmap, int quality, boolean isBitmapRecycle) {
        if (savePath == null || originalBitmap == null) {
            return;
        }
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(savePath);
            bos = new BufferedOutputStream(fos);
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (isBitmapRecycle) {
                originalBitmap.recycle();
            }
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return mime;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return mime;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return mime;
            }
        }
        return mime;
    }

    public static String getExtensionName(String filename) {
        try {
            if ((filename != null) && (filename.length() > 0)) {
                int dot = filename.lastIndexOf('.');
                if ((dot > -1) && (dot < (filename.length() - 1))) {
                    return filename.substring(dot + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }


    public static String getFileNameByUrl(String url) {
        try {
            String result = url.substring(url.lastIndexOf("/") + 1);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getFileNameNoEx(String filename) {
        try {
            if ((filename != null) && (filename.length() > 0)) {
                int dot = filename.lastIndexOf('.');
                if ((dot > -1) && (dot < (filename.length()))) {
                    return filename.substring(0, dot);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }


    public static boolean checkedFileNotFound(File file) throws FileNotFoundException {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            String message = String.format("%s 文件不存在", file.getAbsolutePath());
            throw new FileNotFoundException(message);
        }
        return true;
    }

}