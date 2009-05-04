/*
 * Copyright (C) 2009 The Android Open Source Project
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

package android.content;

import android.net.Uri;
import android.database.Cursor;

import java.util.Map;

public class ContentProviderOperation {
    private final static int TYPE_INSERT = 1;
    private final static int TYPE_UPDATE = 2;
    private final static int TYPE_DELETE = 3;
    private final static int TYPE_COUNT = 4;

    private final int mType;
    private final Uri mUri;
    private final String mSelection;
    private final String[] mSelectionArgs;
    private final ContentValues mValues;
    private final Integer mExpectedCount;
    private final ContentValues mValuesBackReferences;
    private final Map<Integer, Integer> mSelectionArgsBackReferences;

    private static final String[] COUNT_COLUMNS = new String[]{"count(*)"};

    /**
     * Creates a {@link ContentProviderOperation} by copying the contents of a
     * {@link Builder}.
     */
    private ContentProviderOperation(Builder builder) {
        mType = builder.mType;
        mUri = builder.mUri;
        mValues = builder.mValues;
        mSelection = builder.mSelection;
        mSelectionArgs = builder.mSelectionArgs;
        mExpectedCount = builder.mExpectedCount;
        mSelectionArgsBackReferences = builder.mSelectionArgsBackReferences;
        mValuesBackReferences = builder.mValuesBackReferences;
    }

    /**
     * Create a {@link Builder} suitable for building an insert {@link ContentProviderOperation}.
     * @param uri The {@link Uri} that is the target of the insert.
     * @return a {@link Builder}
     */
    public static Builder newInsert(Uri uri) {
        return new Builder(TYPE_INSERT, uri);
    }

    /**
     * Create a {@link Builder} suitable for building an update {@link ContentProviderOperation}.
     * @param uri The {@link Uri} that is the target of the update.
     * @return a {@link Builder}
     */
    public static Builder newUpdate(Uri uri) {
        return new Builder(TYPE_UPDATE, uri);
    }

    /**
     * Create a {@link Builder} suitable for building a delete {@link ContentProviderOperation}.
     * @param uri The {@link Uri} that is the target of the delete.
     * @return a {@link Builder}
     */
    public static Builder newDelete(Uri uri) {
        return new Builder(TYPE_DELETE, uri);
    }

    /**
     * Create a {@link Builder} suitable for building a count query. When used in conjunction
     * with {@link Builder#withExpectedCount(int)} this is useful for checking that the
     * uri/selection has the expected number of rows.
     * {@link ContentProviderOperation}.
     * @param uri The {@link Uri} to query.
     * @return a {@link Builder}
     */
    public static Builder newCountQuery(Uri uri) {
        return new Builder(TYPE_COUNT, uri);
    }

    /**
     * Applies this operation using the given provider. The backRefs array is used to resolve any
     * back references that were requested using
     * {@link Builder#withValueBackReferences(ContentValues)} and
     * {@link Builder#withSelectionBackReferences(java.util.Map)}.
     * @param provider the {@link ContentProvider} on which this batch is applied
     * @param backRefs a {@link ContentProviderResult} array that will be consulted
     * to resolve any requested back references.
     * @param numBackRefs the number of valid results on the backRefs array.
     * @return a {@link ContentProviderResult} that contains either the {@link Uri} of the inserted
     * row if this was an insert otherwise the number of rows affected.
     * @throws OperationApplicationException thrown if either the insert fails or
     * if the number of rows affected didn't match the expected count
     */
    public ContentProviderResult apply(ContentProvider provider, ContentProviderResult[] backRefs,
            int numBackRefs) throws OperationApplicationException {
        ContentValues values = resolveValueBackReferences(backRefs, numBackRefs);
        String[] selectionArgs =
                resolveSelectionArgsBackReferences(backRefs, numBackRefs);

        if (mType == TYPE_INSERT) {
            Uri newUri = provider.insert(mUri, values);
            if (newUri == null) {
                throw new OperationApplicationException("insert failed");
            }
            return new ContentProviderResult(newUri);
        }

        int numRows;
        if (mType == TYPE_DELETE) {
            numRows = provider.delete(mUri, mSelection, selectionArgs);
        } else if (mType == TYPE_UPDATE) {
            numRows = provider.update(mUri, values, mSelection, selectionArgs);
        } else if (mType == TYPE_COUNT) {
            Cursor cursor = provider.query(mUri, COUNT_COLUMNS, mSelection, selectionArgs, null);
            try {
                if (!cursor.moveToNext()) {
                    throw new RuntimeException("since we are doing a count query we should always "
                            + "be able to move to the first row");
                }
                if (cursor.getCount() != 1) {
                    throw new RuntimeException("since we are doing a count query there should "
                            + "always be exacly row, found " + cursor.getCount());
                }
                numRows = cursor.getInt(0);
            } finally {
                cursor.close();
            }
        } else {
            throw new IllegalStateException("bad type, " + mType);
        }

        if (mExpectedCount != null && mExpectedCount != numRows) {
            throw new OperationApplicationException("wrong number of rows: " + numRows);
        }

        return new ContentProviderResult(numRows);
    }

    /**
     * The ContentValues back references are represented as a ContentValues object where the
     * key refers to a column and the value is an index of the back reference whose
     * valued should be associated with the column.
     * @param backRefs an array of previous results
     * @param numBackRefs the number of valid previous results in backRefs
     * @return the ContentValues that should be used in this operation application after
     * expansion of back references. This can be called if either mValues or mValuesBackReferences
     * is null
     * @VisibleForTesting this is intended to be a private method but it is exposed for
     * unit testing purposes
     */
    public ContentValues resolveValueBackReferences(
            ContentProviderResult[] backRefs, int numBackRefs) {
        if (mValuesBackReferences == null) {
            return mValues;
        }
        final ContentValues values;
        if (mValues == null) {
            values = new ContentValues();
        } else {
            values = new ContentValues(mValues);
        }
        for (Map.Entry<String, Object> entry : mValuesBackReferences.valueSet()) {
            String key = entry.getKey();
            Integer backRefIndex = mValuesBackReferences.getAsInteger(key);
            if (backRefIndex == null) {
                throw new IllegalArgumentException("values backref " + key + " is not an integer");
            }
            values.put(key, backRefToValue(backRefs, numBackRefs, backRefIndex));
        }
        return values;
    }

    /**
     * The Selection Arguments back references are represented as a Map of Integer->Integer where
     * the key is an index into the selection argument array (see {@link Builder#withSelection})
     * and the value is the index of the previous result that should be used for that selection
     * argument array slot.
     * @param backRefs an array of previous results
     * @param numBackRefs the number of valid previous results in backRefs
     * @return the ContentValues that should be used in this operation application after
     * expansion of back references. This can be called if either mValues or mValuesBackReferences
     * is null
     * @VisibleForTesting this is intended to be a private method but it is exposed for
     * unit testing purposes
     */
    public String[] resolveSelectionArgsBackReferences(
            ContentProviderResult[] backRefs, int numBackRefs) {
        if (mSelectionArgsBackReferences == null) {
            return mSelectionArgs;
        }
        String[] newArgs = new String[mSelectionArgs.length];
        System.arraycopy(mSelectionArgs, 0, newArgs, 0, mSelectionArgs.length);
        for (Map.Entry<Integer, Integer> selectionArgBackRef
                : mSelectionArgsBackReferences.entrySet()) {
            final Integer selectionArgIndex = selectionArgBackRef.getKey();
            final int backRefIndex = selectionArgBackRef.getValue();
            newArgs[selectionArgIndex] = backRefToValue(backRefs, numBackRefs, backRefIndex);
        }
        return newArgs;
    }

    /**
     * Return the string representation of the requested back reference.
     * @param backRefs an array of results
     * @param numBackRefs the number of items in the backRefs array that are valid
     * @param backRefIndex which backRef to be used
     * @throws ArrayIndexOutOfBoundsException thrown if the backRefIndex is larger than
     * the numBackRefs
     * @return the string representation of the requested back reference.
     */
    private static String backRefToValue(ContentProviderResult[] backRefs, int numBackRefs,
            Integer backRefIndex) {
        if (backRefIndex > numBackRefs) {
            throw new ArrayIndexOutOfBoundsException("asked for back ref " + backRefIndex
                    + " but there are only " + numBackRefs + " back refs");
        }
        ContentProviderResult backRef = backRefs[backRefIndex];
        String backRefValue;
        if (backRef.uri != null) {
            backRefValue = backRef.uri.getLastPathSegment();
        } else {
            backRefValue = String.valueOf(backRef.count);
        }
        return backRefValue;
    }

    /**
     * Used to add parameters to a {@link ContentProviderOperation}. The {@link Builder} is
     * first created by calling {@link ContentProviderOperation#newInsert(android.net.Uri)},
     * {@link ContentProviderOperation#newUpdate(android.net.Uri)},
     * {@link ContentProviderOperation#newDelete(android.net.Uri)} or
     * {@link ContentProviderOperation#newCountQuery(android.net.Uri)}. The withXXX methods
     * can then be used to add parameters to the builder. See the specific methods to find for
     * which {@link Builder} type each is allowed. Call {@link #build} to create the
     * {@link ContentProviderOperation} once all the parameters have been supplied.
     */
    public static class Builder {
        private final int mType;
        private final Uri mUri;
        private String mSelection;
        private String[] mSelectionArgs;
        private ContentValues mValues;
        private Integer mExpectedCount;
        private ContentValues mValuesBackReferences;
        private Map<Integer, Integer> mSelectionArgsBackReferences;

        /** Create a {@link Builder} of a given type. The uri must not be null. */
        private Builder(int type, Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("uri must not be null");
            }
            mType = type;
            mUri = uri;
        }

        /** Create a ContentroviderOperation from this {@link Builder}. */
        public ContentProviderOperation build() {
            return new ContentProviderOperation(this);
        }

        /**
         * Add a {@link ContentValues} of back references. The key is the name of the column
         * and the value is an integer that is the index of the previous result whose
         * value should be used for the column. The value is added as a {@link String}.
         * A column value from the back references takes precedence over a value specified in
         * {@link #withValues}.
         * This can only be used with builders of type insert or update.
         * @return this builder, to allow for chaining.
         */
        public Builder withValueBackReferences(ContentValues backReferences) {
            if (mType != TYPE_INSERT && mType != TYPE_UPDATE) {
                throw new IllegalArgumentException(
                        "only inserts and updates can have value back-references");
            }
            mValuesBackReferences = backReferences;
            return this;
        }

        /**
         * Add a {@link Map} of back references. The integer key is the index of the selection arg
         * to set and the integer value is the index of the previous result whose
         * value should be used for the arg. If any value at that index of the selection arg
         * that was specified by {@likn withSelection} will be overwritten.
         * This can only be used with builders of type update, delete, or count query.
         * @return this builder, to allow for chaining.
         */
        public Builder withSelectionBackReferences(Map<Integer, Integer> backReferences) {
            if (mType != TYPE_COUNT && mType != TYPE_UPDATE && mType != TYPE_DELETE) {
                throw new IllegalArgumentException(
                        "only deletes, updates and counts can have selection back-references");
            }
            mSelectionArgsBackReferences = backReferences;
            return this;
        }

        /**
         * The ContentValues to use. This may be null. These values may be overwritten by
         * the corresponding value specified by {@link #withValueBackReferences(ContentValues)}.
         * This can only be used with builders of type insert or update.
         * @return this builder, to allow for chaining.
         */
        public Builder withValues(ContentValues values) {
            if (mType != TYPE_INSERT && mType != TYPE_UPDATE) {
                throw new IllegalArgumentException("only inserts and updates can have values");
            }
            mValues = values;
            return this;
        }

        /**
         * The selection and arguments to use. An occurrence of '?' in the selection will be
         * replaced with the corresponding occurence of the selection argument. Any of the
         * selection arguments may be overwritten by a selection argument back reference as
         * specified by {@link #withSelectionBackReferences}.
         * This can only be used with builders of type update, delete, or count query.
         * @return this builder, to allow for chaining.
         */
        public Builder withSelection(String selection, String[] selectionArgs) {
            if (mType != TYPE_DELETE && mType != TYPE_UPDATE && mType != TYPE_COUNT) {
                throw new IllegalArgumentException(
                        "only deletes, updates and counts can have selections");
            }
            mSelection = selection;
            mSelectionArgs = selectionArgs;
            return this;
        }

        /**
         * If set then if the number of rows affected by this operation do not match
         * this count {@link OperationApplicationException} will be throw.
         * This can only be used with builders of type update, delete, or count query.
         * @return this builder, to allow for chaining.
         */
        public Builder withExpectedCount(int count) {
            if (mType != TYPE_DELETE && mType != TYPE_UPDATE && mType != TYPE_COUNT) {
                throw new IllegalArgumentException(
                        "only deletes, updates and counts can have expected counts");
            }
            mExpectedCount = count;
            return this;
        }
    }
}
