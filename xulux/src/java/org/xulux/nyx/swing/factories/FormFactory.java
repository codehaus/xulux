package org.xulux.nyx.swing.factories;

import java.util.StringTokenizer;

import org.xulux.nyx.guidefaults.GuiDefaults;
import org.xulux.nyx.swing.BaseForm;
import org.xulux.nyx.swing.SimpleForm;
import org.xulux.nyx.examples.datamodel.DefaultBase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.utils.Resources;

/**
 * Returns a form based on the specified model
 * The form will be constructed based on the 
 * design guidelines
 * 
 * @author Martin van den Bemt
 * @version $Id; $
 */
public class FormFactory
{

    private static Log log = LogFactory.getLog(FormFactory.class);
    private static FormFactory instance = null;
    public static String DEFAULT_FORM_CONTROLS = "default.form.controls"; //$NON-NLS-1$
    public static String FIELDS = "fields"; //$NON-NLS-1$
    public static String DOT = "."; //$NON-NLS-1$

    /**
     * Constructor for FormFactory.
     * 
     */
    private FormFactory()
    {
    }

    public static synchronized BaseForm getForm(
        DefaultBase base,
        String formName)
    {
        return getForm(base, formName, GuiDefaults.class);
    }

    public static synchronized BaseForm getForm(
        DefaultBase base,
        String formName,
        Class clazz)
    {
        String fieldNames =
            Resources.getResource(clazz, formName + DOT + FIELDS);
        if (log.isTraceEnabled())
        {
            log.trace("fieldNames : "+fieldNames);
        }
        StringTokenizer stn = new StringTokenizer(fieldNames, ",");
        FieldCollection fc = new FieldCollection();
        fc.setBase(base);
        while (stn.hasMoreTokens())
        {
            String fieldName = stn.nextToken();
            GuiField field = new GuiField(formName, fieldName, clazz);
            if (log.isTraceEnabled())
            {
                log.trace("Adding field "+field.getField());
            }
            fc.addField(field);
        }
        SimpleForm form = new SimpleForm(fc);
        return form;
    }

    /**
     * returns the singleton instance of the formFactory
     * Mainly used for internal factory use.
     * @return the instance of formFactory
     */
    public static FormFactory getInstance()
    {
        if (instance == null)
        {
            instance = new FormFactory();
        }
        return instance;
    }

}
