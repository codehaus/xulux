/*
$Id: NewDictionaryWizardPage.java,v 1.1 2004-08-28 17:55:58 mvdb Exp $

Copyright 2004 The Xulux Project

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
package org.xulux.eclipse.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.dialogs.TypeSelectionDialog;
import org.eclipse.jdt.internal.ui.viewsupport.IViewPartInputProvider;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * The dictionary wizard allows you to set the filename of the dictionary
 * file to use.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NewDictionaryWizardPage.java,v 1.1 2004-08-28 17:55:58 mvdb Exp $
 */
public class NewDictionaryWizardPage extends WizardPage {
	private Text baseClassText;
	private Text directoryText;
	private Text fileText;
	private ISelection selection;

	/**
	 * @param pageName
	 */
	public NewDictionaryWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Xulux Dictionary File");
		setDescription("This wizard creates a new Xulux dictionary file.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Base Class:");

		baseClassText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		baseClassText.setLayoutData(gd);
		baseClassText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleClassBrowse();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Directory:");
		directoryText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		directoryText.setLayoutData(gd);
		directoryText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
		button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                handleDirectoryBrowse();
            }
        });
		
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");
		
		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		initialize();
		dialogChanged();
		setControl(container);
	}
	
	/**
	 * Tests if the current workbench selection is a suitable
	 * container to use.
	 */
	
	private void initialize() {
		if (selection!=null && selection.isEmpty()==false && selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection)selection;
			if (ssel.size()>1) return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer)obj;
				else
					container = ((IResource)obj).getParent();
				baseClassText.setText(container.getFullPath().toString());
			}
		}
		fileText.setText("Dictionary.xml");
	}
	
	private void handleDirectoryBrowse() {
		ContainerSelectionDialog dialog =
			new ContainerSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(),
				false,
				"Select directory to create dictionary in");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				directoryText.setText(((Path)result[0]).toOSString());
			}
		}
	}
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */

	private void handleClassBrowse() {
	    IJavaElement element = null;
		IWorkbenchPart part= JavaPlugin.getActivePage().getActivePart();
		System.out.println("part : " +part);
		if (part instanceof IViewPartInputProvider) {
			Object elem= ((IViewPartInputProvider)part).getViewPartInput();
			if (elem instanceof IJavaElement) {
				System.out.println("java element " + (IJavaElement) elem);
				element = (IJavaElement) elem;
			}
		}
		if (element.getElementType() == IJavaElement.JAVA_MODEL) {
		    try {
                IJavaProject[] projects = JavaCore.create(ResourcesPlugin.getWorkspace().getRoot()).getJavaProjects();
                if (projects.length == 1) {
                    element = projects[0];
                }
            } catch (JavaModelException e) {
                e.printStackTrace();
            }
		}
		IJavaElement[] elements= new IJavaElement[] { element };
		IJavaSearchScope scope= SearchEngine.createJavaSearchScope(elements);

		TypeSelectionDialog dialog= new TypeSelectionDialog(getShell(), getWizard().getContainer(), IJavaSearchConstants.TYPE, scope);
		dialog.setTitle("Select Dictionary Base Class");
		dialog.setMessage("Selet a base class to be used as the base of the dictionary to be created");

		if (dialog.open() == Window.OK) {
			System.out.println("Selected type : " +((IType) dialog.getFirstResult()).getFullyQualifiedName());
			baseClassText.setText(((IType) dialog.getFirstResult()).getFullyQualifiedName());
		}
	}
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		String fileName = getFileName();

		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * @return the baseclass
	 */
	public String getBaseClass() {
		return baseClassText.getText();
	}
	/**
	 * @return the filename
	 */
	public String getFileName() {
		return fileText.getText();
	}
	
	/**
	 * @return the directory
	 */
	public String getDirtoryName() {
	    return directoryText.getText();
	}
	    
}