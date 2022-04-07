package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HelpActions {
    

    protected static ArrayList<Action> actions;


    public HelpActions() {
        actions = new ArrayList<Action>();
        actions.add(new ShortcutsAction("Shortcuts", null, "Lists all shortcuts", Integer.valueOf(KeyEvent.VK_O)));

        


    }

    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Help");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class ShortcutsAction extends ImageAction {

        ShortcutsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl pressed H"));
        }

        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> l1 = new DefaultListModel<>();
            DefaultListModel<String> l2 = new DefaultListModel<>();
            l1.addElement("Help           ");
            l1.addElement("Open           ");  
            l1.addElement("Save           ");  
            l1.addElement("Save As        ");  
            l1.addElement("Export         ");
            l1.addElement("Exit           "); 
            l1.addElement("Undo           "); 
            l1.addElement("Redo           "); 
            l1.addElement("Zoom in        "); 
            l1.addElement("Zoom out       "); 
            l1.addElement("Zoom fit       "); 
            l1.addElement("Zoom full      "); 
            l1.addElement("Mean filter    "); 
            l1.addElement("Sharpen filter "); 
            l1.addElement("Gaussian filter"); 
            l1.addElement("median filter  "); 
            l1.addElement("greyscale      "); 
            l1.addElement("brightness     "); 
            l1.addElement("contrast       "); 
            l1.addElement("rotate 180     "); 
            l1.addElement("rotate left 90 "); 
            l1.addElement("rotate right 90"); 
            l1.addElement("flip horizontal"); 
            l1.addElement("flip vertical  "); 
            l1.addElement("scale 150%     "); 
            l1.addElement("scale 50%      ");
            
            l2.addElement("|                  CTRL + Shift + H"); 
            l2.addElement("|                  CTRL + O");  
            l2.addElement("|                  CTRL + S");  
            l2.addElement("|                  CTRL + Shift + S");  
            l2.addElement("|                  CTRL + E");
            l2.addElement("|                  CTRL + W"); 
            l2.addElement("|                  CTRL + Z"); 
            l2.addElement("|                  CTRL + Y"); 
            l2.addElement("|                  CTRL + ="); 
            l2.addElement("|                  CTRL + -"); 
            l2.addElement("|                  CTRL + /"); 
            l2.addElement("|                  CTRL + Shift + /"); 
            l2.addElement("|                  CTRL + M"); 
            l2.addElement("|                  CTRL + J"); 
            l2.addElement("|                  CTRL + G"); 
            l2.addElement("|                  CTRL + D"); 
            l2.addElement("|                  CTRL + A"); 
            l2.addElement("|                  CTRL + B"); 
            l2.addElement("|                  CTRL + C"); 
            l2.addElement("|                  CTRL + F"); 
            l2.addElement("|                  CTRL + I"); 
            l2.addElement("|                  CTRL + R"); 
            l2.addElement("|                  CTRL + H"); 
            l2.addElement("|                  CTRL + V"); 
            l2.addElement("|                  CTRL + ["); 
            l2.addElement("|                  CTRL + ]");    
            JList<String> list = new JList<>(l1);
            JList<String> list2 = new JList<>(l2);  
            JPanel panel = new JPanel(new BorderLayout());
            JPanel panel1 = new JPanel();
            panel1.add(list);
            JPanel panel2 = new JPanel();
            panel2.add(list2);
            JScrollPane scrollPane = new JScrollPane();
            
            list.setLayoutOrientation(JList.VERTICAL);
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);
            panel1.setBackground(Color.white);
            panel2.setBackground(Color.white);
            splitPane.setOneTouchExpandable(false);
            splitPane.setDividerSize(0);
            scrollPane.setViewportView(splitPane);
            
            panel.add(scrollPane);
            
            
            JFrame frame = new JFrame("ANDIE | Shortcuts");
            Image image;
            try {
                image = ImageIO.read(new File("./src/icon.png"));
                frame.setIconImage(image);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            frame.add(panel);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            

        }

    }

}
