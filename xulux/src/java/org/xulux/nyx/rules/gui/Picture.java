
/*
 $Id: Picture.java,v 1.1 2002-10-29 00:10:02 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.rules.gui;

import org.xulux.nyx.utils.NotYetSupportedException;

/**
 * A picture is a way to present a field to the use
 * eg the picture @s20 will show a field which can contain
 * 20 visible characters.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Picture.java,v 1.1 2002-10-29 00:10:02 mvdb Exp $
 */
public class Picture
{
    String pictureString;
    int length = -2;

    /**
     * Allow overrides..
     */
    public Picture()
    {
    }

    /**
     * Constructor for Picture.
     */
    public Picture(String picture)
    {
        setPictureString(picture);
    }

    public String toString()
    {
        return getPictureString();
    }

    /**
     * Returns the picture.
     * If not length can be figured out, it will use -1
     * to let the caller figure the length based on the current value
     * or some other fancy stuff.
     * @return String
     */
    public String getPictureString()
    {
        return pictureString;
    }

    public int getFieldLength()
    {
        if (this.length == -2)
        {
            if (pictureString.startsWith("@s"))
            {
                this.length =
                    Integer.valueOf(pictureString.substring(2)).intValue();
            }
            else
            {
                this.length = -1;
            }
        }

        return this.length;
    }

    /**
     * Sets the picture.
     * @param picture The picture to set
     * @throws NotYetSupportedException - when the picture is not yet supported
     */
    public void setPictureString(String pictureString)
    {
        if (!pictureString.startsWith("@"))
        {
            throw new NotYetSupportedException("Picture should start with the @ sign");
        }
        this.pictureString = pictureString;
    }

}
