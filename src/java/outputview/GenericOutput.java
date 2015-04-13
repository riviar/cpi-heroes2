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
 * @author Rafal Kural
 * @version 1.0
 */
public class GenericOutput {
    /**
     * Output name
     */
    private String name;
    /**
     * Output type enum
     */
    private EOutputType outputType;
    /**
     * Path to output file
     */
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
    
    /**
     * Creates output object
     * @param name output name
     * @param outputType output type enum
     * @param path path to output file
     * @param tool enum of tool used to generate this output
     */
    public GenericOutput(String name, EOutputType outputType,
            String path, ETool tool) {
        this.name = name;
        this.outputType = outputType;
        this.path = path;
        this.tool = tool;
    }
}
