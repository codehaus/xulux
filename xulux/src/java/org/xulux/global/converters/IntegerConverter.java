/*
 $Id: IntegerConverter.java,v 1.1 2003-12-18 00:17:20 mvdb Exp $

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
package org.xulux.global.converters;

import org.xulux.global.IConverter;

/**
 * Converts an Integer to a String or an String to an Integer
 * It will return null when none of the above is possible.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IntegerConverter.java,v 1.1 2003-12-18 00:17:20 mvdb Exp $
 */
public class IntegerConverter implements IConverter {

    /**
     *
     */
    public IntegerConverter() {
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getBeanValue(java.lang.Object)
     */
    public Object getBeanValue(Object object) {
        if (object instanceof String) {
            try {
                return new Integer((String) object);
            } catch (NumberFormatException nfe) {
                return null;
            }
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getGuiValue(java.lang.Object)
     */
    public Object getGuiValue(Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).toString();
        }
        return null;
    }
    /**
     * @see org.xulux.nyx.global.IConverter#getType()
     */
    public Class getType() {
        return Integer.class;
    }

}
