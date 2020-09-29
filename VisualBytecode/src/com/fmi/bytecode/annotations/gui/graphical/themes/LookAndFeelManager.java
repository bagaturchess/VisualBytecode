package com.fmi.bytecode.annotations.gui.graphical.themes;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.synth.SynthLookAndFeel;


public class LookAndFeelManager {

    /**
     * Metal
     * CDE/Motif
     * Windows
     */
    
    /**
     * com.sun.java.swing.plaf.gtk.GTKLookAndFeel
     * com.sun.java.swing.plaf.motif.MotifLookAndFeel
     * com.sun.java.swing.plaf.windows.WindowsLookAndFeel
     * javax.swing.plaf.metal.MetalLookAndFeel
     */
    
    /**
     * System
     * Metal -> themes=[DefaultMetal, Ocean]
     * Motif
     * GTK
     */
     
    private static String LOOKANDFEEL = "System";

    private static String THEME = "Ocean";

    public static void initLookAndFeel() {
    
        //if (true) return;
            
            String lookAndFeel = null;
           
            if (LOOKANDFEEL != null) {
                if (LOOKANDFEEL.equals("Metal")) {
                    lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                  //  an alternative way to set the Metal L&F is to replace the 
                  // previous line with:
                  // lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
                    
                }
                
                else if (LOOKANDFEEL.equals("System")) {
                    lookAndFeel = UIManager.getSystemLookAndFeelClassName();
                } 
                
                else if (LOOKANDFEEL.equals("Motif")) {
                    lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                } 
                
                else if (LOOKANDFEEL.equals("GTK")) { 
                    lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                } 
                
                else {
                    System.err.println("Unexpected value of LOOKANDFEEL specified: "
                                       + LOOKANDFEEL);
                    lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                }

                try {
                    UIManager.setLookAndFeel(lookAndFeel);
                    
                    // If L&F = "Metal", set the theme
                    
                    if (LOOKANDFEEL.equals("Metal")) {
                      if (THEME.equals("DefaultMetal"))
                         MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                      else if (THEME.equals("Ocean"))
                         MetalLookAndFeel.setCurrentTheme(new OceanTheme());
                      else
                         MetalLookAndFeel.setCurrentTheme(new MyTheme());
                                            
                      UIManager.setLookAndFeel(new MetalLookAndFeel()); 
                    }       
                            
                } 
                
                catch (ClassNotFoundException e) {
                    System.err.println("Couldn't find class for specified look and feel:"
                                       + lookAndFeel);
                    System.err.println("Did you include the L&F library in the class path?");
                    System.err.println("Using the default look and feel.");
                } 
                
                catch (UnsupportedLookAndFeelException e) {
                    System.err.println("Can't use the specified look and feel ("
                                       + lookAndFeel
                                       + ") on this platform.");
                    System.err.println("Using the default look and feel.");
                } 
                
                catch (Exception e) {
                    System.err.println("Couldn't get specified look and feel ("
                                       + lookAndFeel
                                       + "), for some reason.");
                    System.err.println("Using the default look and feel.");
                    e.printStackTrace();
                }
            }
        }

}
