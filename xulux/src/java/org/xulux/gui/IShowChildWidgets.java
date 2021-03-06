/*
   $Id: IShowChildWidgets.java,v 1.2 2004-01-28 15:00:22 mvdb Exp $
   
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
package org.xulux.gui;

/**
 * This is an empty interface for container widgets.
 * The purpose is to be able to determine that a widget cannot
 * be shown itself and that the container widget should
 * request the children of the widget to make them show.
 * I came across this issue when using buttongroups..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IShowChildWidgets.java,v 1.2 2004-01-28 15:00:22 mvdb Exp $
 */
public interface IShowChildWidgets {

}
