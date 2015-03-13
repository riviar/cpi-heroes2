/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

/**
 *
 * @author Fox
 */
public class FastQCJob extends AbstractJob {
    
    public FastQCJob(String inputPath) {
        getParameters().put("input", inputPath);
        setExecutableFile("fastqc");
    }
    
    @Override
    public void execute() {
        
    }
    
}
