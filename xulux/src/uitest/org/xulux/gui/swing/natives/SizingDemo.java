package org.xulux.gui.swing.natives;

import java.io.InputStream;

import org.xulux.core.ApplicationPart;
import org.xulux.guidriver.XuluxGuiDriver;

/**
 * @author Martin van den Bemt
 * @version $Id: SizingDemo.java,v 1.1 2004-10-19 13:46:54 mvdb Exp $
 */
public class SizingDemo {

  public void start() {
    InputStream is = getClass().getClassLoader().getResourceAsStream("org/xulux/gui/swing/natives/SizingDemo.xml");
    XuluxGuiDriver driver = new XuluxGuiDriver();
    ApplicationPart part = driver.read(is, null);
    part.activate();
//    JFrame frame = new JFrame();
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.getContentPane().setLayout(new AutoSizeLayoutManager(null));
////    JPanel panel = new JPanel();
////    frame.getContentPane().add(panel);
//    JButton button = new JButton("I am a button");
////    panel.setLayout(new AutoSizeLayoutManager());
//    button.setPreferredSize(new Dimension(300,300));
//    frame.getContentPane().add(button);
//    frame.setVisible(true);
//    frame.pack();
  }
  
  public static void main(String[] args) {
    new SizingDemo().start();
  }
}
