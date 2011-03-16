/*
 * Copyright (c) 2010 Stiftung Deutsches Elektronen-Synchrotron,
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
package org.csstudio.domain.desy.epics.alarm;

import javax.annotation.Nonnull;

import org.csstudio.domain.desy.system.AbstractAlarmSystemVariable;
import org.csstudio.domain.desy.system.ControlSystem;
import org.csstudio.domain.desy.time.TimeInstant;
import org.csstudio.domain.desy.types.ICssValueType;

/**
 * An EPICS system variable is tightly bound to EpicsAlarms
 *
 * @author bknerr
 * @since 17.11.2010
 * @param <V> the base value type
 * @param <T> the css variable type
 */
public class EpicsSystemVariable<T>
    extends AbstractAlarmSystemVariable<T, EpicsAlarm> {


    /**
     * Constructor.
     * @param name
     * @param data
     * @param origin the control system from which this variable originates
     * @param timestamp
     * @param alarm
     */
    public EpicsSystemVariable(@Nonnull final String name,
                               @Nonnull final ICssValueType<T> data,
                               @Nonnull final ControlSystem origin,
                               @Nonnull final TimeInstant timestamp,
                               @Nonnull final EpicsAlarm alarm) {
        super(name, data, origin, timestamp, alarm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    public EpicsAlarm getAlarm() {
        return super.getAlarm();
    }
}
