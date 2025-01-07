/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    private String fileName;

    public FileManager() {

    }

    // ----------------------------------------------------------
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    // ----------------------------------------------------------
    public List<String> readDataFromFile() throws IOException {
        List<String> result;
        result = Files.readAllLines(new File(fileName).toPath(), Charset.forName("utf-8"));
        return result;
    }

    // ----------------------------------------------------------
    public void saveDataToFile(String data) throws IOException {
        Files.writeString(Paths.get(fileName), data, Charset.forName("utf-8"));
    }
    // ----------------------------------------------------------

}
