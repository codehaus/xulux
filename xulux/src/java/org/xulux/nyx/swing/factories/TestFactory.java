
package org.xulux.nyx.swing.factories;

import org.xulux.nyx.global.BeanField;
import org.xulux.nyx.swing.BaseForm;
import org.xulux.nyx.swing.NewForm;

/**
 * A testFactory, which just contains some experimenting code, to end up with the
 * "ideal" situation. Will be deleted / moved to examples or test in
 * a later stage.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TestFactory.java,v 1.1 2002-10-31 01:44:26 mvdb Exp $
 */
public class TestFactory
{

    private static TestFactory instance;
    /**
     * Constructor for TestFactory.
     */
    public TestFactory()
    {
    }
    
    public static TestFactory getInstance()
    {
        if (instance == null)
        {
            instance = new TestFactory();
        }
        return instance;
    }
    
    
    public static NewForm getForm(String form, Object bean)
    {
        GuiField field = new GuiField(new BeanField());
        //field.get
        return null;
    }
            

}
