/*
   $Id: EverythingListener.java,v 1.3 2004-01-28 15:09:24 mvdb Exp $
   
   Copyright 2002-2004 The Xulux Project

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.xulux.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.xulux.gui.NyxListener;
import org.xulux.swing.util.NyxEventQueue;

/**
 * Listens to everything.. Making life easy when debugging.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: EverythingListener.java,v 1.3 2004-01-28 15:09:24 mvdb Exp $
 */
public class EverythingListener
    extends NyxListener
    implements
        MouseListener,
        ActionListener,
        AncestorListener,
        ComponentListener,
        ContainerListener,
        FocusListener,
        HierarchyBoundsListener,
        HierarchyListener,
        InputMethodListener,
        ItemListener,
        KeyListener,
        MouseMotionListener,
        PropertyChangeListener,
        VetoableChangeListener {

    /**
     *
     */
    public EverythingListener() {
        super();
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        // free up the eventqueue when tab is pressed..
        NyxEventQueue q = NyxEventQueue.getInstance();
        q.holdEvents(false);
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {

    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {

    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println("combo actionPerformed : " + e);
    }

    /**
     * @see javax.swing.event.AncestorListener#ancestorAdded(javax.swing.event.AncestorEvent)
     */
    public void ancestorAdded(AncestorEvent event) {
        System.out.println("combo ancestorAdded: " + event);
    }

    /**
     * @see javax.swing.event.AncestorListener#ancestorMoved(javax.swing.event.AncestorEvent)
     */
    public void ancestorMoved(AncestorEvent event) {
        System.out.println("combo ancestorMoved: " + event);
    }

    /**
     * @see javax.swing.event.AncestorListener#ancestorRemoved(javax.swing.event.AncestorEvent)
     */
    public void ancestorRemoved(AncestorEvent event) {
        System.out.println("combo ancestorRemoved: " + event);
    }

    /**
     * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
     */
    public void componentHidden(ComponentEvent e) {
        System.out.println("combo componentHidden: " + e);
    }

    /**
     * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
     */
    public void componentMoved(ComponentEvent e) {
        System.out.println("combo componentMoved: " + e);
    }

    /**
     * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
     */
    public void componentResized(ComponentEvent e) {
        System.out.println("combo componentResized: " + e);

    }

    /**
     * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
     */
    public void componentShown(ComponentEvent e) {
        System.out.println("combo componentShown: " + e);
    }

    /**
     * @see java.awt.event.ContainerListener#componentAdded(java.awt.event.ContainerEvent)
     */
    public void componentAdded(ContainerEvent e) {
        System.out.println("combo componentAdded: " + e);
    }

    /**
     * @see java.awt.event.ContainerListener#componentRemoved(java.awt.event.ContainerEvent)
     */
    public void componentRemoved(ContainerEvent e) {
        System.out.println("combo componentRemoved: " + e);
    }

    /**
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    public void focusGained(FocusEvent e) {
        System.out.println("combo focusGained: " + e);
    }

    /**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    public void focusLost(FocusEvent e) {
        System.out.println("combo focusLost: " + e);
    }

    /**
     * @see java.awt.event.HierarchyBoundsListener#ancestorMoved(java.awt.event.HierarchyEvent)
     */
    public void ancestorMoved(HierarchyEvent e) {
        System.out.println("combo ancestorMoved : " + e);
    }

    /**
     * @see java.awt.event.HierarchyBoundsListener#ancestorResized(java.awt.event.HierarchyEvent)
     */
    public void ancestorResized(HierarchyEvent e) {
        System.out.println("combo ancesterResized: " + e);
    }

    /**
     * @see java.awt.event.HierarchyListener#hierarchyChanged(java.awt.event.HierarchyEvent)
     */
    public void hierarchyChanged(HierarchyEvent e) {
        System.out.println("combo hierarchyChanged: " + e);
    }

    /**
     * @see java.awt.event.InputMethodListener#caretPositionChanged(java.awt.event.InputMethodEvent)
     */
    public void caretPositionChanged(InputMethodEvent event) {
        System.out.println("combo caretPositionChanged: " + event);
    }

    /**
     * @see java.awt.event.InputMethodListener#inputMethodTextChanged(java.awt.event.InputMethodEvent)
     */
    public void inputMethodTextChanged(InputMethodEvent event) {
        System.out.println("combo inputMethodTextChanged: " + event);
    }

    /**
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent e) {
        System.out.println("combo itemStateChanged: " + e);
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        System.out.println("combo keyPressed: " + e);
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        System.out.println("combo keyReleased: " + e);
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        System.out.println("combo keyTyped: " + e);
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        System.out.println("combo mouseDragged: " + e);
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        System.out.println("combo mouseMoved: " + e);
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("combo propertyChange: " + evt);
    }

    /**
     * @see java.beans.VetoableChangeListener#vetoableChange(java.beans.PropertyChangeEvent)
     */
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        System.out.println("combo vetoableChange: " + evt);
    }

}
