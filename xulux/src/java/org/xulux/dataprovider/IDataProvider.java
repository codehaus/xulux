/*
   $Id: IDataProvider.java,v 1.5 2004-10-05 10:11:06 mvdb Exp $
   
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
package org.xulux.dataprovider;

import java.util.Map;

/**
 * The dataprovider interface.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IDataProvider.java,v 1.5 2004-10-05 10:11:06 mvdb Exp $
 */
public interface IDataProvider {

    /**
     * @return the current mappings (in cache) that are known to the dataprovider
     */
    Map getMappings();
    
    /**
     * @param object the object. The dataprovider should determine what it accepts.
     * @return the mapping for the specified object
     */
    IMapping getMapping(Object object);

    /**
     * Sets the properties of the driver.
     * All elements in the properties element in the dictionary xml.
     * eg.
     * <pre>
     * &lt;properties&gt;
     *   &lt;bean&gt;org.xulux.dataprovider.ParameteredBean&lt;/bean&gt;
     *    &lt;fields&gt;
     *      &lt;field name="value"/&gt;
     *    &lt;/fields&gt;
     * &lt;/properties&gt;
     * </pre>
     *
     * This xml will result in calls to setProperty like this :
     * setProperty("bean", "org.xulux.dataprovider.ParameteredBean");
     * setProperty("fields.field.name", "value");
     *
     * @param key the key
     * @param value the value
     */
    void setProperty(String key, String value);

    /**
     * After processing the dictionary xml files (so if all properties are set
     * initialize is called to interpret the properties.
     */
    void initialize();

    /**
     * Add a mapping to the dataprovider
     * @param mapping
     */
    void addMapping(IMapping mapping);
    
  /**
   * A convenience method, so you can set values on the highest possible level
   *
   * @param mapping the mapping to use (could be a string or the current part / field value)
   * @param field the field name to use
   * @param object the object to use. If null the provider will try to create the object
   * @param value the value to set in the object
   * @return the object in the changed form, or if object was null, the newly created object.
   *         if an error occured of somekind, it should be logged and the value null should be
   *         returned.
   */
  Object setValue(Object mapping, String field, Object object, Object value);

  /**
   * A convenience method, so you can get values on the highest possible level
   *
   * @param mapping the mapping to use (could be a string or the current part / field value)
   * @param field the field name to use
   * @param object the object to use. If null the provider will try to create the object
   * @return the object, or nul when an error occurred or the value is null.
   */
  Object getValue(Object mapping, String field, Object object);
  
  /**
   * @return Specifies if the dataprovider needs the partvalue to function correctly
   */
  boolean needsPartValue();
  
}
