/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etcc.csc.common.FileFormat;

/**
 * A variety of convenience methods for handling HTTP responses.
 * @author unknown
 * @author Milosh Boroyevich
 */
public class HttpResponseUtil {
    /**
     * Constructor.  Private, as should not be instantiated.
     */
    private HttpResponseUtil() {
        //end <init>
    }

    public static void copyStream(InputStream ins, OutputStream ops) throws IOException {
        byte[] buffer = new byte[64 * 1024]; //use 64K buffer.
        int count = -1;

        do {
            count = ins.read(buffer, 0, buffer.length);
            if (count > 0) {
                ops.write(buffer, 0, count);
            }
        } while (count >= 0);
        ops.flush();
    }

    public static String parseISToString(InputStream is) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }

        System.out.println(buffer);
        return buffer.toString();
    }

    /**
     * @deprecated use {@link #verifyContentType(URLConnection, FileFormat)}
     * @param connection
     * @param expectedContentType
     * @return
     */
    @Deprecated
    public static boolean verifyContentType( URLConnection connection, String expectedContentType )
    {
      return expectedContentType.equalsIgnoreCase( connection.getContentType( ) );
    }

    /**
     * Verifies the content type.
     * @param connection the URL connection object
     * @param expectedFormat the expected file content format
     * @return <tt>true</tt> if the connection content type matches the expected content type
     * @see FileFormat#isValidContentType(String)
     * @see URLConnection#getContentType()
     */
    public static boolean verifyContentType(URLConnection connection, FileFormat expectedFormat) {
        return expectedFormat.isValidContentType(connection.getContentType());
    }

    /**
     * Send the specified file resource to the HTTP response output stream.
     * @param response the HTTP response for streaming the resource file
     * @param context used for accessing the file resource
     * @param filename full path name (including extension) of the file resource ('/' is prepended
     * @param format file format for content output
     * @throws IOException if an I/O error occurs.
     * @see ServletContext#getResourceAsStream(String)
     * @see HttpServletResponse#getOutputStream()
     */
    // moved from com.etcc.csc.presentation.action.accountManagement.AccountUploadMultipleVehicleAction
    public static void streamFile(HttpServletResponse response, ServletContext context, String filename, FileFormat format) throws IOException {
        InputStream fileIn = context.getResourceAsStream('/'+filename);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(format.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        int i;
        while ((i = fileIn.read()) != -1) {
            out.write(i);
        }
        response.setContentLength(i);
        out.flush();
        out.close();
        fileIn.close();
    }

    /**
     * Send the specified bytes to the HTTP response output stream.
     * @param request the HTTP request to set attributes
     * @param response the HTTP response for streaming the bytes
     * @param inBytes binary representation of the output
     * @param filename just the name portion (extension is added automatically)
     * @param format file format for content output
     * @return Display string based on the file format to be used for action mapping (<tt>null</tt> if <tt>inBytes</tt> is zero-length)
     * @throws IOException if an I/O error occurs.
     * @see HttpServletResponse#getOutputStream()
     * @see FileFormat#getDisplay()
     */
    // consolidated from com.etcc.csc.presentation.action.accountManagement.AccountStatementsAction and all other report actions
    public static String streamBytes(HttpServletRequest request, HttpServletResponse response,
            byte[] inBytes, String filename, FileFormat format) throws IOException {
        if (inBytes.length > 0) {
            String ext = format.toString().toLowerCase();
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + '.' + ext);
            response.setContentType(format.getContentType());
            response.setContentLength(inBytes.length);

            request.setAttribute("byteArray", inBytes);
            request.setAttribute("pageSize", Integer.valueOf(0));
            request.setAttribute(format.getDisplay(), Boolean.TRUE);

            ServletOutputStream out = response.getOutputStream();
            out.write(inBytes, 0, inBytes.length);
            out.flush();
            out.close();

            return format.getDisplay();
        }
        return null;
    }
}
