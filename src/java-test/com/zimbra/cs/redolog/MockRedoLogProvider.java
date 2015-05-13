/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2013, 2014 Zimbra, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.redolog;

import java.io.File;

import com.zimbra.common.service.ServiceException;

/**
 * Mock {@link RedoLogProvider} for unit test.
 *
 * @author ysasaki
 */
public class MockRedoLogProvider extends RedoLogProvider {

    public MockRedoLogProvider() throws ServiceException {
        mRedoLogManager = new FileRedoLogManager(new File("build/test/redo/redo.log"), new File("build/test/redo"), false);
    }

    @Override
    public boolean isMaster() {
        return true;
    }

    @Override
    public boolean isSlave() {
        return false;
    }

    @Override
    public void startup(boolean runCrashRecovery) throws ServiceException {
    }

    @Override
    public void shutdown() throws ServiceException {
    }

    @Override
    public void initRedoLogManager() {
    }

    //we intentionally stub startup/shutdown since redo log is not needed for test
    //these methods let tests which exercise the redolog start and stop it as needed
    public void forceStart() throws ServiceException {
        mRedoLogManager.start(false);
    }

    public void forceStop() {
        mRedoLogManager.stop();
    }

}
