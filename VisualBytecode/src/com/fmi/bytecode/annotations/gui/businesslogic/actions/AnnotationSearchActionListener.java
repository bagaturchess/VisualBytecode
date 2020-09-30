package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.AnnotationsSearchDialog;
import com.fmi.bytecode.annotations.tool.tool.ReadResult;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationFilter;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationsIndex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import javax.swing.tree.TreeModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.ModelException;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;
import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.AnnotationFilterModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.NegativeAnnotationFilter;
import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.PositiveAnnotationFilter;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult.SearchResultUtils;

public class AnnotationSearchActionListener implements ActionListener {
    
    private AnnotationsSearchDialog dialog;
    
    public AnnotationSearchActionListener(AnnotationsSearchDialog dialog) {
        this.dialog = dialog;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Cancel")) {
            dialog.dispose();     
        } else if (command.equals("Add")) {
            try {
                dialog.addCurrentFilter();
            } catch (ModelException f) {
                JOptionPane.showMessageDialog(dialog, f.getMessage(),
                                             "Error message", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals("Remove")) {
            dialog.removeCurrentFilter();
        } else if (command.equals("Search")) {
            ProjectModel projectModel = dialog.getProjectModel();
            File contentFile = dialog.getContentFile();
            String packageName = dialog.getPackageName();
            
            List<AnnotationFilterModel> filtersModels
                                        = dialog.getAddedAnnotationsFilters();
            
            if (filtersModels.size() == 0) {
                JOptionPane.showMessageDialog(dialog, 
                             "There is no added annotation filters.\r\n " +
                             "Please add at least one before search.",
                             "Information", JOptionPane.INFORMATION_MESSAGE);

            } else {
                dialog.dispose();
                List<AnnotationFilter> filters = new ArrayList<AnnotationFilter>();
                for (AnnotationFilterModel filtersModel : filtersModels) {
                    if (filtersModel.isPositive()) {
                        filters.add(new PositiveAnnotationFilter(filtersModel));
                    } else {
                        filters.add(new NegativeAnnotationFilter(filtersModel));
                    }
                }
                
                Map<ProjectModel, Map<File, AnnotationsIndex>> annIndexes = 
                            new HashMap<ProjectModel, Map<File, AnnotationsIndex>>();
                
                if (projectModel == null) {
                    Set<ProjectModel> prjModels = ModelsRepository.getModelsRepository().getAllProjectsModels();
                    for (ProjectModel currPrj : prjModels) {
                        if (contentFile != null) {
                            throw new IllegalMonitorStateException("Node projects have not got definite content file " 
                                                                    + contentFile.getAbsolutePath());    
                        }
                        createProjectAndContentAnnotationIndexes(annIndexes, currPrj, 
                                                                 contentFile, packageName, filters);               
                    } 
                } else {
                    createProjectAndContentAnnotationIndexes(annIndexes, projectModel, 
                                                             contentFile, packageName, filters);
                }
                
                TreeModel tm = SearchResultUtils.createTree(annIndexes);
                GUIComponentsRepository.getComponentsRepository().getMainFrame().addSearchResultTreeModel(tm);
            }
        }
    }

    private void createProjectAndContentAnnotationIndexes(Map<ProjectModel, Map<File, AnnotationsIndex>> annIndexes,
                                                          ProjectModel projectModel, File contentFile, String packageName,
                                                          List<AnnotationFilter> filters) {
        ReadResult result = projectModel.getAnnotationResult();
        annIndexes.put(projectModel, new HashMap<File, AnnotationsIndex>()); 
        if (contentFile == null) {
            List<File> contentFiles = projectModel.getProjectContent();
            for (File currentContentFile : contentFiles) {
                AnnotationsIndex ai = result.getAnnotationsIndex(packageName, currentContentFile, filters);
                annIndexes.get(projectModel).put(currentContentFile, ai);
            }                        
        } else {
            AnnotationsIndex ai = result.getAnnotationsIndex(packageName, contentFile, filters);
            annIndexes.get(projectModel).put(contentFile, ai);
        }
    }
}
