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
 *
 * @author vmuser
 */
public class StreamGobbler extends Thread {

        InputStream is;
        String type;
        String logfile;

        public StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }
        
        public StreamGobbler(InputStream is, String type, Integer jobId) {
            this.is = is;
            this.type = type;
            this.logfile = Integer.toString(jobId);            
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                //java.io.File file = new java.io.File("/home/pitas/Deskargak/" + logfile + ".log");
                java.io.File file = new java.io.File("/home/vmuser/CPI/log/" + logfile + ".log");
                //java.io.PrintWriter outputfile = new java.io.PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                while ((line = br.readLine()) != null) {
                    java.io.PrintWriter outputfile = new java.io.PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                
                    System.out.println(type + "> " + line);
                    outputfile.println(type + "> " + line);
                    outputfile.close();
                }
                //outputfile.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
}

