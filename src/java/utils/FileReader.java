/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that allows retrieving contents of files
 * @author Rafal Kural
 * @version 1.0
 */
public final class FileReader {
    
    /**
     * Prevent instantiating class to simulate static class
     */
    private FileReader() {
        
    }
    
    /**
     * Reads file indicated by chosen filepath and return chosen number of lines
     * @param filepath path to the file to be read
     * @param numberOfLines number of lines to return
     * @return raw contents of the file as string
     * @throws FileNotFoundException there is no file under specified filepath
     */
    public static String readFile(String filepath, int numberOfLines) throws FileNotFoundException {
        StringBuilder stringBuffer = new StringBuilder();
        int i = 0;
        File file = new File(filepath);
        Scanner fileStream = new Scanner(file);
        
        while (fileStream.hasNext() && (i < numberOfLines)) {
            stringBuffer.append(fileStream.nextLine()).append("<br/>");
            i++;
        }
        
        fileStream.close();
        
        return stringBuffer.toString();
    }
    
    /**
     * Reads file indicated by chosen filepath and list of lines
     * @param filepath path to the file to be read
     * @return raw contents of the file as string
     * @throws FileNotFoundException there is no file under specified filepath
     */
    public static List<String> readFileToLineList(String filepath) throws FileNotFoundException {
        
        List<String> lineList = new ArrayList();
        File file = new File(filepath);
        Scanner fileStream = new Scanner(file);
        
        while (fileStream.hasNext()) {
            lineList.add(fileStream.nextLine());
        }
        
        fileStream.close();
        
        return lineList;
    }
}
