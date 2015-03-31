/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that allows retrieving contents of files
 * @author Rafal Kural
 */
public final class FileReader {
    
    /**
     * Prevent instantiating class to simulate static class
     */
    private FileReader() {
        
    }
    
    /**
     * Reads file indicated by chosen filepath and return its raw contents
     * @param filepath path to the file to be read
     * @return raw contents of the file as string
     * @throws FileNotFoundException there is no file under specified filepath
     */
    public static String readFile(String filepath) throws FileNotFoundException {
        StringBuilder stringBuffer = new StringBuilder();
        File file = new File(filepath);
        Scanner fileStream = new Scanner(file);
        
        while (fileStream.hasNext()) {
            stringBuffer.append(fileStream.nextLine()).append("\n");
        }
        
        fileStream.close();
        
        return stringBuffer.toString();
    }
}
