/*
   $Id: IDictionary.java,v 1.1 2004-03-23 08:42:22 mvdb Exp $
   
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

/**
 * The Dictionary interface. Use this to implement your own
 * dictionary format. For now this is empty.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IDictionary.java,v 1.1 2004-03-23 08:42:22 mvdb Exp $
 */
public interface IDictionary {
    
    /**
     * Clears all the mappings currently available
     */
    void clearMappings();
    
    /**
     * Initialises the dictionary from the specified object
     *
     * @param object
     */
    void initialize(Object object);
}
