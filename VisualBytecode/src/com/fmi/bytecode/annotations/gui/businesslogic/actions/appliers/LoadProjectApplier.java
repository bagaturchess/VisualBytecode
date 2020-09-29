package com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import java.io.File;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import javax.swing.JTree;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;
import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;
import com.fmi.bytecode.annotations.gui.businesslogic.model.ModelException;

import com.fmi.bytecode.annotations.gui.utils.ModelUtils;

public class LoadProjectApplier implements SelectedFileValueApplier {
    
    private JTree tree;
    
    public LoadProjectApplier(JTree tree) {
        this.tree = tree;
    }

    public void apply(File selectedFile) throws Exception {
        ProjectModel projectModel = ModelUtils.loadProjectModel(selectedFile);
        String fileName = selectedFile.getName();
        String projectName = projectModel.getProjectName() + ProjectModel.FILE_EXTENSION ;
        if (!fileName.equals(projectName)) {
            throw new ModelException("The project file name is not equal to <project-name>" + ProjectModel.FILE_EXTENSION +"\n Project file name is " + fileName
            + " but project name is " + projectModel.getProjectName());
        }
        
        warningIfProblems(projectModel);
                                      
        //add project Model to tree data
        ModelsRepository.getModelsRepository().addProjectModel(projectModel);
        
        //Load project model in GUI
        tree.updateUI();
    }

    private void warningIfProblems(ProjectModel projectModel) {
        String[] warnings = projectModel.getAnnotationResult().getProcessingProblems();
        
        if (warnings != null && warnings.length > 0) {
            //List warningsList = Arrays.asList(warnings);
            //System.out.println("PROBLEMS: " + warningsList);
            String message = "There are warnings which will overwrite the enumerated classes meta information: \r\n";
            for (int i = 0; i < warnings.length; i++) {
                message += "Warning " + (i + 1) + ": " + cut(warnings[i], 50) + "\r\n";
            }
            
            JOptionPane.showMessageDialog(GUIComponentsRepository.getComponentsRepository().getMainFrame(), 
                                          message,
                                          "Warnings", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String cut(String string, int length) {
        StringTokenizer tokens = new StringTokenizer(string, " ");
        int countSymbols = 0;
        String result = "";
        
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            countSymbols += token.length();
            result += token + " ";
            if (countSymbols > length) {
                result += "\r\n";
                countSymbols = 0;
            }   
        }
        return result;   
    }
}
