package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.StaticTextNode;


public class ResultNode extends StaticTextNode implements SavableNode {
    
    public ResultNode(String name) {
        super(null, name);
    }

    public List<SaveUnit> getUnits() {
        List<SaveUnit> saveUnits = new ArrayList<SaveUnit>();
         
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            LevelNode content = (LevelNode) this.getChildAt(i);
            saveUnits.addAll(content.getUnits());                             
         }
         return saveUnits;
    }
}
