/*
$Id: StringUtils.java,v 1.2 2004-10-18 14:10:47 mvdb Exp $

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

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: StringUtils.java,v 1.2 2004-10-18 14:10:47 mvdb Exp $
 */
public class StringUtils {
  
  /**
   * Replace the specified string with the specified char
   *
   * @param value the value to do the magic on
   * @param replace the value to replace
   * @param c the char to replace it with
   * @return a new string with replacements
   */
  public static String replace(String value, String replace,  char c) {
    if (value.indexOf(replace) == -1) {
      return value;
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < value.length(); i++) {
      boolean replaceIt = false;
      for (int j = 0; j < replace.length(); j++) {
        if (value.charAt(i+j) == replace.charAt(j)) {
          replaceIt = true;
        } else {
          replaceIt = false;
          break;
        }
      }
      if (replaceIt) {
        sb.append(c);
        i+=replace.length()-1;
      } else {
        sb.append(value.charAt(i));
      }
    }
    return sb.toString();
  }

  /**
   * Capitalizes a string, which means the first character
   * will be uppercase and the other characters will be lowercase.
   * @param constraint
   */
  public static String capitalize(String constraint) {
    if (constraint == null || constraint.length() == 0) {
      return constraint;
    }
    constraint = constraint.toLowerCase();
    StringBuffer buffer = new StringBuffer();
    buffer.append(Character.toUpperCase(constraint.charAt(0)));
    buffer.append(constraint.substring(1));
    return buffer.toString();
  }

}
