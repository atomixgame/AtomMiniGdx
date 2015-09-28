/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.io;

import java.io.File;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FileUtils {

    public static String getDirectory(final String path) {
        if (path == null) {
            return null;
        }
        final int index1 = path.lastIndexOf('/');
        final int index2 = path.lastIndexOf('\\');
        final int index = Math.max(index1, index2);
        if (index < 0 || index + 1 >= path.length()) {
            return null;
        }
        return path.substring(0, index + 1);
    }

    public static String getFileName(final String path) {
        if (path == null) {
            return null;
        }
        final int index1 = path.lastIndexOf('/');
        final int index2 = path.lastIndexOf('\\');
        final int index = Math.max(index1, index2);
        if (index < 0 || index + 1 >= path.length()) {
            return path;
        }
        return path.substring(index + 1);
    }

    public static String getFileNameWithoutExt(final String path) {
        String filename = getFileName(path);
        if (filename == null) {
            return null;
        }
        final int index = filename.lastIndexOf(".");
        if (index > 0) {
            return filename.substring(0, index);
        }
        return filename;
    }

    public static String removeExt(final String path) {
        if (path == null) {
            return null;
        }
        final int index = path.lastIndexOf('.');
        if (index < 0) {
            return path;
        }
        return path.substring(0, index);
    }

    public static String toDirectory(String path) {
        if (path == null) {
            return null;
        }
        path = changeFileSeparator(path);
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }

    public static String[] toClassPath(String classpathRoot, String... path) {
        classpathRoot = toDirectory(classpathRoot);
        String[] ret = new String[path.length];
        for (int i = 0; i < path.length; i++) {
            String clsPath = path[i];
            clsPath = clsPath.replace('\\', '/');
            int index = clsPath.indexOf(classpathRoot);
            if (index >= 0) {
                clsPath = clsPath.substring(index + classpathRoot.length());
            }
            if (clsPath.indexOf(":") >= 0) {
                throw new RuntimeException("Can't remove base path " + classpathRoot + " from " + clsPath);
            }
            if (!clsPath.startsWith("/")) {
                clsPath = "/" + clsPath;
            }
            ret[i] = clsPath;
        }
        return ret;
    }

    public static String[] toClassPath(String... paths) {
        String classpathRoots = System.getProperty("java.class.path");
        String[] classpath = classpathRoots.split(File.pathSeparator);
        for (String entry : classpath) {
            if (containsPath(paths[0], entry)) {
                return toClassPath(entry, paths);
            }
        }
        return null;
    }

    public static boolean containsPath(CharSequence path, CharSequence subPath) {
        if (path.length() < subPath.length()) {
            return false;
        }
        for (int i = 0; i < subPath.length(); i++) {
            char c1 = path.charAt(i);
            char c2 = path.charAt(i);
            if (c1 != c2 && (!isPathSeparator(c1) || !isPathSeparator(c2))) {
                return false;
            }
        }
        if (path.length() == subPath.length()) {
            return true;
        }
        char lastChar = subPath.charAt(subPath.length() - 1);
        return isPathSeparator(lastChar) || isPathSeparator(path.charAt(subPath.length()));
    }

    public static boolean isPathSeparator(char c) {
        return c == '/' || c == '\\';
    }

    public static String changeFileSeparator(String path) {
        if (path == null) {
            return null;
        }
        if (path.indexOf('\\') >= 0) {
            path = path.replace('\\', '/');
        }
        return path;
    }

    public static int parseByRadix(final CharSequence number, final int radix) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            final char c = number.charAt(i);
            if (c != ' ') {
                buf.append(c);
            }
        }
        String str = buf.toString();
        return Integer.parseInt(str, radix);
    }

    public static void replace(StringBuilder buf, String from, String to) {
        int index = buf.indexOf(from);
        while (index >= 0) {
            buf.replace(index, index + from.length(), to);
            index = buf.indexOf(from);
        }
    }

    public static String getExtension(final String path) {
        if (path == null) {
            return null;
        }
        final int index = path.lastIndexOf('.');
        if (index < 0 || index >= path.length()) {
            return null;
        }
        return path.substring(index, path.length());
    }

    public static String concatPath(String... paths) {
        String concat = concatPath('/', paths);
        return changeFileSeparator(concat);
    }

    public static String concatPath(char separator, String... paths) {
        if (paths == null || paths.length == 0) {
            return null;
        }
        if (paths.length == 1) {
            return paths[0];
        }
        StringBuilder builder = new StringBuilder();
        builder.append(paths[0]);
        for (int i = 1; i < paths.length; i++) {
            if (builder.length() > 0) {
                char end = builder.charAt(builder.length() - 1);
                if (!isSeparatorChar(end)) {
                    builder.append(separator);
                }
            }
            String path = paths[i];
            if (path == null || path.isEmpty()) {
                continue;
            }
            char start = path.charAt(0);
            if (isSeparatorChar(start)) {
                path = path.substring(1);
            }
            builder.append(path);

        }
        String result = builder.toString();
        return result;
    }

    private static boolean isSeparatorChar(char c) {
        return c == '/' || c == File.separatorChar;
    }

    public static String addFilePostfix(String fileName, int count) {
        String file = removeExt(fileName);
        String ext = getExtension(fileName);
        String countStr = count < 10 ? "_0" + count : "_" + count;
        if (ext != null) {
            return file + countStr + ext;
        }
        return file + countStr;
    }

    public static boolean isSameBasePath(String fileName1, String fileName2) {
        if (fileName1 == null || fileName2 == null) {
            return false;
        }
        int i = 0;
        int j = 0;

        // skip base
        final int length1 = fileName1.length();
        final int length2 = fileName2.length();
        char c1 = 0;
        char c2 = 0;
        while (c1 == c2) {
            if (i >= length1 || j >= length2) {
                return length1 == length2;
            }
            c1 = fileName1.charAt(i);
            c2 = fileName2.charAt(j);
            i++;
            j++;
        }
        if (c1 == '_') {
            i++;
            if (i < length1) {
                c1 = fileName1.charAt(i);
            }
        }
        if (c2 == '_') {
            j++;
            if (j < length2) {
                c2 = fileName2.charAt(j);
            }
        }

        while (Character.isDigit(c1) && i < length1) {
            c1 = fileName1.charAt(i);
            i++;
        }
        while (Character.isDigit(c2) && j < length2) {
            c2 = fileName2.charAt(j);
            j++;
        }

        if (i == length1 && j == length2) {
            return true;
        }
        if (i == length1 || j == length2) {
            return false;
        }
        if (c1 != c2 || c1 != '.') {
            return false;
        }

        while (c1 == c2) {
            if (i == length1 && j == length2) {
                return true;
            }
            if (i == length1 || j == length2) {
                return false;
            }
            c1 = fileName1.charAt(i);
            c2 = fileName2.charAt(j);
            i++;
            j++;
        }
        return false;
    }
}
