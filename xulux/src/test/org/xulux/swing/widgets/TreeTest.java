/*
   $Id: TreeTest.java,v 1.2 2004-07-14 15:05:31 mvdb Exp $
   
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
package org.xulux.swing.widgets;

import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeTest.java,v 1.2 2004-07-14 15:05:31 mvdb Exp $
 */
public class TreeTest extends TestCase {

    /**
     * Constructor for TreeTest.
     * @param name the name of the test
     */
    public TreeTest(String name) {
        super(name);
    }
    
    public void testProperties() {
        System.out.println("testProperties");
        Tree tree = new Tree("tree");
        tree.initialize();
        assertEquals(true, tree.jtree.isRootVisible());
        tree.setProperty("showroot", "false");
        assertEquals(false, tree.jtree.isRootVisible());
    }
}
