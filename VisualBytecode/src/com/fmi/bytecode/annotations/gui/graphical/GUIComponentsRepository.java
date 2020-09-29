package com.fmi.bytecode.annotations.gui.graphical;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.awt.Event;
import java.awt.event.KeyEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.fmi.bytecode.annotations.gui.utils.ResourceUtils;

public class GUIComponentsRepository {
    
    private static GUIComponentsRepository componentsRepository;
    private MainFrame mainFrame;
    
    public static String IMAGE_DIR = "/com/fmi/bytecode/annotations/gui/graphical/images/";
    public static ImageIcon IMAGE_OPEN = loadImage("open.gif");
    public static ImageIcon IMAGE_EXIT = loadImage("exit.png");
    public static ImageIcon IMAGE_HELP = loadImage("help.gif");
    public static ImageIcon IMAGE_NEW  = loadImage("new.png");
    public static ImageIcon IMAGE_SAVE = loadImage("save.png");
    public static ImageIcon IMAGE_ABOUT = loadImage("about.gif");
    
    public static ImageIcon FIND_ANNOTATIONS = loadImage("find.png");
    public static ImageIcon REMOVE_FILE = loadImage("close.gif"); 
    public static ImageIcon PROJECT_PROPS = loadImage("properties.gif");
    public static ImageIcon CLOSE_ALL = loadImage("closeall.gif");
    public static ImageIcon CLOSE_MENU = loadImage("closemenu.png");
    public static ImageIcon CLOSE_TAB = loadImage("closetab.gif");
    public static ImageIcon PROJECT_TAB = loadImage("project.gif");
    public static ImageIcon PROJECT_LOGO = loadImage("dino.png");
    public static ImageIcon XML_EXPORT = new ImageIcon(loadImage("test.png").getImage().getScaledInstance(17, 17, java.awt.Image.SCALE_AREA_AVERAGING));
    
    public static KeyStroke SK_CTRL_F4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.CTRL_MASK, false);
    public static KeyStroke SK_CTRL_F = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK, false);
    public static KeyStroke SK_CTRL_N = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK, false);
    public static KeyStroke SK_CTRL_O = KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK, false);
    public static KeyStroke SK_CTRL_SHIFT_F4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.SHIFT_MASK | Event.CTRL_MASK, false);
    public static KeyStroke SK_ALT_F4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK, false);
    public static KeyStroke SK_CTRL_S = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK, false);
    public static KeyStroke SK_CTRL_P = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK, false);
    public static KeyStroke SK_F1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false);
    public static KeyStroke SK_CTRL_E = KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK, false);
    
    private GUIComponentsRepository() {
    }

    public static GUIComponentsRepository getComponentsRepository() {
        if (componentsRepository == null) {
            componentsRepository = new GUIComponentsRepository();
        }
        return componentsRepository;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
    
    public static ImageIcon loadImage(String imageName) {
        String imagePath = IMAGE_DIR + imageName;
        byte[] imageData = ResourceUtils.readResourceData(imagePath);
        return new ImageIcon(imageData);    
    }
}
