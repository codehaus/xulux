/*
   $Id: NyxCombo.java,v 1.13 2004-11-30 14:28:01 mvdb Exp $
   
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
package org.xulux.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.IDataProvider;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.InvalidValueException;
import org.xulux.gui.events.NyxValueChangedEvent;
import org.xulux.utils.ClassLoaderUtils;
import org.xulux.utils.NyxCollectionUtils;

/**
 * The combo abstract. This will contain the combo generics
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxCombo.java,v 1.13 2004-11-30 14:28:01 mvdb Exp $
 */
public abstract class NyxCombo extends Widget implements IContentWidget {

    /**
     * the content
     */
    protected List content;
    /**
     * the notselected value
     */
    protected String notSelectedValue;
    /**
     * Is the content changed ?
     */
    protected boolean contentChanged;

    /**
     * Is the notselected value set
     */
    protected boolean notSelectedValueSet;
    
    /**
     * The log instance
     */
    protected static Log log = LogFactory.getLog(NyxCombo.class);

    /**
     * Constructor for NyxCombo.
     * @param name the name of the combo
     */
    public NyxCombo(String name) {
        super(name);
    }

    /**
     * This just disposes of references to combo
     * and doesn't get rid of native resource
     * allocated. You have to override this
     * and call super for this to work
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        getPart().removeWidget(this, this);
        removeAllRules();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public abstract Object getNativeWidget();

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public abstract void initialize();

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public abstract void refresh();

    /**
     * Sets the content of the combo.
     * The content should be all of the same type
     * If the content is a basetype, fields can be specified
     * to say which one is used, in any other situation, toString is used
     * to represent a field.
     * @param object the content
     */
    public void setContent(Object object) {
        if (object instanceof List) {
            this.content = (List) object;
            if (getNotSelectedValue() != null) {
                this.content.add(0, getNotSelectedValue());
            }
            contentChanged = true;
        }
        if (initialized) {
            refresh();
        }
    }

    /**
     * Initializes the content when the property
     * content and content.type is present
     */
    protected void initializeContent() {
        String content = getProperty("content");
        String contentType = getProperty("content.type");
        if (content == null || contentType == null) {
            return;
        }
        if (contentType.equalsIgnoreCase("string")) {
            this.content = NyxCollectionUtils.getListFromCSV(content);
        } else if (contentType.equalsIgnoreCase("field")) {
            int index = content.lastIndexOf(".");
            Class clz = ClassLoaderUtils.getClass(content.substring(0, index));
            if (clz == null) {
                if (log.isWarnEnabled()) {
                    log.warn("content field " + content + " of widget " + getName() + " could not be found");
                }
            }
            IMapping mapping = XuluxContext.getDictionary().getDefaultProvider().getMapping(clz);
            if (mapping != null) {
                IField field = mapping.getField(content.substring(index + 1));
                if (field != null) {
                    this.content = NyxCollectionUtils.getList(field.getValue(null));
                }
            }
        }
    }

    /**
     * Initializes the not selected value.
     */
    protected void initializeNotSelectedValue() {
        String nsv = getNotSelectedValue();
        if (nsv == null) {
            if (notSelectedValueSet) {
                this.content.remove(0);
                notSelectedValueSet = false;
                contentChanged = true;
            }
            return;
        }
        if (this.content == null || content.size() == 0) {
            if (this.content == null) {
                this.content = new ArrayList();
            }
            this.content.add(nsv);
        } else {
            boolean replaceFirstEntry = false;
            if (notSelectedValueSet) {
                Object firstValue = content.get(0);
                if (firstValue instanceof String) {
                    if (firstValue.equals(getNotSelectedValue())) {
                        return;
                    }
                    replaceFirstEntry = true;
                }
            }
            if (replaceFirstEntry) {
                content.set(0, nsv);
            } else {
                List tmpContent = this.content;
                content = new ArrayList();
                content.add(nsv);
                content.addAll(tmpContent);
            }
        }
        this.notSelectedValue = nsv;
        this.notSelectedValueSet = true;
        this.contentChanged = true;
    }
    /**
     * Sets the not selected value.
     * It will always be the first one in the list..
     * Does nothing when value is null
     * @param notSelectedValue the text representing the notselected text.
     */
    public void setNotSelectedValue(String notSelectedValue) {
        if (notSelectedValueSet) {
            // we don't want to do anything when
            // the not selected value is the same
            if (this.notSelectedValue.equals(notSelectedValue)) {
                return;
            }
        }
        this.notSelectedValue = notSelectedValue;
        contentChanged = true;
        initializeNotSelectedValue();
        if (initialized) {
            refresh();
        }
    }

    /**
     * @return the notSelectedValue
     */
    public String getNotSelectedValue() {
        return this.notSelectedValue;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        if (getProvider() != null) {
            IDataProvider dp = XuluxContext.getDictionary().getProvider(getProvider());
            IMapping mapping = dp.getMapping(this.value);
            IField field = null;
            if (getField() != null) {
              field = mapping.getField(getField());
            } else {
              if (mapping != null) {
                field = mapping.getField(this.value);
              }
            }
            if (field != null) {
              return field.getValue(this.value);
            } else {
              return null;
            }
        } else if (getField() == null) {
            if (this.value != null && getNotSelectedValue() != null && this.value.equals(getNotSelectedValue())) {
                return null;
            }
            return this.value;
        } else {
            IMapping map = XuluxContext.getDictionary().getMapping(getPart().getBean());
            if (map == null) {
                return this.value;
            }
            IField field = map.getField(getField());
            if (field != null) {
                return field.getValue(getPart().getBean());
            }
            return this.value;
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#setValue(java.lang.Object)
     */
    public void setValue(Object object) {
        setValue(object, true);
    }
    protected boolean settingValue = false;
    /**
     * @todo Provide some tests to prove if this stuff actually works!
     * @param object the value to set
     * @param refresh should the widget be refreshed?
     */
    public void setValue(Object object, boolean refresh) {
        settingValue = true;
        if (isUseIgnored()) {
            this.value = object;
            notifyListeners(new NyxValueChangedEvent() {

                public Object getValue() {
                    return NyxCombo.this.value;
                }

                public Widget getWidget() {
                    return NyxCombo.this;
                }
            });
        }
        if (getProvider() != null) {
            if (!(object instanceof String) && this.value == null) {
              this.value = object;
            } else {
              IDataProvider pr = XuluxContext.getDictionary().getProvider(getProvider());
              IMapping mapping = pr.getMapping(this.value);
              IField field = null;
              if (getField() != null) {
                  field = mapping.getField(getField());
              } else {
                  field = mapping.getField(this.value);
              }
              try {
                this.value = field.setValue(this.value, object);
                setValidValue(true);
              } catch(InvalidValueException ive) {
                setValidValue(false);
              }
            }
        } else if (getField() == null) {
            this.previousValue = this.value;
            this.value = object;
        } else {
            IMapping map = XuluxContext.getDictionary().getMapping(getPart().getBean());
            if (map != null) {
	            IField field = map.getField(getField());
	            if (field != null) {
	                Object currentValue = field.getValue(getPart().getBean());
	                if (currentValue != null) {
	                    this.previousValue = currentValue;
	                }
	                field.setValue(getPart().getBean(), object);
	                this.value = object;
	            }
            }
            if (this.value == null) {
                this.value = object;
            }
        }
        settingValue = false;
        if (initialized && refresh) {
            refresh();
        }
    }

    /**
     * Sets the value without updating the screen
     * Normally only called by the classes that handle
     * the combo box, like combo listeners.
     * @param object the value
     */
    public void setLazyValue(Object object) {
        setValue(object, false);
    }

    /**
     * @see org.xulux.gui.Widget#clear()
     */
    public void clear() {
        setValue(null);
        if (initialized) {
            refresh();
        }
    }

    /**
     * Returns the content.
     * @return ArrayList
     */
    public Object getContent() {
        if (contentChanged) {
          initializeContent();
        }
        return content;
    }

    /**
     * @see org.xulux.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.gui.Widget#focus()
     */
    public void focus() {

    }

    /**
     * @see org.xulux.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }

    /**
     * 
     * @see org.xulux.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return false;
    }
    
    /**
     * We need to keep track of content changes
     * through the property system.
     * @see org.xulux.gui.Widget#setProperty(java.lang.String, java.lang.Object)
     */
    public void setProperty(String key, Object value) {
        if (key != null) {
            if (key.startsWith("content")) {
                contentChanged = true;
            }
        }
        super.setProperty(key, value);
    }

}
