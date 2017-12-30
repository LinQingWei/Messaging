/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.messaging.datamodel.action;

import android.os.Parcel;
import android.text.TextUtils;

import com.android.messaging.datamodel.BugleDatabaseOperations;
import com.android.messaging.datamodel.DataModel;
import com.android.messaging.datamodel.DatabaseWrapper;
import com.android.messaging.datamodel.MessagingContentProvider;
import com.android.messaging.util.Assert;

public class FreemeUpdateConversationTopStatusAction extends Action {

    public static void updateTopStatus(final String conversationId, boolean needTop) {
        final FreemeUpdateConversationTopStatusAction action =
                new FreemeUpdateConversationTopStatusAction(conversationId, needTop);
        action.start();
    }

    private static final String KEY_CONVERSATION_ID = "conversation_id";
    private static final String KEY_IS_TOP = "is_top";

    protected FreemeUpdateConversationTopStatusAction(
            final String conversationId, final boolean isTop) {
        Assert.isTrue(!TextUtils.isEmpty(conversationId));
        actionParameters.putString(KEY_CONVERSATION_ID, conversationId);
        actionParameters.putBoolean(KEY_IS_TOP, isTop);
    }

    @Override
    protected Object executeAction() {
        final String conversationId = actionParameters.getString(KEY_CONVERSATION_ID);
        final boolean isTop = actionParameters.getBoolean(KEY_IS_TOP);

        final DatabaseWrapper db = DataModel.get().getDatabase();
        db.beginTransaction();
        try {
            BugleDatabaseOperations.updateConversationTopStatusInTransaction(
                    db, conversationId, isTop);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        MessagingContentProvider.notifyConversationListChanged();
        MessagingContentProvider.notifyConversationMetadataChanged(conversationId);
        return null;
    }

    protected FreemeUpdateConversationTopStatusAction(final Parcel in) {
        super(in);
    }

    public static final Creator<FreemeUpdateConversationTopStatusAction> CREATOR
            = new Creator<FreemeUpdateConversationTopStatusAction>() {
        @Override
        public FreemeUpdateConversationTopStatusAction createFromParcel(final Parcel in) {
            return new FreemeUpdateConversationTopStatusAction(in);
        }

        @Override
        public FreemeUpdateConversationTopStatusAction[] newArray(final int size) {
            return new FreemeUpdateConversationTopStatusAction[size];
        }
    };

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        writeActionToParcel(parcel, flags);
    }
}
