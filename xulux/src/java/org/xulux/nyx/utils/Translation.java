/*
 $Id: Translation.java,v 1.1 2003-07-10 22:40:21 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
 package org.xulux.nyx.utils;

/**
 * The translation object is a holder of translation
 * information (url to get names from)
 * It defaults to classloader loading.
 * 
 * TODO: Automaticaly figure out what to do!
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Translation.java,v 1.1 2003-07-10 22:40:21 mvdb Exp $
 */
public class Translation {
    
    private String url;
    private String type;
    
    /**
     * 
     */
    public Translation() {
        super();
    }
    
    /**
     * Convenient constructor instead of calling 
     * the setters..
     * 
     * @param url
     * @param type
     */
    public Translation(String url, String type) {
        this();
        setUrl(url);
        setType(type);
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param string
     */
    public void setType(String string) {
        type = string;
    }

    /**
     * @param string
     */
    public void setUrl(String string) {
        url = string;
    }
    
    public String toString() {
        return this.url;
    }
    
    /**
     * Case sensitive comparison of urls.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        if (object instanceof Translation) {
            Translation trans = (Translation)object;
            if (getUrl() != null) {
                if (getUrl().equals(trans.getUrl())) {
                    if (getType() != null) {
                        return getType().equals(trans.getType());
                    }else {
                        return trans.getType()==null?true:false;
                    } 
                }
            }
        }
        return false;
    }

}
