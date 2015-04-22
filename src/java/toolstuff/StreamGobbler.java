/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Handles the standard and error output of the command line processes to redirect them into the Glassfish server log and the job log file.
 * @author Asier Gonzalez
 */
public class StreamGobbler extends Thread {

        /**
         * The process output stream
         */
        InputStream is;
        
        /**
         * Stream type.
         * <p><code>OUTPUT</code> for the standard output and <code>ERROR</code> for errors
         */
        String type;
        
        /**
         * Name of the file where the log is stored in <i>/home/vmuser/CPI/log/</i> 
         */
        String logfile;

        /**
         * Gets the input stream from the process 
         * @param is The process output stream
         * @param type Stream type
         */
        public StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }
        
        /**
         * 
         * @param is The process output stream
         * @param type Stream type
         * @param jobId Job number in the database that names the log file 
         */
        public StreamGobbler(InputStream is, String type, Integer jobId) {
            this.is = is;
            this.type = type;
            this.logfile = Integer.toString(jobId);            
        }

        /**
         * Launches the stream handling thread
         */
        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                java.io.File file = new java.io.File("/home/vmuser/CPI/log/" + logfile + ".log");
                while ((line = br.readLine()) != null) {
                    java.io.PrintWriter outputfile = new java.io.PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                
                    System.out.println(type + "> " + line);
                    outputfile.println(type + "> " + line);
                    outputfile.close();
                }
                
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
}

