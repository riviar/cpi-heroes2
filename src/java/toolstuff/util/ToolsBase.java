/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains information about all tools available in the system (should!)
 *
 * @author Fox
 */
public class ToolsBase {

    private List<Tool> preprocessingTools;
    private List<Tool> assemblyTools;
    private List<Tool> annotationTools;
    private List<Tool> downsteamTools;

    public ToolsBase() {
        preprocessingTools = new ArrayList();
        assemblyTools = new ArrayList();
        annotationTools = new ArrayList();
        downsteamTools = new ArrayList();
    }

    /**
     * Add list of tools and sorts them according to types
     * @param tools 
     */
    public void addTools(List<Tool> tools) {
        for (Tool tool:tools) {
            addTools(tool);
        }
    }
    
    /**
     * Adds tools to correct list according to its type
     * @param tool 
     */
    public void addTools(Tool tool) {
        switch (tool.getToolType()) {
            case PREPROCESSING:
                preprocessingTools.add(tool);
                break;
            case ASSEMBLY:
                assemblyTools.add(tool);
                break;
            case ANNOTATION:
                annotationTools.add(tool);
                break;
            case DOWNSTREAM:
                downsteamTools.add(tool);
                break;
        }
    }
    
    /**
     * Returns list of names for tools of specified type
     * @param type
     * @return 
     */
    public List<String> getToolsNames(EToolType type) {
        List<String> names = new ArrayList();
        switch (type) {
            case PREPROCESSING:
                for (Tool tool:preprocessingTools) {
                    names.add(tool.getName());
                }
                break;
            case ASSEMBLY:
                for (Tool tool:assemblyTools) {
                    names.add(tool.getName());
                }
                break;
            case ANNOTATION:
                for (Tool tool:annotationTools) {
                    names.add(tool.getName());
                }
                break;
            case DOWNSTREAM:
                for (Tool tool:downsteamTools) {
                    names.add(tool.getName());
                }
                break;
        }
        return names;
    }
    
    /**
     * Return tool based on its name
     * @param name
     * @return 
     */
    public Tool getToolByName(String name) {
        for (Tool tool:preprocessingTools)
        {
            if (tool.getName().contains(name)) {
                return tool;
            }
        }
        for (Tool tool:assemblyTools)
        {
            if (tool.getName().contains(name)) {
                return tool;
            }
        }
        for (Tool tool:annotationTools)
        {
            if (tool.getName().contains(name)) {
                return tool;
            }
        }
        for (Tool tool:downsteamTools)
        {
            if (tool.getName().contains(name)) {
                return tool;
            }
        }
        return null;
    }
    
}
