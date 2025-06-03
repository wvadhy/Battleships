package org.example.util;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JSONHandler {

    /*
    Handler for JSON configuration files for Battleships.
     */

    public JSONObject load() {
        // Allows user to pick file from system directories, only accepts valid JSON files.
        try {
            JFileChooser fc = new JFileChooser();
            int x = fc.showOpenDialog(null);
            if (x == JFileChooser.APPROVE_OPTION) {
                String json = getJsonFrom(fc.getSelectedFile().getAbsolutePath());
                return new JSONObject(json);
            } else {
                System.out.println("Error");
                return null;
            }
        } catch (Exception err) {
            System.out.println(err.getCause());
            return null;
        }
    }

    private String getJsonFrom(String path) throws IOException {
        try (InputStream is = new FileInputStream(path)) {
            return IOUtils.toString(is, "UTF-8");
        }
    }

}
