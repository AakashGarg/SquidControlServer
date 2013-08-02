/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.controller;

/**
 *
 * @author aakash
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utilities {

    public String streamToStream(InputStream is, OutputStream os, int buff) {
        int read = 0;
        byte[] bytes = new byte[buff];
        if (is != null) {
            try {
                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                os.flush();
                os.close();
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        return "Success";
    }
}
