package com.gint.app.bisis.circ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EscapeDialog extends JDialog implements ContainerListener, KeyListener{
  private JButton btn = null;

  static Class containerClass;
  static {
    try{
      containerClass = Class.forName("java.awt.Container");
    }catch(ClassNotFoundException e){
      containerClass = null;
    }
  }

  public EscapeDialog (Frame frame, String title, boolean modal){
    super(frame, title, modal);
    if(containerClass != null){
      addContainerListener(this);
      addKeyListener(this);
    }
  };


  public void componentAdded(ContainerEvent e){
    addKeyAndContainerListenerRecursively(e.getChild());
  };

  public void componentRemoved(ContainerEvent e){
    removeKeyAndContainerListenerRecursively(e.getChild());
  };

  public void keyPressed(KeyEvent e){
    int code = e.getKeyCode();
    if(code == KeyEvent.VK_ESCAPE)
      setVisible(false);
    else if(code == KeyEvent.VK_ENTER)
      performEnterAction(e);
  };

  public void keyReleased(KeyEvent e){
  };

  public void keyTyped(KeyEvent e){
  };

  public void performEnterAction(KeyEvent e){
    if(btn!=null) btn.doClick();
  };

  public void registerButton(JButton b){
    btn = b;
  }

  public void releaseButton(){
    btn = null;
  }

  private void addKeyAndContainerListenerRecursively(Component c){
    c.addKeyListener(this);
    if(containerClass.isAssignableFrom(c.getClass())){
      Container cont = (Container) c;
      cont.addContainerListener(this);
      Component [] children = cont.getComponents();
      for(int i = 0; i < children.length; i++)
         addKeyAndContainerListenerRecursively(children[i]);
    }
  };
  
  private void removeKeyAndContainerListenerRecursively(Component c){
    c.removeKeyListener(this);
    if(containerClass.isAssignableFrom(c.getClass())){
      Container cont = (Container) c;
      cont.removeContainerListener(this);
      Component [] children = cont.getComponents();
      for(int i = 0; i < children.length; i++)
         removeKeyAndContainerListenerRecursively(children[i]);
    }
  };

}
