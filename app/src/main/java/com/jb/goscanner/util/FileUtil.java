package com.jb.goscanner.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuheng
 */
public class FileUtil {
    /**
     * sdcard head
     */
    public final static String SDCARD = Environment
            .getExternalStorageDirectory().getPath();

    /**
     * 保存位图到sd卡目录下
     *
     * @param bitmap       ：位图资源
     * @param filePathName ：待保存的文件完整路径名
     * @param iconFormat   ：图片格式
     * @return true for 保存成功，false for 保存失败。
     * @author huyong
     */
    public static boolean saveBitmapToSDFile(final Bitmap bitmap,
                                             final String filePathName, CompressFormat iconFormat) {
        boolean result = false;
        if (bitmap == null || bitmap.isRecycled()) {
            return result;
        }
        try {
            createNewFile(filePathName, false);
            OutputStream outputStream = new FileOutputStream(filePathName);
            result = bitmap.compress(iconFormat, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 保存数据到指定文件
     *
     * @param byteData
     * @param filePathName
     * @return true for save successful, false for save failed.
     * @author huyong
     */
    public static boolean saveByteToSDFile(final byte[] byteData,
                                           final String filePathName) {
        boolean result = false;
        try {
            File newFile = createNewFile(filePathName, false);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            fileOutputStream.write(byteData);
            fileOutputStream.flush();
            fileOutputStream.close();
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param path   ：文件路径
     * @param append ：若存在是否插入原文件
     * @return
     * @author huyong
     */
    public static File createNewFile(String path, boolean append) {
        File newFile = new File(path);
        if (!append) {
            if (newFile.exists()) {
                newFile.delete();
            } else {
                // 不存在，则删除带png后缀名的文件
                File prePngFile = new File(path + ".png");
                if (prePngFile != null && prePngFile.exists()) {
                    prePngFile.delete();
                }
            }
        }
        if (!newFile.exists()) {
            try {
                File parent = newFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                newFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newFile;
    }

    /**
     * <br>
     * 功能简述:创建文件 <br>
     * 功能详细描述: <br>
     * 注意:1：如果不存在父文件夹，则新建文件夹；2：如果文件已存在，则直接返回
     *
     * @param destFileName
     * @param replace      是否删除旧文件，生成新文件
     * @return
     */
    public static boolean createFile(String destFileName, boolean replace) {
        File file = new File(destFileName);
        if (file.exists()) {
            if (replace) {
                file.delete();
            } else {
                System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
                return false;
            }
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
            return false;
        }
        if (!file.getParentFile().exists()) {
            System.out.println("目标文件所在路径不存在，准备创建。。。");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目录文件所在的目录失败！");
                return false;
            }
        }
        // 创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！");
            return false;
        }
    }

    /**
     * 指定路径文件是否存在
     *
     * @param filePath
     * @return
     * @author huyong
     */
    public static boolean isFileExist(String filePath) {
        boolean result = false;
        try {
            File file = new File(filePath);
            result = file.exists();
            file = null;
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 在媒体库中隐藏文件夹内的媒体文件 1. 加入.nomedia文件，使媒体功能扫描不到，用户可以通过文件浏览器方便看到 2.
     * 在文件夹前面加点，隐藏整个文件夹，用户需要对文件浏览器设置显示点文件才能看到
     *
     * @param folder 文件夹
     */
    public static void hideMedia(final String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(folder, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file = null;
    }

    /**
     * 创建文件夹（如果不存在）
     *
     * @param dir
     */
    public static void mkDir(final String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file = null;
    }

    /**
     * 在媒体库中显示文件夹内的媒体文件
     *
     * @param folder 文件夹
     */
    public static void showMediaInFolder(final String folder) {
        File file = new File(folder, ".nomedia");
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean copyFile(String srcStr, String decStr) {
        // 前提
        File srcFile = new File(srcStr);
        if (!srcFile.exists()) {
            return false;
        }
        File decFile = new File(decStr);
        if (!decFile.exists()) {
            File parent = decFile.getParentFile();
            parent.mkdirs();

            try {
                decFile.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(srcFile);
            output = new FileOutputStream(decFile);
            byte[] data = new byte[4 * 1024]; // 4k
            while (true) {
                int len = input.read(data);
                if (len <= 0) {
                    break;
                }
                output.write(data, 0, len);

                // just test how it will perform when a exception occure on
                // backing up
                // throw new IOException();
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (Exception e2) {
                }
            }
            if (null != output) {
                try {
                    output.flush();
                    output.close();
                } catch (Exception e2) {
                }
            }
        }

        return true;
    }

    /**
     * @param src         ：源文件
     * @param dst         ：目标文件
     * @param encryptbyte ：加密字节长度，不需加密，则传入0
     * @throws IOException
     * @author huyong
     */
    public static void copyOutPutFile(File src, File dst, int encryptbyte)
            throws IOException {
        FileInputStream srcStream = new FileInputStream(src);
        FileOutputStream dstStream = new FileOutputStream(dst);
        FileChannel inChannel = srcStream.getChannel();
        FileChannel outChannel = dstStream.getChannel();
        if (encryptbyte < 0) {
            encryptbyte = 0;
        }
        try {
            inChannel.transferTo(inChannel.size() - encryptbyte,
                    inChannel.size(), outChannel);
            outChannel.transferFrom(inChannel, outChannel.size(),
                    inChannel.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            srcStream.close();
            dstStream.close();
        }
    }

    /**
     * @param src         ：源文件
     * @param dst         ：目标文件
     * @param encryptbyte ：加密字节数，若不需要加密，直接传入0
     * @throws IOException
     * @author huyong
     */
    public static void copyInputFile(File src, File dst, int encryptbyte)
            throws IOException {
        FileInputStream srcStream = new FileInputStream(src);
        FileOutputStream dstStream = new FileOutputStream(dst);
        FileChannel inChannel = srcStream.getChannel();
        FileChannel outChannel = dstStream.getChannel();

        if (encryptbyte < 0) {
            encryptbyte = 0;
        }
        try {
            inChannel.transferTo(encryptbyte, inChannel.size(), outChannel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            srcStream.close();
            dstStream.close();
        }
    }

    /**
     * 根据给定路径参数删除单个文件的方法 私有方法，供内部其它方法调用
     *
     * @param filePath 要删除的文件路径
     * @return 成功返回true, 失败返回false
     */
    public static boolean deleteFile(String filePath) {
        // 定义返回结果
        boolean result = false;
        // //判断路径参数是否为空
        // if(filePath == null || "".equals(filePath)) {
        // //如果路径参数为空
        // System.out.println("文件路径不能为空~！");
        // } else {
        // //如果路径参数不为空
        // File file = new File(filePath);
        // //判断给定路径下要删除的文件是否存在
        // if( !file.exists() ) {
        // //如果文件不存在
        // System.out.println("指定路径下要删除的文件不存在~！");
        // } else {
        // //如果文件存在，就调用方法删除
        // result = file.delete();
        // }
        // }

        if (filePath != null && !"".equals(filePath.trim())) {
            File file = new File(filePath);
            if (file.exists()) {
                result = file.delete();
            }
        }
        return result;
    }

    /*
     * @param path 要删除的文件夹路径
     *
     * @return 是否成功
     */
    public static boolean deleteCategory(String path) {
        if (path == null || "".equals(path)) {
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            deleteDirectory(path);
        }

        return file.delete();
    }

    public static String getSdDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /*
     * 采用了新的办法获取APK图标，之前的失败是因为android中存在的一个BUG,通过 appInfo.publicSourceDir =
     * apkPath;来修正这个问题，详情参见:
     * http://code.google.com/p/android/issues/detail?id=9151
     */
    public static Drawable getApkIcon(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The extension separator character.
     *
     * @since 1.4
     */
    public static final char EXTENSION_SEPARATOR = '.';

    /**
     * Returns the index of the last directory separator character.
     * <p>
     * This method will handle a file in either Unix or Windows format. The
     * position of the last forward or backslash is returned.
     * <p>
     * The output will be the same irrespective of the machine that the code is
     * running on.
     *
     * @param filename the filename to find the last path separator in, null
     *                 returns -1
     * @return the index of the last separator character, or -1 if there is no
     * such character
     */
    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        }
        return filename.lastIndexOf(UNIX_SEPARATOR);
    }

    /**
     * Returns the index of the last extension separator character, which is a
     * dot.
     * <p>
     * This method also checks that there is no directory separator after the
     * last dot. To do this it uses {@link #indexOfLastSeparator(String)} which
     * will handle a file in either Unix or Windows format.
     * <p>
     * The output will be the same irrespective of the machine that the code is
     * running on.
     *
     * @param filename the filename to find the last path separator in, null
     *                 returns -1
     * @return the index of the last separator character, or -1 if there is no
     * such character
     */
    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = indexOfLastSeparator(filename);
        return lastSeparator > extensionPos ? -1 : extensionPos;
    }

    /**
     * Gets the extension of a filename.
     * <p>
     * This method returns the textual part of the filename after the last dot.
     * There must be no directory separator after the dot.
     * <p>
     * <pre>
     * foo.txt      --> "txt"
     * a/b/c.jpg    --> "jpg"
     * a/b.txt/c    --> ""
     * a/b/c        --> ""
     * </pre>
     * <p>
     * The output will be the same irrespective of the machine that the code is
     * running on.
     *
     * @param filename the filename to retrieve the extension of.
     * @return the extension of the file or an empty string if none exists or
     * {@code null} if the filename is {@code null}.
     */
    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    /**
     * Gets the name minus the path from a full filename.
     * <p>
     * This method will handle a file in either Unix or Windows format. The text
     * after the last forward or backslash is returned.
     * <p>
     * <pre>
     * a/b/c.txt --> c.txt
     * a.txt     --> a.txt
     * a/b/c     --> c
     * a/b/c/    --> ""
     * </pre>
     * <p>
     * The output will be the same irrespective of the machine that the code is
     * running on.
     *
     * @param filename the filename to query, null returns null
     * @return the name of the file without the path, or an empty string if none
     * exists
     */
    public static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfLastSeparator(filename);
        return filename.substring(index + 1);
    }

    public static String getPathFromFilepath(String filepath) {
        if (!TextUtils.isEmpty(filepath)) {
            int pos = filepath.lastIndexOf(UNIX_SEPARATOR);
            if (pos != -1) {
                return filepath.substring(0, pos);
            }
        }
        return "";
    }

    public static String getNameFromFilepath(String filepath) {
        if (!TextUtils.isEmpty(filepath)) {
            int pos = filepath.lastIndexOf(UNIX_SEPARATOR);
            if (pos != -1) {
                return filepath.substring(pos + 1);
            }
        }
        return "";
    }

    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.isFile()) {
                size = file.length();
            } else if (file.isDirectory()) {
                final File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File childFile : listFiles) {
                        if (childFile.isDirectory()) {
                            size = size + getFileSize(childFile);
                        } else {
                            size = size + childFile.length();
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return size;
    }

    public static byte[] getByteFromSDFile(final String filePathName) {
        byte[] bs = null;
        try {
            File newFile = new File(filePathName);
            FileInputStream fileInputStream = new FileInputStream(newFile);
            DataInputStream dataInputStream = new DataInputStream(
                    fileInputStream);
            BufferedInputStream inPutStream = new BufferedInputStream(
                    dataInputStream);
            bs = new byte[(int) newFile.length()];
            inPutStream.read(bs);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bs;
    }

    /**
     * <br>
     * 功能简述:删除文件夹 <br>
     * 功能详细描述: <br>
     * 注意:
     *
     * @param sPath
     * @return
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (files == null) {
            // 尽管从API理解上，只要非文件夹类型，就返回为空，在上面的代码中以及判断并返回，但是任然有部分手机执行到这里报空指针
            return false;
        }
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public static String copyFileToApkCacheFromAssetOut(Context context,
                                                        String fileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        // String apkFile = null;
        try {
            is = context.getAssets().open(fileName);
            // apkFile = createApkCachePath_out(context, fileName);
            // fos = new FileOutputStream(new File(apkFile));
            fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE
                    | Context.MODE_WORLD_WRITEABLE);
            byte[] buffer = new byte[1024];
            while (true) {
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
                fos.flush();
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File fileLocation = new File(context.getFilesDir(), fileName);

        return fileLocation.getPath();
    }

    private static String createApkCachePath(Context context, String fileName) {
        // 存放临时从assets目录中读取出来的dex文件的缓存目录
        final String apks_cache = "apks";
        String parentDir = context.getDir(apks_cache, Context.MODE_PRIVATE)
                .getAbsolutePath();
        String cacheName = null;
        File file = new File(parentDir);
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(parentDir + File.separator + fileName);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            cacheName = file.getAbsolutePath();
        }
        return cacheName;
    }

    public static String copyFileToApkCacheFromAsset(Context context,
                                                     String fileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        String apkFile = null;
        try {
            is = context.getAssets().open(fileName);
            apkFile = createApkCachePath(context, fileName);
            fos = new FileOutputStream(new File(apkFile));
            byte[] buffer = new byte[1024];
            while (true) {
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
                fos.flush();
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return apkFile;
    }

    public static boolean checkFileInAssets(Context context, String toCheckFile) {
        AssetManager assetMgr = null;
        boolean isContainShellEngine = false;
        InputStream input = null;
        try {
            assetMgr = context.getAssets();
            if (assetMgr != null) {
                input = assetMgr.open(toCheckFile);
            }
            isContainShellEngine = true;
        } catch (Exception e) {
            isContainShellEngine = false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                }
            }
        }
        return isContainShellEngine;
    }

    /**
     * 统一获取raw文件流中数据
     *
     * @param context
     * @param rawId
     * @return
     */
    public static String getShortStrDataFromRaw(Context context, int rawId) {
        String strData = null;
        if (context == null) {
            return strData;
        }
        // 从资源获取流
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(rawId);
            if (is != null) {
                byte[] buffer = new byte[128];
                int len = is.read(buffer); // 读取流内容
                if (len > 0) {
                    strData = new String(buffer, 0, len).trim(); // 生成字符串
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strData;
    }

    /**
     * @param strPath 获取文件夹下文件的路径
     * @return 文件夹下的文件列表
     * @author zhangxi
     * @date 2013-09-22
     */
    public static ArrayList<String> getDirFiles(String strPath) {
        ArrayList<String> strFileList = new ArrayList<String>();
        try {
            File dirFile = new File(strPath);
            // 如果dir对应的文件不存在，或者不是一个目录，则退出
            if (!dirFile.exists() || !dirFile.isDirectory()) {
                return null;
            }

            File[] files = dirFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 将文件夹下的文件返回，排除子文件夹
                if (files[i].isFile()) {
                    strFileList.add(files[i].getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strFileList;
    }

    public static String readFileToString(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return readToString(inputStream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return null;
    }

    /**
     * @param inputStream
     * @param encoding
     * @return
     */
    public static String readToString(InputStream inputStream, String encoding) {

        InputStreamReader in = null;
        try {
            StringWriter sw = new StringWriter();
            in = new InputStreamReader(inputStream, encoding);
            copy(in, sw);
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return null;
    }

    private static int copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[1024 * 4];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static int copy(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * <br>
     * 功能简述:将Asserts目录下的文件拷贝到指定的sd卡目录下 <br>
     * 功能详细描述: <br>
     * 注意:
     *
     * @param context
     * @param assertsName asserts目录下的资源名
     * @param dirFilePath 要拷贝的SD卡的路径
     * @param fileName    拷贝到sd卡后文件的名字
     */
    public static void copyAssetsToSdCard(Context context, String assertsName,
                                          String dirFilePath, String fileName) {
        if (assertsName != null && dirFilePath != null && fileName != null) {
            InputStream assertsInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                assertsInputStream = context.getAssets().open(assertsName);
                File mTargetFire = new File(dirFilePath, fileName);
                if (!mTargetFire.exists()) {
                    File parent = mTargetFire.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }

                    mTargetFire.createNewFile();
                    fileOutputStream = new FileOutputStream(mTargetFire);
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = assertsInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                }
            } catch (Exception e) {
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;
                    }
                    if (assertsInputStream != null) {
                        assertsInputStream.close();
                        assertsInputStream = null;
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * <br>
     * 功能简述: 通过安装包的包名，拿安装包路径 <br>
     * 功能详细描述: <br>
     *
     * @param context     上下文
     * @param packageName 安装包包名
     * @return
     */
    public static String getApkPath(Context context, String packageName) {
        String pathString = "";
        try {
            pathString = context.getPackageManager().getApplicationInfo(
                    packageName, 0).sourceDir;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return pathString;
    }

    /**
     * 获取apk本身大小
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getApkSize(Context context, String packageName) {
        long size = 0;
        String path = getApkPath(context, packageName);
        if (!TextUtils.isEmpty(path)) {
            size = new File(path).length();
        }
        return size;
    }

    /**
     * <br>
     * 功能简述: 文件重命名或移动文件，移动文件则会进行覆盖目标文件操作 <br>
     * 功能详细描述: <br>
     * 注意:
     *
     * @param srcStr    原文件名路径
     * @param decString 目标文件名路径
     */
    public static void renameFile(String srcStr, String decString) {
        File srcFile = new File(srcStr);
        if (srcFile == null || !srcFile.exists()) {
            return;
        }

        File decFile = new File(decString);
        if (decFile.exists()) {
            decFile.delete();
        }
        srcFile.renameTo(decFile);
    }

    /**
     * <br>
     * 功能简述: 返回某目录下以apk结尾的 <br>
     * 功能详细描述: <br>
     * 注意:
     *
     * @param path     文件路径
     * @param endStr   结尾字符串，一般传扩展名如：".apk"
     * @param isDelete 是否将文件后缀名去掉
     * @return
     */
    public static List<String> listFile(String path, final String endStr,
                                        boolean isDelete) {

        File file = new File(path);
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(endStr);
            }
        };
        File[] fs = file.listFiles(filter);

        List<String> list = new ArrayList<String>();
        for (File f : fs) {
            String fileName = f.getName();
            if (isDelete) {
                list.add(fileName.substring(0, fileName.lastIndexOf(endStr)));
            } else {
                list.add(fileName);
            }

        }
        return list;
    }

    public static File[] listFile(String path) {

        File file = new File(path);

        if (file.isDirectory()) {
            return file.listFiles();
        }

        return null;
    }

    public static ArrayList<File> scan(String path, int depth) {
        File file = new File(path);
        ArrayList<File> folderList = new ArrayList<File>();
        scanFolder(file, folderList, depth);
        return folderList;
    }

    /**
     * 根据指定的层数扫描文件夹
     *
     * @param file       扫描的文件夹
     * @param folderList 文件夹存储队列
     * @param depth      扫描的层数
     */

    private static void scanFolder(File file, ArrayList<File> folderList,
                                   int depth) {
        if (!file.isDirectory()) {
            return;
        }
        // 文件夹则添加到队列中
        folderList.add(file);
        if (depth <= 0 || file.listFiles() == null) {
            // 到达遍历的深度 或者 文件夹为空 则返回
            return;
        }

        depth--;
        for (File child : file.listFiles()) {
            scanFolder(child, folderList, depth);
        }
    }

    /**
     * 去除结尾的文件路径分隔符
     *
     * @param path 路径
     */
    public static String removeEndSeparator(String path) {
        if (path.endsWith(File.separator)) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 检测当前路径是否为空文件夹
     *
     * @param path 文件夹路径
     * @return 若为空文件夹，则返回true
     */
    public static boolean isEmptyFolder(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            final String[] list = file.list();
            return list == null || list.length == 0;
        }
        return false;
    }

    /**
     * 获取是否为目录
     *
     * @param path
     * @return
     */
    public static boolean isDirectory(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    /**
     * 获取某个路径下文件的OutputStream
     *
     * @param context
     * @param pathName
     * @return
     */
    public static OutputStream getExtCardOutputStream(Context context, String pathName) {
        // TODO: 17/4/8 不保险的方法，没验证过
        try {
            File file = new File(pathName);
            return context.getContentResolver().openOutputStream(Uri.fromFile(file));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否为有效视频文件。大小大于10kb
     *
     * @param file
     * @return
     */
    public static boolean isVaildFile(File file) {
        return FileUtil.getFileSize(file) > 10 * 1024;
    }
}
