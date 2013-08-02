<%-- 
    Document   : test
    Created on : Sep 26, 2012, 1:54:26 PM
    Author     : Aakash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.*,java.io.*"%>               

<%
    String file = "ajay.zip";
    File f = new File("C:\\Users\\Aakash\\Desktop\\SquidRMI\\test\\" + file);
    response.setContentType("application/jar");
    response.setHeader("Content-Disposition", "attachment; filename=" + file);
    //String name = f.getName().substring(f.getName().lastIndexOf("\\") + 1, f.getName().length());
    InputStream in = new FileInputStream(f);
    ServletOutputStream outs = response.getOutputStream();

    int bit = 256;
    int i = 0;
    try {
        while ((bit) >= 0) {
            bit = in.read();
            outs.write(bit);
        }
    } catch (IOException ioe) {
        ioe.printStackTrace(System.out);
    }
    outs.flush();
    outs.close();
    in.close();
%>
