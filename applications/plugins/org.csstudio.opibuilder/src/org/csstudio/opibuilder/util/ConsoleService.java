/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.opibuilder.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

import org.csstudio.opibuilder.OPIBuilderPlugin;
import org.csstudio.opibuilder.preferences.PreferencesHelper;
import org.csstudio.platform.ui.util.CustomMediaFactory;
import org.csstudio.platform.ui.util.UIBundlingThread;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;


/**The console service which manage the console output.
 * @author Xihui Chen
 *
 */
public class ConsoleService {

	private static final String ENTER = "\n"; //$NON-NLS-1$

	private static ConsoleService instance;

	/**
	 * The message console.
	 */
	private MessageConsole console = null;

	/**
	 * The console output stream.
	 */
	private MessageConsoleStream errorStream, warningStream, infoStream;

	/**
	 * Return the only one instance of this class.
	 *
	 * @return The only one instance of this class.
	 */
	public synchronized static ConsoleService getInstance() {
		if (instance == null) {
			instance = new ConsoleService();
		}
		return instance;
	}

	private ConsoleService() {
		console = new MessageConsole("OPI Builder Console", null);


		// Values are from https://bugs.eclipse.org/bugs/show_bug.cgi?id=46871#c5
		console.setWaterMarks(80000, 100000);

		ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
		consolePlugin.getConsoleManager().addConsoles(
				new IConsole[] { console });

	}



	private String getTimeString(){
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$
	    return sdf.format(cal.getTime());

	}

	/**Write error information to the OPI console.
	 * @param message the output string.
	 */
	public void writeError(final String message){
		switch (PreferencesHelper.getConsolePopupLevel()) {
		case ALL:
			popConsoleView();
			break;
		default:
			break;
		}

		final String output = getTimeString() + " ERROR: " + message + ENTER;
		UIBundlingThread.getInstance().addRunnable(new Runnable() {

			public void run() {
				if(errorStream == null){
					errorStream = console.newMessageStream();
					errorStream.setColor(CustomMediaFactory.getInstance().getColor(
							CustomMediaFactory.COLOR_RED));
				}
				writeToConsole(errorStream, output);
			}
		});


	}

	/**Write warning information to the OPI console.
	 * @param message the output string.
	 */
	public void writeWarning(String message){
		final String output = getTimeString() + " WARNNING: " + message+ ENTER;
		switch (PreferencesHelper.getConsolePopupLevel()) {
		case ALL:
			popConsoleView();
			break;
		default:
			break;
		}
		UIBundlingThread.getInstance().addRunnable(new Runnable() {

			public void run() {
				if(warningStream == null){
					warningStream = console.newMessageStream();
					warningStream.setColor(CustomMediaFactory.getInstance().getColor(
							CustomMediaFactory.COLOR_ORANGE));
				}
				writeToConsole(warningStream, output);
			}
		});

	}

	/**Write information to the OPI console.
	 * @param message the output string.
	 */
	public void writeInfo(String message){
		final String output = getTimeString() + " INFO: " + message+ ENTER;
		switch (PreferencesHelper.getConsolePopupLevel()) {
		case ALL:
		case ONLY_INFO:
			popConsoleView();
			break;
		default:
			break;
		}
		UIBundlingThread.getInstance().addRunnable(new Runnable(){
			public void run() {
				if(infoStream == null){
					infoStream = console.newMessageStream();
					infoStream.setColor(CustomMediaFactory.getInstance().getColor(
							CustomMediaFactory.COLOR_BLACK));
				}
				writeToConsole(infoStream, output);
			}
		});

	}

	public void writeString(String s){
		if(infoStream == null){
			infoStream = console.newMessageStream();
			infoStream.setColor(CustomMediaFactory.getInstance().getColor(
					CustomMediaFactory.COLOR_BLACK));
		}
		writeToConsole(infoStream, s);
	}



	/**Write string to the console.
	 * @param output
	 */
	private void writeToConsole(MessageConsoleStream stream, String output){
		try {
			stream.write(output);
		} catch (IOException e) {
            OPIBuilderPlugin.getLogger().log(Level.WARNING, "Write Console error",e); //$NON-NLS-1$
		}
	}

	private void popConsoleView(){
		if(PlatformUI.getWorkbench() != null){
			UIBundlingThread.getInstance().addRunnable(new Runnable() {
				public void run() {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().
							getActivePage().showView("org.eclipse.ui.console.ConsoleView"); //$NON-NLS-1$
					} catch (PartInitException e) {
			            OPIBuilderPlugin.getLogger().log(Level.WARNING, "ConsoleView activation error",e); //$NON-NLS-1$
					}
				}
			});
		}
	}
}
