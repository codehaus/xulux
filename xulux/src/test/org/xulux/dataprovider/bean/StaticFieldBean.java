package org.xulux.dataprovider.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: StaticFieldBean.java,v 1.1 2004-10-20 17:28:59 mvdb Exp $
 */
public class StaticFieldBean {
  
  /**
   * @return the content
   */
  public static List getContent() {
    List list = new ArrayList();
    list.add(new StaticFieldBean().new Person("Martin van den Bemt"));
    list.add(new StaticFieldBean().new Person("John Doe"));
    list.add(new StaticFieldBean().new Person("Jane Doe"));
    return list;
  }

  /**
   * The person object
   */
  public class Person {
    String name;
    public Person(String name) {
      this.name = name;
    }
    
    public String getName() {
      return this.name;
    }
    
  }
}
