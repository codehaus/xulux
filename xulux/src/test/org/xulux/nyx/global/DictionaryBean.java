/*
 $Id: DictionaryBean.java,v 1.2.2.2 2003-05-04 15:27:41 mvdb Exp $

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
 * DictionaryBean for testing the dictionary
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionaryBean.java,v 1.2.2.2 2003-05-04 15:27:41 mvdb Exp $
 */
public class DictionaryBean extends DictionaryBaseBean
{
    
    private String name;
    private String street;
    private String city;
    private String ignorePrivate;
    private String ignoreProtected;
    private DictionarySubSubBean subBean;
    
    /**
     * Constructor for DictionaryBean.
     */
    public DictionaryBean()
    {
        super();
    }

    /**
     * Returns the city.
     * @return String
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the street.
     * @return String
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * Sets the city.
     * @param city The city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the street.
     * @param street The street to set
     */
    public void setStreet(String street)
    {
        this.street = street;
    }

    /**
     * Returns the ignore.
     * @return String
     */
    private String getIgnorePrivate()
    {
        return ignorePrivate;
    }

    /**
     * Returns the ignoreProtected.
     * @return String
     */
    protected String getIgnoreProtected()
    {
        return ignoreProtected;
    }

    /**
     * Sets the ignorePrivate.
     * @param ignorePrivate The ignorePrivate to set
     */
    public void setIgnorePrivate(String ignorePrivate)
    {
        this.ignorePrivate = ignorePrivate;
    }

    /**
     * Sets the ignoreProtected.
     * @param ignoreProtected The ignoreProtected to set
     */
    public void setIgnoreProtected(String ignoreProtected)
    {
        this.ignoreProtected = ignoreProtected;
    }

    /**
     * Returns the subBean.
     * @return DictionarySubBean
     */
    public DictionarySubSubBean getSubBean()
    {
        return subBean;
    }

    /**
     * Sets the subBean.
     * @param subBean The subBean to set
     */
    public void setSubBean(DictionarySubSubBean subBean)
    {
        this.subBean = subBean;
    }

}
