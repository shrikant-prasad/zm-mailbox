/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2015 Zimbra, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.cs.pushnotifications;

import com.zimbra.common.util.ZimbraLog;

public class NotificationProcessor implements Runnable {

    private final PushNotification notification;

    public NotificationProcessor(PushNotification notification) {
        this.notification = notification;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        ZimbraLog.clearContext();
        ZimbraLog.addMboxToContext(notification.getDevice().getMailboxId());
        ZimbraLog.addAccountNameToContext(notification.getAccountName());
        int itemId = notification.getItemId();
        if (itemId != -1) {
            ZimbraLog.addItemToContext(itemId);
        }
        if (!notification.getDataSourceName().isEmpty()) {
            ZimbraLog.addDataSourceNameToContext(notification.getDataSourceName());
        }

        PushProvider provider = ProviderFactory.createProvider(notification.getDevice());
        if (provider != null) {
            ZimbraLog.mailbox.debug("ZMG: Process notification for device token=%s", notification
                .getDevice().getRegistrationId());
            provider.push(notification);
        } else {
            ZimbraLog.mailbox.info("ZMG: No provider name=%s found for device token=%s",
                notification.getDevice().getPushProvider(), notification.getDevice()
                    .getRegistrationId());
        }
    }
}
