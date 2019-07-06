package org.yingzuidou.platform.common.utils;

import java.io.File;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/6
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class FileUtil {


    /**
     * 获取基于classpath的资源路径
     * 需要注意{@link Class#getResource(String)} 和 {@link ClassLoader#getResource(String)}是不一样的，前者path=/ 为
     * 获取classpath路径 后者 path=""为获取classpath路径
     *
     * @param path 资源路径，当path=/时表示获取classpath的路径
     * @return 当前运行环境的资源路径
     */
    public static String retrieveResourcePath(String path) {
        return Objects.requireNonNull(FileUtil.class.getClassLoader().getResource(path)).getPath();
    }

    /**
     * 根据传入的文件对象来创建新文件，如果上级目录不存在则先创建上级目录
     *
     * @param file 需要创建的文件对象
     * @return true表示文件不存在并创建成功， false表示文件已经存在
     */
    public static boolean createFile(File file){
        if (!file.exists()) {
            if (!file.isDirectory()) {
                File parent = file.getParentFile();
                boolean ff, fd = false;
                if (!parent.exists()) {
                    fd = parent.mkdirs();
                }
                try {
                    ff = file.createNewFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return ff || fd;
            } else {
                return file.mkdirs();
            }
        }
        return false;
    }

    /**
     * 如果文件或目录存在，则删除。需要注意如果是目录，那么在删除目录之前必须保证这个目录为空
     *
     * @param file 文件或目录对象
     * @return true 表示成功 false表示失败
     */
    public static boolean removeIfExist(File file) {
        if (file.exists()) {
          return file.delete();
        }
        return false;
    }

    /**
     * 如果存在同一个文件名，那么先删除再创建
     *
     * @param file 需要创建的文件
     * @return true 表示成功 false 失败
     */
    public static boolean deleteAndCreateIfExist(File file) {
        removeIfExist(file);
        createFile(file);
        return true;
    }
}
