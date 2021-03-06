/* 
 * Copyright (c) 2008 Stiftung Deutsches Elektronen-Synchrotron, 
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS. 
 * WITHOUT WARRANTY OF ANY KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR PARTICULAR PURPOSE AND 
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE 
 * IN ANY RESPECT, THE USER ASSUMES THE COST OF ANY NECESSARY SERVICING, REPAIR OR 
 * CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE. 
 * NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.
 * DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, 
 * OR MODIFICATIONS.
 * THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION, MODIFICATION, 
 * USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS 
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY 
 * AT HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 */
package org.csstudio.sds.components.epics;

import java.util.HashMap;
import java.util.Map;

import org.csstudio.platform.simpledal.ConnectionState;
import org.csstudio.sds.components.model.ActionButtonModel;
import org.csstudio.sds.internal.rules.DirectConnectionRule;
import org.csstudio.sds.model.initializers.AbstractControlSystemSchema;

/**
 * Initializes an action button with EPICS specific property values.
 * 
 * TODO: Action Button has no border: How should we make the alarm status visible?
 * 
 * @author jhatje
 * 
 */
public final class ActionButtonInitializer extends
		AbstractEpicsWidgetInitializer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize(final AbstractControlSystemSchema schema) {
//		initializeCommonAlarmBehaviour();
		initializeCommonConnectionStates();

		initializeDynamicProperty(ActionButtonModel.PROP_ACTIONDATA,
				"$channel$", "$channel$", DirectConnectionRule.TYPE_ID);

		// To set dynamisation and condition state for the same property the
		// condition states has to be set at last. Otherwise the dynamicProperty
		// deletes the condition states.
		initializeDynamicProperty(ActionButtonModel.PROP_LABEL, "$channel$",
				null, DirectConnectionRule.TYPE_ID);
		Map<ConnectionState, Object> stringsByConnectionState = new HashMap<ConnectionState, Object>();
		stringsByConnectionState.put(ConnectionState.CONNECTION_LOST,
				"Connection lost");
		stringsByConnectionState.put(ConnectionState.INITIAL, "Initialisation");
		initializeDynamicPropertyForConnectionState(
				ActionButtonModel.PROP_LABEL, "$channel$",
				stringsByConnectionState, DirectConnectionRule.TYPE_ID);
	}
}
