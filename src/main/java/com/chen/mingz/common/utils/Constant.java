package com.chen.mingz.common.utils;

import org.apache.commons.io.IOUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chenmingzhi on 18/3/5.
 */
public class Constant {

    public static final String ENCODING = "UTF-8";


    public static final String FILESEPARATOR = getFileSeparator();

    public static SimpleDateFormat DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String SQLPATH = getFileSeparator() + "sql" + getFileSeparator();

    public static final String CONFIGPATH = getFileSeparator() + "config" + getFileSeparator();

    public static ConcurrentHashMap<String, String> SQLMAP = new ConcurrentHashMap<>();

    public static String USRDIR = System.getProperty("user.dir");

    public static AtomicLong atomicLong = new AtomicLong(10);

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    public static String getFileSeparator() {
        if (isWindowsPlatform()) {
            return "/";
        } else {
            return System.getProperty("file.separator");
        }
    }

    public static final boolean isWindowsPlatform() {
        return File.pathSeparatorChar == ';';
    }


    public static String desktop() {
        return FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    }

    public static String deskPlus() {
        return desktop() + getFileSeparator();
    }


    public static String getClob(java.sql.Clob clob) throws IOException {
        java.io.Reader reader = null;
        if (clob != null) {
            try {
                reader = clob.getCharacterStream();
                return new String(IOUtils.toString(reader));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            return "";
        } else {
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Map deepClone(Map obj) {
        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Map) clonedObj;

    }

}
