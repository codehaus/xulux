/*
$Id: StringUtilsTest.java,v 1.2 2004-10-18 14:10:47 mvdb Exp $

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
package org.xulux.utils;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: StringUtilsTest.java,v 1.2 2004-10-18 14:10:47 mvdb Exp $
 */
public class StringUtilsTest extends TestCase {

  /**
   * Constructor for StringUtilsTest.
   * @param name the name of the test
   */
  public StringUtilsTest(String name) {
    super(name);
  }
  
  public void testReplaceStringWithChar() {
    System.out.println("testReplaceStringWithChar");
    assertEquals("hi", StringUtils.replace("hi", "karel", '\n'));
    assertEquals("hi\n", StringUtils.replace("hi\\n", "\\n", '\n'));
    assertEquals("line1\nline2", StringUtils.replace("line1\\nline2", "\\n", '\n'));
  }
  
  public void testCapitalize() {
    System.out.println("testCapitalize");
    assertEquals("Hi", StringUtils.capitalize("hi"));
    assertEquals("Hi", StringUtils.capitalize("HI"));
    assertEquals("Hi", StringUtils.capitalize("Hi"));
    assertEquals("Hi", StringUtils.capitalize("hI"));
    assertEquals("H", StringUtils.capitalize("h"));
    assertEquals(null, StringUtils.capitalize(null));
    assertEquals("", StringUtils.capitalize(""));
  }

}
