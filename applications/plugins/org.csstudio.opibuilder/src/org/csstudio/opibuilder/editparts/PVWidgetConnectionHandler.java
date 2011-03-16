/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.opibuilder.editparts;

import org.csstudio.platform.ui.util.UIBundlingThread;
import org.csstudio.utility.pv.PV;

/**
 * The connection handler for PV widget. It will set the enable state of the widget 
 * based on control PV's connectivity.
 * @author Xihui Chen
 *
 */
public class PVWidgetConnectionHandler extends ConnectionHandler{

	private AbstractPVWidgetEditPart editPart;
	
	public PVWidgetConnectionHandler(AbstractPVWidgetEditPart editpart) {
		super(editpart);
		this.editPart = editpart;
	}
	
	@Override
	protected void markWidgetAsDisconnected(PV pv) {
		super.markWidgetAsDisconnected(pv);
		final PV controlPV = editPart.getControlPV();
		if(controlPV != null && controlPV == pv){
		UIBundlingThread.getInstance().addRunnable(new Runnable() {
			
			public void run() {
				editPart.getFigure().setEnabled(false);
			}
		});
		}
	}
	
	@Override
	protected void widgetConnectionRecovered(PV pv) {
		if(isConnected())
			return;
		super.widgetConnectionRecovered(pv);
		final PV controlPV = editPart.getControlPV();
		if(controlPV != null && controlPV == pv){
		UIBundlingThread.getInstance().addRunnable(new Runnable() {			
			public void run() {
				editPart.getFigure().setEnabled(
						editPart.getWidgetModel().isEnabled() 
						&& controlPV.isWriteAllowed());
			}
		});
		}
		
	}

	
	
	
}
