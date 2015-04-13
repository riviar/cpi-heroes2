/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Static class used to manipulate contents of the files
 *
 * @author Rafal Kural
 * @version 1.0
 */
public class FileEditor {

    /**
     * Private constructor that will not allows to create instance of this class
     */
    private FileEditor() {

    }

    /**
     * Removes css and injects link to new css file for FastQC output html file
     *
     * @param path
     */
    public static void editFastQCHTML(String path) {
        try {
            
            List<String> linesList = FileReader.readFileToLineList(path);
            String firstLine = linesList.get(0);
            String lastLine = linesList.get(linesList.size() - 1);
            
            //find index of unwanted part of first line
            int removeFrom = firstLine.indexOf("<style type=\"text/css\">");
            //copy rest of the line
            firstLine = firstLine.substring(0, removeFrom);
            //add new text at the end of line
            firstLine = firstLine.concat("<link href=\"../resources/css/fastqc_native_conversion_css.css\" rel=\"stylesheet\" type=\"text/css\" />");
            //save changes
            linesList.set(0, firstLine);
            
            lastLine = lastLine.substring(8);
            
            
            //build new contents and overwrite html file
            String newContents = firstLine + "\n" + lastLine;
            PrintWriter out = new PrintWriter(path);
            out.write(newContents);
            out.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("FastQC output file not found. Expected path: " + path);
        }
    }
}
