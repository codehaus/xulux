/*
 $Id: FormFieldArea.java,v 1.2.2.1 2003-04-29 16:52:45 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.
 
 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.
 
 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.
 
 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project.  For written permission,
    please contact martin@mvdb.net.
 
 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.
 
 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).
 
 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */
package org.xulux.nyx.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.swing.factories.FieldCollection;
import org.xulux.nyx.swing.factories.FieldFactory;
import org.xulux.nyx.swing.factories.GuiField;

/**
 * The area where the fields of the form reside
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: FormFieldArea.java,v 1.2.2.1 2003-04-29 16:52:45 mvdb Exp $
 */
public class FormFieldArea extends JPanel
{

    private FieldCollection fields;

    /**
     * Constructor for FormFieldArea.
     * @param layout
     * @param isDoubleBuffered
     */
    public FormFieldArea(LayoutManager layout, boolean isDoubleBuffered)
    {
        super(layout, isDoubleBuffered);
    }

    /**
     * Constructor for FormFieldArea.
     * @param layout
     */
    public FormFieldArea(LayoutManager layout)
    {
        super(layout);
    }

    /**
     * Constructor for FormFieldArea.
     * @param isDoubleBuffered
     */
    public FormFieldArea(boolean isDoubleBuffered)
    {
        super(isDoubleBuffered);
    }

    /**
     * Constructor for FormFieldArea.
     */
    public FormFieldArea()
    {
//        FormLayout layout = new FormLayout();
        GridLayout layout = new GridLayout();
        setLayout(layout);
    }

    public FormFieldArea(FieldCollection fields, boolean processInnerForms)
    {
        this();
        setFields(fields, processInnerForms);
    }

    /**
     * Removes all fields from the area
     */
    public void removeAllFields()
    {
        Component[] children = getComponents();
        for (int i = 0; i < children.length; i++)
        {
            if (!(children[i] instanceof JRootPane))
            {
                remove(children[i]);
            }
        }
    }

    /**
     * Returns the fields.
     * @return FieldCollection
     */
    public FieldCollection getFields()
    {
        return fields;
    }

    /**
     * Sets the fields.
     * @param fields The fields to set
     */
    public void setFields(FieldCollection fields, boolean processInnerForms)
    {
        this.fields = fields;
        processFields(processInnerForms);
    }

    /**
     * Process the passed in fields and adds it to the component
     */
    protected void processFields(boolean processInnerForms)
    {
        Iterator it = fields.getFields().iterator();
        while (it.hasNext())
        {
            GuiField guiField = (GuiField) it.next();
            if (guiField.isBaseType() && !processInnerForms)
            {
                continue;
            }
            if (guiField.isBaseType() && processInnerForms)
            {
                // we need another formarea added to this form area..
                System.out.println("co : "+guiField.getCurrentObject(fields.getBase()).getClass());
                FieldCollection fCollection =
                    new FieldCollection(
                        Dictionary.getInstance().getMapping(
                            guiField.getFieldClass()),
                        guiField.getCurrentObject(fields.getBase()));
                System.out.println("fields : "+fCollection.getFields());
                FormFieldArea area = new FormFieldArea(fCollection, true);
                System.out.println("area layout : "+area.getLayout().getClass().getName());
                //System.exit(0);
                // surround the new area with a border..
                area.setBorder(
                    BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.white),
                        guiField.getLabel()));
                 add(area);
            }
            else
            {
                JComponent field =
                    FieldFactory.getField(guiField, fields.getBase());
                add(field);
            }
            GridLayout layout = (GridLayout) getLayout();
            layout.setRows(layout.getRows() + 1);
        }
    }
}
