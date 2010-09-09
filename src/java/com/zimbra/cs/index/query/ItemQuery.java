/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010 Zimbra, Inc.
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
package com.zimbra.cs.index.query;

import java.util.ArrayList;
import java.util.List;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.index.DBQueryOperation;
import com.zimbra.cs.index.NoResultsQueryOperation;
import com.zimbra.cs.index.QueryOperation;
import com.zimbra.cs.index.query.parser.QueryParser;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.service.util.ItemId;

/**
 * Query by item ID.
 *
 * @author tim
 * @author ysasaki
 */
public final class ItemQuery extends Query {

    private boolean mIsAllQuery;
    private boolean mIsNoneQuery;
    private List<ItemId> mItemIds;
    private Mailbox mMailbox;

    public static Query Create(Mailbox mbox, int mod, String str)
        throws ServiceException {

        boolean allQuery = false;
        boolean noneQuery = false;
        List<ItemId> itemIds = new ArrayList<ItemId>();

        if (str.equalsIgnoreCase("all")) {
            allQuery = true;
        } else if (str.equalsIgnoreCase("none")) {
            noneQuery = true;
        } else {
            String[] items = str.split(",");
            for (int i = 0; i < items.length; i++) {
                if (items[i].length() > 0) {
                    ItemId iid = new ItemId(items[i], mbox.getAccountId());
                    itemIds.add(iid);
                }
            }
            if (itemIds.size() == 0) {
                noneQuery = true;
            }
        }

        return new ItemQuery(mbox, mod, allQuery, noneQuery, itemIds);
    }

    ItemQuery(Mailbox mbox, int mod, boolean all, boolean none, List<ItemId> ids) {
        super(mod, QueryParser.ITEM);
        mIsAllQuery = all;
        mIsNoneQuery = none;
        mItemIds = ids;
        mMailbox = mbox;
    }

    @Override
    public QueryOperation getQueryOperation(boolean truth) {
        DBQueryOperation dbOp = new DBQueryOperation();

        truth = calcTruth(truth);

        if (truth&&mIsAllQuery || !truth&&mIsNoneQuery) {
            // adding no constraints should match everything...
        } else if (truth&&mIsNoneQuery || !truth&&mIsAllQuery) {
            return new NoResultsQueryOperation();
        } else {
            for (ItemId iid : mItemIds) {
                dbOp.addItemIdClause(mMailbox, iid, truth);
            }
        }
        return dbOp;
    }

    @Override
    public StringBuilder dump(StringBuilder out) {
        super.dump(out);
        if (mIsAllQuery) {
            out.append(",all");
        } else if (mIsNoneQuery) {
            out.append(",none");
        } else {
            for (ItemId id : mItemIds) {
                out.append(',');
                out.append(id.toString());
            }
        }
        return out.append(')');
    }
}
