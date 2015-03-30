/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outputview;

import toolstuff.util.ETool;

/**
 * Generic output entity class
 * Output entity allows to generate it in specific way on output page
 * @author Fox
 */
public class GenericOutput {
    private String name;
    private EOutputType outputType;
    private String path;
    /**
     * Tool that generated this output
     */
    private ETool tool;

    public EOutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(EOutputType outputType) {
        this.outputType = outputType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ETool getTool() {
        return tool;
    }

    public void setTool(ETool tool) {
        this.tool = tool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public GenericOutput(String name, EOutputType outputType,
            String path, ETool tool) {
        this.name = name;
        this.outputType = outputType;
        this.path = path;
        this.tool = tool;
    }
}
