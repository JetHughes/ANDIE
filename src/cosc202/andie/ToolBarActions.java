package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

 /**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ToolBarActions {
    
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public ToolBarActions() {
        actions = new ArrayList<Action>();
        actions.add(new zoom("zoom", null, "zoom", Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new zoom("test1", null, "zoom1", Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new zoom("test2", null, "zoom2", Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new zoom("test3", null, "zoom3", Integer.valueOf(KeyEvent.VK_Z)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JToolBar createMenu() {
        JToolBar editMenu = new JToolBar(null, JToolBar.VERTICAL);

        for (Action action: actions) {
            editMenu.add(new JButton(action));
            editMenu.setAlignmentX(1);
        }
        editMenu.setFloatable(false);
        return editMenu;
    }

    public class zoom extends ImageAction{
        
        zoom(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            System.out.println("ZOOOOOOOOM");
            target.getImage();
            target.repaint();
            target.getParent().revalidate();
        }
    }

}
