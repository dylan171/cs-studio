/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.alarm.beast.ui.clientmodel;

import java.text.SimpleDateFormat;

import javax.jms.MapMessage;

import org.csstudio.alarm.beast.JMSAlarmMessage;
import org.csstudio.alarm.beast.SeverityLevel;
import org.csstudio.platform.data.ITimestamp;
import org.csstudio.platform.data.TimestampFactory;
import org.csstudio.platform.logging.JMSLogMessage;

/** Information about an alarm update
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class AlarmUpdateInfo
{
    /** Parser for received time stamp */
    final protected static SimpleDateFormat date_format =
        new SimpleDateFormat(JMSLogMessage.DATE_FORMAT);

    final private SeverityLevel current_severity, severity;
    final private String name_or_path, current_message, message, value;
    final private ITimestamp timestamp;

    /** Initialize from JMS MapMessage
     *  @param message Message that must contain alarm info
     *  @return {@link AlarmUpdateInfo}
     *  @throws Exception on error in JMS access or parsing of received data
     */
    public static AlarmUpdateInfo fromMapMessage(final MapMessage message)
            throws Exception
    {
        final String name = message.getString(JMSLogMessage.NAME);
        final SeverityLevel severity = SeverityLevel.parse(
                message.getString(JMSLogMessage.SEVERITY));
        final String status = message.getString(JMSAlarmMessage.STATUS);
        final SeverityLevel current_severity = SeverityLevel.parse(
                message.getString(JMSAlarmMessage.CURRENT_SEVERITY));
        final String current_message = message.getString(JMSAlarmMessage.CURRENT_STATUS);
        final String value = message.getString(JMSAlarmMessage.VALUE);
        final String timetext = message.getString(JMSLogMessage.EVENTTIME);
        final long millisecs = date_format.parse(timetext).getTime();
        final ITimestamp timestamp = TimestampFactory.fromMillisecs(millisecs);
        return new AlarmUpdateInfo(name, current_severity, current_message,
                severity, status, value, timestamp);
    }

    /** Initialize from pieces
     *  @param name_or_path
     *  @param current_severity
     *  @param current_message
     *  @param severity
     *  @param message
     *  @param value
     *  @param timestamp
     */
    public AlarmUpdateInfo(final String name_or_path,
            final SeverityLevel current_severity,
            final String current_message,
            final SeverityLevel severity, final String message,
            final String value,
            final ITimestamp timestamp)
    {
        this.name_or_path = name_or_path;
        this.current_severity = current_severity;
        this.current_message = current_message;
        this.severity = severity;
        this.message = message;
        this.value = value;
        this.timestamp = timestamp;
    }

    /** @return PV name */
    public String getNameOrPath()
    {
        return name_or_path;
    }

    /** @return Current PV severity */
    public SeverityLevel getCurrentSeverity()
    {
        return current_severity;
    }

    /** @return Current PV message */
    public String getCurrentMessage()
    {
        return current_message;
    }

    /** @return Alarm severity */
    public SeverityLevel getSeverity()
    {
        return severity;
    }

    /** @return Alarm message */
    public String getMessage()
    {
        return message;
    }

    /** @return Alarm value */
    public String getValue()
    {
        return value;
    }

    /** @return Time of alarm */
    public ITimestamp getTimestamp()
    {
        return timestamp;
    }

    /** @return Debug representation */
    @Override
    public String toString()
    {
        return "Update " + name_or_path + " to current " +
           current_severity.getDisplayName() + "/" + current_message + ", alarm " +
           severity.getDisplayName() + "/" + message;
    }
}
