package org.xulux.swing.widgets;

import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.xulux.core.ApplicationPart;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IDataProvider;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.converters.IntegerConverter;

import junit.framework.TestCase;

/**
 * Testcase for the entry widget.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: EntryTest.java,v 1.6 2005-01-20 15:58:00 mvdb Exp $
 */
public class EntryTest extends TestCase {

    /**
     * Constructor for EntryTest.
     * @param name the name of the test
     */
    public EntryTest(String name) {
        super(name);
    }

    /**
     * Test the valueclass that is set using
     * an override of setProperty
     */
    public void testValueClass() {
        System.out.println("testValueClass");
        Entry entry = new Entry("entry");
        assertEquals(null, entry.valueClass);
        entry.setProperty("VALUEClass", Integer.class);
        assertEquals(Integer.class.toString(), entry.getProperty("VALUEClass"));
        entry.setProperty("valueclass", null);
        assertEquals(null, entry.valueClass);
        assertEquals(null, entry.getProperty("valueclass"));
        entry.setProperty("valueclass", Double.class.getName());
        assertEquals(Double.class.getName(), entry.getProperty("valueclass"));
        assertEquals(Double.class, entry.valueClass);
        // does it doe a getClass on unknown objects.
        entry.setProperty("valueclass", new Integer(0));
        assertEquals(Integer.class, entry.valueClass);
        // is it accepting nulls
        entry.setProperty(null, null);
        // is passing on properties it shouldn't handle
        entry.setProperty("test", "test");
        assertEquals("test", entry.getProperty("test"));
        entry.destroy();
    }    

    /**
     * Test the setValue method in the entry.
     * This should be externalized later on.
     */
    public void testSetAndGetValue() {
        System.out.println("testSetAndGetValue");
        Entry entry = new Entry("entry");
        assertNull(entry.getValue());
        entry.setValue(null);
        assertNull(entry.getValue());
        entry.setValue("value");
        assertEquals("value", entry.getValue());
        entry.setProperty("valueClass", Integer.class);
        assertEquals(entry.valueClass, Integer.class);
        assertEquals("value", entry.getValue());
        entry.setValue("1");
        assertEquals("1", entry.getValue());
        Dictionary.addConverter(IntegerConverter.class);
        entry.setValue("1");
        assertEquals(new Integer(1), entry.getValue());
        entry.destroy();
    }

    public void testSetAndGetValueWithField() {
        System.out.println("testSetAndGetValueWithField");
        Entry entry = new Entry("entry");
        entry.setField("field");
        assertEquals("field", entry.getField());
    }
    
    public void testClear() {
        System.out.println("testClear");
        ApplicationPart part = new ApplicationPart();
        Entry entry = new Entry("entry");
        entry.setPart(part);
        entry.setValue("value");
        //assertEquals("value", entry.getGuiValue());
        assertEquals("value", entry.getValue());
        entry.clear();
        assertEquals("", entry.getGuiValue());
        assertEquals(null, entry.getValue());
        entry = new Entry("entry");
        entry.setPart(part);
        entry.setValue("value");
        entry.initialize();
        assertEquals("value", entry.getGuiValue());
        assertEquals("value", entry.getValue());
        assertEquals(false, entry.isValueEmpty());
        entry.clear();
        assertEquals("", entry.getGuiValue());
        assertEquals(null, entry.getValue());
        entry.destroy();
        assertEquals(true, entry.isValueEmpty());
    }

    public void testGetNativeWidget() {
        System.out.println("testGetNativeWidget");
        ApplicationPart part = new ApplicationPart();
        Entry entry = new Entry("entry");
        part.addWidget(entry);
        entry.initialize();
        assertEquals(true, entry.getNativeWidget() instanceof JComponent);
        assertEquals(false, entry.isInitializing());
        entry.destroy();
        entry = new Entry("entry");
        entry.setPart(part);
        assertEquals(true, entry.getNativeWidget() instanceof JComponent);
    }
    
    public void testDefaults() {
        System.out.println("testDefaults");
        Entry entry = new Entry("entry");
        assertEquals(true, entry.canContainValue());
        assertEquals(false, entry.canContainChildren());
        entry.addNyxListener(null);
    }
    /**
     * Test the usage of a provider 
     */
    public void testProvider() {
        System.out.println("testProvider");
        Entry entry = new Entry("entry");
        assertEquals(null, entry.getProvider());
        entry.setProvider("test");
        assertEquals("test", entry.getProvider());
        TestProvider provider = new TestProvider();
        XuluxContext.getDictionary().registerProvider("test", provider);
        TestObject object = new TestObject("testobject");
        IField field = new TestProvider().getMapping(null).getField(null);
        assertEquals("testobject", field.getValue(object));
        entry.setField("name");
        entry.setValue(new TestObject("newvalue"));
        entry.setValue("newvalue");
        assertEquals(TestObject.class, entry.getValue().getClass());
    }
    
    /**
     * Test the converter stuff in the entry..
     */
    public void testConvert() {
        System.out.println("testConverter");
        Entry entry = new Entry("entry");
        ApplicationPart part = new ApplicationPart();
        part.addWidget(entry);
        entry.setLazyProperty("converter.class", "org.xulux.swing.widgets.MockConverter");
        entry.refresh();
        entry.setValue(MockConverter.BEANVALUE);
        assertEquals(MockConverter.GUIVALUE, entry.textComponent.getText());
        assertEquals(MockConverter.BEANVALUE, entry.getValue());
        assertEquals(MockConverter.GUIVALUE, entry.getGuiValue());
        entry.setLazyProperty("valueclass", TestObject.class.getName());
        entry.setValue(new TestObject("karel"));
        assertEquals(true, entry.getValue() instanceof TestObject);
        assertEquals(MockConverter.GUIVALUE, entry.getGuiValue());
        entry.setValue("martin");
        assertEquals(MockConverter.BEANVALUE, entry.getValue());
    }

    /**
     * The test data provider can handle the TestObject
     */
    public class TestObject {
      
      String name;
      
      public TestObject(String name) {
          this.name = name;
      }
      /**
       * @return
       */
      public String getName() {
        return name;
      }

      /**
       * @param string
       */
      public void setName(String string) {
        name = string;
      }

    }
    
    public class TestField implements IField {
        public String getName() {
          return null;
        }
  
        public String getAlias() {
          return null;
        }
  
        public Object setValue(Object object, Object value) {
          if (object == null) {
            object = new TestObject(null);
          }
          ((TestObject)object).setName((String) value);
          return object;
        }
  
        public Object getValue(Object object) {
          if (object instanceof String) {
              return new TestObject((String) object);
          }
          return ((TestObject)object).getName();
        }
  
        public boolean isReadOnly() {
          return false;
        }
  
        public Class getType() {
          return null;
        }
    }
    
    public class TestMapping implements IMapping {
        public void addField(IField field) {
  
        }
  
        public List getFields() {
            return null;
        }
  
        public IField getField(Object object) {
            return new TestField();
        }
  
        public String getName() {
            return "getName";
        }

        /**
         * @see org.xulux.dataprovider.IMapping#setValue(java.lang.String, java.lang.Object, java.lang.Object)
         */
        public Object setValue(String field, Object object, Object value) {
          return null;
        }

        /**
         * @see org.xulux.dataprovider.IMapping#getValue(java.lang.String, java.lang.Object)
         */
        public Object getValue(String field, Object object) {
          return null;
        }
    }
    /**
     * A dataprovider for testing purposes.
     * It just handles testObjects though
     */
    public class TestProvider implements IDataProvider {
        /**
         * @see org.xulux.dataprovider.IDataProvider#getMappings()
         */
        public Map getMappings() {
          return null;
        }
  
        /**
         * @see org.xulux.dataprovider.IDataProvider#getMapping(java.lang.Object)
         */
        public IMapping getMapping(Object object) {
          return new TestMapping();
        }
  
        /**
         * @see org.xulux.dataprovider.IDataProvider#setProperty(java.lang.String, java.lang.String)
         */
        public void setProperty(String key, String value) {
        }
  
        /**
         * @see org.xulux.dataprovider.IDataProvider#initialize()
         */
        public void initialize() {
        }
  
        /**
         * @see org.xulux.dataprovider.IDataProvider#addMapping(org.xulux.dataprovider.IMapping)
         */
        public void addMapping(IMapping mapping) {
        }

        /**
         * @see org.xulux.dataprovider.IDataProvider#setValue(java.lang.Object, java.lang.String, java.lang.Object, java.lang.Object)
         */
        public Object setValue(Object mapping, String field, Object object, Object value) {
          return null;
        }

        /**
         * @see org.xulux.dataprovider.IDataProvider#getValue(java.lang.Object, java.lang.String, java.lang.Object)
         */
        public Object getValue(Object mapping, String field, Object object) {
          return null;
        }

        /**
         * @see org.xulux.dataprovider.IDataProvider#needsPartValue()
         */
        public boolean needsPartValue() {
          return false;
        }
    }
}
