/*
$Id: MouseDoubleClickTreeListener.java,v 1.1 2005-01-10 18:16:33 mvdb Exp $

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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.xulux.core.PartRequest;
import org.xulux.gui.Widget;
import org.xulux.rules.Rule;
import org.xulux.rules.impl.WidgetRequestImpl;
import org.xulux.utils.ClassLoaderUtils;

/**
 * 
 */
public class MouseDoubleClickTreeListener implements MouseListener {

	Widget widget = null;
	/**
	 * 
	 */
	public MouseDoubleClickTreeListener(Widget widget) {
		this.widget = widget;
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			processDoubleClick();
		}
	}
	
	public void processDoubleClick() {
		String strRule = widget.getProperty("doubleclick");
		Rule rule = (Rule) ClassLoaderUtils.getObjectFromClassString(strRule);
		rule.setOwner(widget);
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.NO_ACTION);
        rule.post(impl);
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
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
	}

}
