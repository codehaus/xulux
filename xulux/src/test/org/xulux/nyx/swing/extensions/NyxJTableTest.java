/*
 $Id: NyxJTableTest.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $

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
package org.xulux.nyx.swing.extensions;

import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.swing.models.NyxTableColumnModel;
import org.xulux.nyx.swing.models.NyxTableModel;

/**
 * Test the nyxJTable.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJTableTest.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $
 */
public class NyxJTableTest extends TestCase {

    /**
     * Constructor for NyxJTableTest.
     * @param name the name of the test
     */
    public NyxJTableTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxJTableTest.class);
        return suite;
    }

    /**
     * Test Constructors
     */
    public void testConstructors() {
        System.out.println("testConstructors");
        // just check if there are any exceptions
        NyxJTable table = new NyxJTable();
        NyxTableModel model = new NyxTableModel();
        NyxTableColumnModel columnModel = new NyxTableColumnModel();
        table = new NyxJTable(model, columnModel);
        assertEquals(model, table.getModel());
        assertEquals(columnModel, table.getColumnModel());
    }

    /**
     * The table changecount
     */
    private static int changeCount = 0;

    /**
     * Test the sibling tables.
     */
    public void testSiblingTables() {
        System.out.println("testSiblingTables");
        final NyxJTable table = new NyxJTable();
        assertNull(table.getSiblingTable());
        final NyxJTable sibling = new NyxJTable();
        table.setSiblingTable(sibling);
        sibling.setSiblingTable(table);
        assertEquals(table.getSiblingTable(), sibling);
        assertEquals(sibling.getSiblingTable(), table);
        assertFalse(table.isChanging());
        assertFalse(sibling.isChanging());
        //final int changeCount = 0;
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (changeCount == 1) {
                    assertTrue(table.isChanging());
                    assertFalse(sibling.isChanging());
                }
                changeCount++;
            }
        });
        table.changeSelection(0, 10, false, false);
        changeCount = 0;
        sibling.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (changeCount == 1) {
                    assertTrue(sibling.isChanging());
                    assertFalse(table.isChanging());
                }
                changeCount++;
            }
        });
        sibling.changeSelection(0, 10, false, false);
        changeCount = 0;
    }

    /**
     * Test the rowselectioninterval
     */
    public void testRowSelectionInterval() {
        System.out.println("testRowSelectionInterval");
        SelectionModel m = new SelectionModel();
        TestTableModel tm = new TestTableModel();
        final NyxJTable table = new NyxJTable();
        table.setSelectionModel(m);
        table.setModel(tm);
        final NyxJTable sibling = new NyxJTable();
        sibling.setSelectionModel(m);
        sibling.setModel(tm);
        table.setSiblingTable(sibling);
        sibling.setSiblingTable(table);
        m.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (changeCount == 1) {
                    assertTrue(table.isChanging());
                    assertFalse(sibling.isChanging());
                }
                changeCount++;
            }
        });
        changeCount = 0;
        table.setRowSelectionInterval(0, 10);
        m.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (changeCount == 1) {
                    assertTrue(sibling.isChanging());
                    assertFalse(table.isChanging());
                }
                changeCount++;
            }
        });
        sibling.setRowSelectionInterval(0, 10);
        changeCount = 0;
        m.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                assertEquals(sibling.getRowCount(), e.getLastIndex());
            }
        });
        try {
            sibling.setRowSelectionInterval(0, 10000);
            fail("Please update test and add checking for nr of rows");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Test the rowselectioninterval
     */
    public void testColumnSelectionInterval() {
        System.out.println("testColumnSelectionInterval");
        SelectionModel m = new SelectionModel();
        TestTableModel tm = new TestTableModel();
        final NyxJTable table = new NyxJTable();
        table.setSelectionModel(m);
        table.setModel(tm);
        final NyxJTable sibling = new NyxJTable();
        sibling.setSelectionModel(m);
        sibling.setModel(tm);
        table.setSiblingTable(sibling);
        sibling.setSiblingTable(table);
        m.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (changeCount == 1) {
                    assertTrue(table.isChanging());
                    assertFalse(sibling.isChanging());
                }
                changeCount++;
            }
        });
        changeCount = 0;
        table.setColumnSelectionInterval(0, 10);
        m.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (changeCount == 1) {
                    assertTrue(sibling.isChanging());
                    assertFalse(table.isChanging());
                }
                changeCount++;
            }
        });
        sibling.setColumnSelectionInterval(0, 10);
        changeCount = 0;
        m.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                assertEquals(sibling.getRowCount(), e.getLastIndex());
            }
        });
        sibling.setColumnSelectionInterval(0, 10000);
    }

    /**
     * A selectionModel for testing purposes
     */
    public class SelectionModel implements ListSelectionModel {

        /**
         * The registered listener
         */
        private ListSelectionListener listener;

        /**
         * @see javax.swing.ListSelectionModel#setSelectionInterval(int, int)
         */
        public void setSelectionInterval(int index0, int index1) {
            if (listener != null) {
                listener.valueChanged(new ListSelectionEvent(this, index0, index1, false));
            }
        }

        /**
         * @see javax.swing.ListSelectionModel#addSelectionInterval(int, int)
         */
        public void addSelectionInterval(int index0, int index1) {
        }

        /**
         * @see javax.swing.ListSelectionModel#removeSelectionInterval(int, int)
         */
        public void removeSelectionInterval(int index0, int index1) {
        }

        /**
         * @see javax.swing.ListSelectionModel#getMinSelectionIndex()
         */
        public int getMinSelectionIndex() {
            return 100;
        }

        /**
         * @see javax.swing.ListSelectionModel#getMaxSelectionIndex()
         */
        public int getMaxSelectionIndex() {
            return 100;
        }

        /**
         * @see javax.swing.ListSelectionModel#isSelectedIndex(int)
         */
        public boolean isSelectedIndex(int index) {
            return false;
        }

        /**
         * @see javax.swing.ListSelectionModel#getAnchorSelectionIndex()
         */
        public int getAnchorSelectionIndex() {
            return 0;
        }

        /**
         * @see javax.swing.ListSelectionModel#setAnchorSelectionIndex(int)
         */
        public void setAnchorSelectionIndex(int index) {
        }

        /**
         * @see javax.swing.ListSelectionModel#getLeadSelectionIndex()
         */
        public int getLeadSelectionIndex() {
            return 0;
        }

        /**
         * @see javax.swing.ListSelectionModel#setLeadSelectionIndex(int)
         */
        public void setLeadSelectionIndex(int index) {
        }

        /**
         * @see javax.swing.ListSelectionModel#clearSelection()
         */
        public void clearSelection() {
        }

        /**
         * @see javax.swing.ListSelectionModel#isSelectionEmpty()
         */
        public boolean isSelectionEmpty() {
            return false;
        }

        /**
         * @see javax.swing.ListSelectionModel#insertIndexInterval(int, int, boolean)
         */
        public void insertIndexInterval(int index, int length, boolean before) {
        }

        /**
         * @see javax.swing.ListSelectionModel#removeIndexInterval(int, int)
         */
        public void removeIndexInterval(int index0, int index1) {
        }

        /**
         * @see javax.swing.ListSelectionModel#setValueIsAdjusting(boolean)
         */
        public void setValueIsAdjusting(boolean valueIsAdjusting) {
        }

        /**
         * @see javax.swing.ListSelectionModel#getValueIsAdjusting()
         */
        public boolean getValueIsAdjusting() {
            return false;
        }

        /**
         * @see javax.swing.ListSelectionModel#setSelectionMode(int)
         */
        public void setSelectionMode(int selectionMode) {
        }

        /**
         * @see javax.swing.ListSelectionModel#getSelectionMode()
         */
        public int getSelectionMode() {
            return 0;
        }

        /**
         * @see javax.swing.ListSelectionModel#addListSelectionListener(javax.swing.event.ListSelectionListener)
         */
        public void addListSelectionListener(ListSelectionListener x) {
            listener = x;
        }

        /**
         * @see javax.swing.ListSelectionModel#removeListSelectionListener(javax.swing.event.ListSelectionListener)
         */
        public void removeListSelectionListener(ListSelectionListener x) {
        }
    }

    /**
     * The test tablemodel
     */
    public class TestTableModel implements TableModel {

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount() {
            return 100;
        }

        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount() {
            return 100;
        }

        /**
         * @see javax.swing.table.TableModel#getColumnName(int)
         */
        public String getColumnName(int columnIndex) {
            return null;
        }

        /**
         * @see javax.swing.table.TableModel#getColumnClass(int)
         */
        public Class getColumnClass(int columnIndex) {
            return null;
        }

        /**
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }

        /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        /**
         * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
         */
        public void addTableModelListener(TableModelListener l) {
        }

        /**
         * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
         */
        public void removeTableModelListener(TableModelListener l) {
        }
    }

    /**
     * Listener to figure out if settings are correct.
     */
    public class TestChangeListener implements ChangeListener {

        /**
         * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
         */
        public void stateChanged(ChangeEvent e) {
            //e
        }
    }
}
