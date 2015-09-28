/**
 * *****************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *****************************************************************************
 */
package sg.atom.core.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Provides utility methods to copy streams.
 * 
 * <p>Replace with Common IO
 */@Deprecated
public class StreamUtils {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final byte[] EMPTY_BYTES = new byte[0];

    
    /**
     * Convenient close for a stream. Use in a finally clause and love life.
     */
    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ioe) {
//                log.warning("Error closing input stream", "stream", in, "cause", ioe);
            }
        }
    }

    /**
     * Convenient close for a stream. Use in a finally clause and love life.
     */
    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException ioe) {
//                log.warning("Error closing output stream", "stream", out, "cause", ioe);
            }
        }
    }

    /**
     * Convenient close for a Reader. Use in a finally clause and love life.
     */
    public static void close(Reader in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ioe) {
//                log.warning("Error closing reader", "reader", in, "cause", ioe);
            }
        }
    }

    /**
     * Convenient close for a Writer. Use in a finally clause and love life.
     */
    public static void close(Writer out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException ioe) {
//                log.warning("Error closing writer", "writer", out, "cause", ioe);
            }
        }
    }

    /**
     * Copies the contents of the supplied input stream to the supplied output
     * stream.
     */
    public static <T extends OutputStream> T copy(InputStream in, T out)
            throws IOException {
        byte[] buffer = new byte[4096];
        for (int read = 0; (read = in.read(buffer)) > 0;) {
            out.write(buffer, 0, read);
        }
        return out;
    }

    /**
     * Reads the contents of the supplied stream into a byte array.
     */
    public static byte[] toByteArray(InputStream stream)
            throws IOException {
        return copy(stream, new ByteArrayOutputStream()).toByteArray();
    }

    /**
     * Reads the contents of the supplied stream into a string using the
     * platform default charset.
     */
    public static String toString(InputStream stream)
            throws IOException {
        return copy(stream, new ByteArrayOutputStream()).toString();
    }

    /**
     * Reads the contents of the supplied stream into a string using the
     * supplied {@link Charset}.
     */
    public static String toString(InputStream stream, String charset)
            throws IOException {
        return copy(stream, new ByteArrayOutputStream()).toString(charset);
    }

    /**
     * Reads the contents of the supplied reader into a string.
     */
    public static String toString(Reader reader)
            throws IOException {
        char[] inbuf = new char[4096];
        StringBuilder outbuf = new StringBuilder();
        for (int read = 0; (read = reader.read(inbuf)) > 0;) {
            outbuf.append(inbuf, 0, read);
        }
        return outbuf.toString();
    }
    /**
     * Copy the data from an {@link InputStream} to an {@link OutputStream}
     * without closing the stream.
     *
     * @throws IOException
     */
    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        copyStream(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copy the data from an {@link InputStream} to an {@link OutputStream}
     * without closing the stream.
     *
     * @throws IOException
     */
    public static void copyStream(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    /**
     * Copy the data from an {@link InputStream} to a byte array without closing
     * the stream.
     *
     * @throws IOException
     */
    public static byte[] copyStreamToByteArray(InputStream input) throws IOException {
        return copyStreamToByteArray(input, input.available());
    }

    /**
     * Copy the data from an {@link InputStream} to a byte array without closing
     * the stream.
     *
     * @param estimatedSize Used to preallocate a possibly correct sized byte
     * array to avoid an array copy.
     * @throws IOException
     */
    public static byte[] copyStreamToByteArray(InputStream input, int estimatedSize) throws IOException {
        ByteArrayOutputStream baos = new OptimizedByteArrayOutputStream(Math.max(0, estimatedSize));
        copyStream(input, baos);
        return baos.toByteArray();
    }

    /**
     * Copy the data from an {@link InputStream} to a string using the default
     * charset without closing the stream.
     *
     * @throws IOException
     */
    public static String copyStreamToString(InputStream input) throws IOException {
        return copyStreamToString(input, input.available());
    }

    /**
     * Copy the data from an {@link InputStream} to a string using the default
     * charset.
     *
     * @param approxStringLength Used to preallocate a possibly correct sized
     * StringBulder to avoid an array copy.
     * @throws IOException
     */
    public static String copyStreamToString(InputStream input, int approxStringLength) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringWriter w = new StringWriter(Math.max(0, approxStringLength));
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];

        int charsRead;
        while ((charsRead = reader.read(buffer)) != -1) {
            w.write(buffer, 0, charsRead);
        }

        return w.toString();
    }

    /**
     * Close and ignore all errors.
     */
    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * A ByteArrayOutputStream which avoids copying of the byte array if not
     * necessary.
     */
    private static class OptimizedByteArrayOutputStream extends ByteArrayOutputStream {

        OptimizedByteArrayOutputStream(int initialSize) {
            super(initialSize);
        }

        @Override
        public synchronized byte[] toByteArray() {
            if (count == buf.length) {
                return buf;
            }
            return super.toByteArray();
        }
    }
}
