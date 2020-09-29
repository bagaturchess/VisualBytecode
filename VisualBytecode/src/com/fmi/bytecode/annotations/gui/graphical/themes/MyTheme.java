package com.fmi.bytecode.annotations.gui.graphical.themes;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This class describes a theme using "primary" colors.
 * You can change the colors to anything else you want.
 *
 * 1.9 07/26/04
 */
public class MyTheme extends DefaultMetalTheme {

    /*public String getName() { return "Toms"; }

    private static final ColorUIResource primary1 = new ColorUIResource(
                              102, 102, 153);
    private static final ColorUIResource primary2 = new ColorUIResource(
                              153, 153, 204);
    private static final ColorUIResource primary3 = new ColorUIResource(
                              204, 204, 255);
    /*private static final ColorUIResource secondary1 = new ColorUIResource(
                              102, 102, 102);
    private static final ColorUIResource secondary2 = new ColorUIResource(
                              153, 153, 153);
    private static final ColorUIResource secondary3 = new ColorUIResource(
                              204, 204, 204);*/
     
     
     //Menu Borders
     private static final ColorUIResource primary1 = new ColorUIResource(
                               102, 102, 100);
                               
     //Menu Selections, scroll bars
     private static final ColorUIResource primary2 = new ColorUIResource(
                               190, 210, 240);
                               
     //Tree items
     private static final ColorUIResource primary3 = new ColorUIResource(
                               200, 220, 245);
    
     //Panes, Frames border
     private static final ColorUIResource secondary1 = new ColorUIResource(
                               120, 140, 200);
     
     //Click ...
     private static final ColorUIResource secondary2 = new ColorUIResource(
                               153, 153, 153);
                               
     //Frame header
     private static final ColorUIResource secondary3 = new ColorUIResource(
                              // 230, 245, 255);
                               240, 240, 255);
                              
                               
    // these are blue in Metal Default Theme
    protected ColorUIResource getPrimary1() { return primary1; } 
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }

    // these are gray in Metal Default Theme
    protected ColorUIResource getSecondary1() { return secondary1; }
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
}


