/*
 $Id: RadioButtonBean.java,v 1.1 2003-12-18 00:17:29 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project. For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.gui.swing.widgets;

/**
 * An example bean used to show / test the usage of the radio button.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RadioButtonBean.java,v 1.1 2003-12-18 00:17:29 mvdb Exp $
 */
public class RadioButtonBean {

    /**
     * bar
     */
    private boolean bar;
    /**
     * foo
     */
    private boolean foo;
    /**
     * group
     */
    private boolean group;
    /**
     * male
     */
    private boolean male;
    /**
     * female
     */
    private boolean female;

    /**
     *
     */
    public RadioButtonBean() {
        super();
    }

    /**
     * @return boolean
     */
    public boolean isBar() {
        return bar;
    }

    /**
     * @return boolean
     */
    public boolean isFemale() {
        return female;
    }

    /**
     * @return boolean
     */
    public boolean isFoo() {
        return foo;
    }

    /**
     * @return boolean
     */
    public boolean isGroup() {
        return group;
    }

    /**
     * @return boolean
     */
    public boolean isMale() {
        return male;
    }

    /**
     * @param b boolean
     */
    public void setBar(boolean b) {
        bar = b;
    }

    /**
     * @param b boolean
     */
    public void setFemale(boolean b) {
        female = b;
    }

    /**
     * @param b boolean
     */
    public void setFoo(boolean b) {
        foo = b;
    }

    /**
     * @param b boolean
     */
    public void setGroup(boolean b) {
        group = b;
    }

    /**
     * @param b boolean
     */
    public void setMale(boolean b) {
        male = b;
    }

}
