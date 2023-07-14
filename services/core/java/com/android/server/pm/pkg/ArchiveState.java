/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.android.server.pm.pkg;

import android.annotation.NonNull;
import android.annotation.Nullable;

import com.android.internal.util.DataClass;

import java.nio.file.Path;
import java.util.List;

/**
 * Information about the state of an archived app. All fields are gathered at the time of
 * archival.
 *
 * @hide
 */
@DataClass(genEqualsHashCode = true, genToString = true)
public class ArchiveState {

    /**
     * Information about main activities.
     *
     * <p> This list has at least one entry. In the vast majority of cases, this list has only one
     * entry.
     */
    @NonNull
    private final List<ArchiveActivityInfo> mActivityInfos;

    /**
     * Corresponds to android:label of the installer responsible for the unarchival of the app.
     * Stored in the installer's locale .
     */
    @NonNull
    private final String mInstallerTitle;

    /** Information about a main activity of an archived app. */
    @DataClass(genEqualsHashCode = true, genToString = true)
    public static class ArchiveActivityInfo {
        /** Corresponds to the activity's android:label in the app's locale. */
        @NonNull
        private final String mTitle;

        /** The path to the stored icon of the activity in the app's locale. */
        @NonNull
        private final Path mIconBitmap;

        /** See {@link #mIconBitmap}. Only set if the app defined a monochrome icon. */
        @Nullable
        private final Path mMonochromeIconBitmap;



        // Code below generated by codegen v1.0.23.
        //
        // DO NOT MODIFY!
        // CHECKSTYLE:OFF Generated code
        //
        // To regenerate run:
        // $ codegen $ANDROID_BUILD_TOP/frameworks/base/services/core/java/com/android/server/pm/pkg/ArchiveState.java
        //
        // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
        //   Settings > Editor > Code Style > Formatter Control
        //@formatter:off


        /**
         * Creates a new ArchiveActivityInfo.
         *
         * @param title
         *   Corresponds to the activity's android:label in the app's locale.
         * @param iconBitmap
         *   The path to the stored icon of the activity in the app's locale.
         * @param monochromeIconBitmap
         *   See {@link #mIconBitmap}. Only set if the app defined a monochrome icon.
         */
        @DataClass.Generated.Member
        public ArchiveActivityInfo(
                @NonNull String title,
                @NonNull Path iconBitmap,
                @Nullable Path monochromeIconBitmap) {
            this.mTitle = title;
            com.android.internal.util.AnnotationValidations.validate(
                    NonNull.class, null, mTitle);
            this.mIconBitmap = iconBitmap;
            com.android.internal.util.AnnotationValidations.validate(
                    NonNull.class, null, mIconBitmap);
            this.mMonochromeIconBitmap = monochromeIconBitmap;

            // onConstructed(); // You can define this method to get a callback
        }

        /**
         * Corresponds to the activity's android:label in the app's locale.
         */
        @DataClass.Generated.Member
        public @NonNull String getTitle() {
            return mTitle;
        }

        /**
         * The path to the stored icon of the activity in the app's locale.
         */
        @DataClass.Generated.Member
        public @NonNull Path getIconBitmap() {
            return mIconBitmap;
        }

        /**
         * See {@link #mIconBitmap}. Only set if the app defined a monochrome icon.
         */
        @DataClass.Generated.Member
        public @Nullable Path getMonochromeIconBitmap() {
            return mMonochromeIconBitmap;
        }

        @Override
        @DataClass.Generated.Member
        public String toString() {
            // You can override field toString logic by defining methods like:
            // String fieldNameToString() { ... }

            return "ArchiveActivityInfo { " +
                    "title = " + mTitle + ", " +
                    "iconBitmap = " + mIconBitmap + ", " +
                    "monochromeIconBitmap = " + mMonochromeIconBitmap +
            " }";
        }

        @Override
        @DataClass.Generated.Member
        public boolean equals(@Nullable Object o) {
            // You can override field equality logic by defining either of the methods like:
            // boolean fieldNameEquals(ArchiveActivityInfo other) { ... }
            // boolean fieldNameEquals(FieldType otherValue) { ... }

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            @SuppressWarnings("unchecked")
            ArchiveActivityInfo that = (ArchiveActivityInfo) o;
            //noinspection PointlessBooleanExpression
            return true
                    && java.util.Objects.equals(mTitle, that.mTitle)
                    && java.util.Objects.equals(mIconBitmap, that.mIconBitmap)
                    && java.util.Objects.equals(mMonochromeIconBitmap, that.mMonochromeIconBitmap);
        }

        @Override
        @DataClass.Generated.Member
        public int hashCode() {
            // You can override field hashCode logic by defining methods like:
            // int fieldNameHashCode() { ... }

            int _hash = 1;
            _hash = 31 * _hash + java.util.Objects.hashCode(mTitle);
            _hash = 31 * _hash + java.util.Objects.hashCode(mIconBitmap);
            _hash = 31 * _hash + java.util.Objects.hashCode(mMonochromeIconBitmap);
            return _hash;
        }

        @DataClass.Generated(
                time = 1689169065133L,
                codegenVersion = "1.0.23",
                sourceFile = "frameworks/base/services/core/java/com/android/server/pm/pkg/ArchiveState.java",
                inputSignatures = "private final @android.annotation.NonNull java.lang.String mTitle\nprivate final @android.annotation.NonNull java.nio.file.Path mIconBitmap\nprivate final @android.annotation.Nullable java.nio.file.Path mMonochromeIconBitmap\nclass ArchiveActivityInfo extends java.lang.Object implements []\n@com.android.internal.util.DataClass(genEqualsHashCode=true, genToString=true)")
        @Deprecated
        private void __metadata() {}


        //@formatter:on
        // End of generated code

    }





    // Code below generated by codegen v1.0.23.
    //
    // DO NOT MODIFY!
    // CHECKSTYLE:OFF Generated code
    //
    // To regenerate run:
    // $ codegen $ANDROID_BUILD_TOP/frameworks/base/services/core/java/com/android/server/pm/pkg/ArchiveState.java
    //
    // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
    //   Settings > Editor > Code Style > Formatter Control
    //@formatter:off


    /**
     * Creates a new ArchiveState.
     *
     * @param activityInfos
     *   Information about main activities.
     *
     *   <p> This list has at least one entry. In the vast majority of cases, this list has only one
     *   entry.
     * @param installerTitle
     *   Corresponds to android:label of the installer responsible for the unarchival of the app.
     *   Stored in the installer's locale .
     */
    @DataClass.Generated.Member
    public ArchiveState(
            @NonNull List<ArchiveActivityInfo> activityInfos,
            @NonNull String installerTitle) {
        this.mActivityInfos = activityInfos;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mActivityInfos);
        this.mInstallerTitle = installerTitle;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mInstallerTitle);

        // onConstructed(); // You can define this method to get a callback
    }

    /**
     * Information about main activities.
     *
     * <p> This list has at least one entry. In the vast majority of cases, this list has only one
     * entry.
     */
    @DataClass.Generated.Member
    public @NonNull List<ArchiveActivityInfo> getActivityInfos() {
        return mActivityInfos;
    }

    /**
     * Corresponds to android:label of the installer responsible for the unarchival of the app.
     * Stored in the installer's locale .
     */
    @DataClass.Generated.Member
    public @NonNull String getInstallerTitle() {
        return mInstallerTitle;
    }

    @Override
    @DataClass.Generated.Member
    public String toString() {
        // You can override field toString logic by defining methods like:
        // String fieldNameToString() { ... }

        return "ArchiveState { " +
                "activityInfos = " + mActivityInfos + ", " +
                "installerTitle = " + mInstallerTitle +
        " }";
    }

    @Override
    @DataClass.Generated.Member
    public boolean equals(@Nullable Object o) {
        // You can override field equality logic by defining either of the methods like:
        // boolean fieldNameEquals(ArchiveState other) { ... }
        // boolean fieldNameEquals(FieldType otherValue) { ... }

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        @SuppressWarnings("unchecked")
        ArchiveState that = (ArchiveState) o;
        //noinspection PointlessBooleanExpression
        return true
                && java.util.Objects.equals(mActivityInfos, that.mActivityInfos)
                && java.util.Objects.equals(mInstallerTitle, that.mInstallerTitle);
    }

    @Override
    @DataClass.Generated.Member
    public int hashCode() {
        // You can override field hashCode logic by defining methods like:
        // int fieldNameHashCode() { ... }

        int _hash = 1;
        _hash = 31 * _hash + java.util.Objects.hashCode(mActivityInfos);
        _hash = 31 * _hash + java.util.Objects.hashCode(mInstallerTitle);
        return _hash;
    }

    @DataClass.Generated(
            time = 1689169065144L,
            codegenVersion = "1.0.23",
            sourceFile = "frameworks/base/services/core/java/com/android/server/pm/pkg/ArchiveState.java",
            inputSignatures = "private final @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.ArchiveActivityInfo> mActivityInfos\nprivate final @android.annotation.NonNull java.lang.String mInstallerTitle\nclass ArchiveState extends java.lang.Object implements []\n@com.android.internal.util.DataClass(genEqualsHashCode=true, genToString=true)")
    @Deprecated
    private void __metadata() {}


    //@formatter:on
    // End of generated code

}
