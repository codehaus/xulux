package org.xulux.gui.rules;

import org.xulux.core.PartRequest;
import org.xulux.rules.Rule;

/**
 *
 * @author Martin van den Bemt
 * @version $Id: DefaultButtonRule.java,v 1.2 2004-05-04 12:04:42 mvdb Exp $
 */
public class DefaultButtonRule extends Rule {

  /**
   * 
   */
  public DefaultButtonRule() {
    super();
  }

  /**
   * @see org.xulux.rules.IRule#pre(org.xulux.core.PartRequest)
   */
  public void pre(PartRequest request) {

  }

  /**
   * @see org.xulux.rules.IRule#post(org.xulux.core.PartRequest)
   */
  public void post(PartRequest request) {
    System.out.println("post rule.."+request.getWidget().getName());

  }

}
