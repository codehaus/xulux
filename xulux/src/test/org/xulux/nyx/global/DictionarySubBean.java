/*
 $Id: DictionarySubBean.java,v 1.3 2003-07-16 15:40:38 mvdb Exp $

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */
package org.xulux.nyx.global;

/**
 * A "sub" base bean in DictioanryBean to test 
 * the Dictionary (mapping in specific..)
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionarySubBean.java,v 1.3 2003-07-16 15:40:38 mvdb Exp $
 */
public class DictionarySubBean extends DictionaryBaseBean
{
    
    private String phone;
    private String fax;
    private String mobile;
    private boolean nice;

    /**
     * Constructor for DictionarySubBean.
     */
    public DictionarySubBean()
    {
        super();
    }

    /**
     * Returns the fax.
     * @return String
     */
    public String getFax()
    {
        return fax;
    }

    /**
     * Returns the mobile.
     * @return String
     */
    public String getMobile()
    {
        return mobile;
    }

    /**
     * Returns the phone.
     * @return String
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Sets the fax.
     * @param fax The fax to set
     */
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    /**
     * Sets the mobile.
     * @param mobile The mobile to set
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    /**
     * Sets the phone.
     * @param phone The phone to set
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    /**
     * @return
     */
    public boolean isNice() {
        return nice;
    }

    /**
     * @param b
     */
    public void setNice(boolean b) {
        nice = b;
    }

}
