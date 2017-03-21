/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2015, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.soap.type;

import javax.xml.bind.annotation.XmlEnum;


/**
 * Message Content the client expects in response
 *
 */
@XmlEnum
public enum MsgContent {

    full, // The complete message
    original, // Only the Message and not quoted text
    both; // The complete message and also this message without quoted text 

    public static MsgContent fromString(String msgContent) {
        try {
            if (msgContent != null)
                return MsgContent.valueOf(msgContent);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }
}
