/*
 $Id: ColorUtils.java,v 1.2 2003-07-10 22:40:22 mvdb Exp $

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
package org.xulux.nyx.gui.utils;

import java.util.Arrays;

/**
 * Color utils to make parsing easier
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ColorUtils.java,v 1.2 2003-07-10 22:40:22 mvdb Exp $
 */
public class ColorUtils
{

    /**
     * Constructor for ColorUtils.
     */
    public ColorUtils()
    {
    }
    
    /**
     * Returns an array with the rgb values.
     * The hex string can be six or three long
     * Six is the format AABBCC
     * Three is the format ABC, which resolves to AABBCC
     * If you pass in an invalid string, the rgb values will
     * all be zero
     * @return
     */
    public static int[] getRGBFromHex(String hex)
    {
        int result[] = new int[3];
        try
        {
        if (hex.length() == 6)
        {
            result[0] = Integer.parseInt(hex.substring(0,2),16);
            result[1] = Integer.parseInt(hex.substring(2,4),16);
            result[2] = Integer.parseInt(hex.substring(4,6),16);
        }
        else if (hex.length() == 3)
        {
            result[0] = Integer.parseInt(hex.substring(0,1)+hex.substring(0,1),16);
            result[1] = Integer.parseInt(hex.substring(1,2)+hex.substring(1,2),16);
            result[2] = Integer.parseInt(hex.substring(2,3)+hex.substring(2,3),16);
        }
        }
        catch(NumberFormatException nfe)
        {
            Arrays.fill(result, 0);
        }
        return result;
    }

}
