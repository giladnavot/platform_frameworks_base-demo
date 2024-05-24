/*
 * Copyright (C) 2020 The Android Open Source Project
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

package android.content.pm;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;

import com.android.internal.util.DataClass;
import com.android.internal.util.Parcelling;

/**
 * Information about a process an app may run.  This corresponds to information collected from the
 * AndroidManifest.xml's &lt;permission-group&gt; tags.
 * @hide
 */
@DataClass(genGetters = true, genSetters = false, genParcelable = true, genAidl = false,
        genBuilder = false)
public class ProcessInfo implements Parcelable {
    /**
     * The name of the process, fully-qualified based on the app's package name.
     */
    @NonNull
    public String name;

    /**
     * If non-null, these are permissions that are not allowed in this process.
     */
    @Nullable
    @DataClass.ParcelWith(Parcelling.BuiltIn.ForInternedStringArraySet.class)
    public ArraySet<String> deniedPermissions;

    /**
     * Indicates if the process has requested GWP-ASan to be enabled, disabled, or left unspecified.
     */
    public @ApplicationInfo.GwpAsanMode int gwpAsanMode;

    /**
     * Indicates if the process has requested Memtag to be enabled (in sync or async mode),
     * disabled, or left unspecified.
     */
    public @ApplicationInfo.MemtagMode int memtagMode;

    /**
     * Enable automatic zero-initialization of native heap memory allocations.
     */
    public @ApplicationInfo.NativeHeapZeroInitialized int nativeHeapZeroInitialized;

    /**
     * Enable use of embedded dex in the APK, rather than extracted or locally compiled variants.
     * If false (default), the parent app's configuration determines behavior.
     */
    public boolean useEmbeddedDex;

    @Deprecated
    public ProcessInfo(@NonNull ProcessInfo orig) {
        this.name = orig.name;
        this.deniedPermissions = orig.deniedPermissions;
        this.gwpAsanMode = orig.gwpAsanMode;
        this.memtagMode = orig.memtagMode;
        this.nativeHeapZeroInitialized = orig.nativeHeapZeroInitialized;
        this.useEmbeddedDex = orig.useEmbeddedDex;
    }



    // Code below generated by codegen v1.0.23.
    //
    // DO NOT MODIFY!
    // CHECKSTYLE:OFF Generated code
    //
    // To regenerate run:
    // $ codegen $ANDROID_BUILD_TOP/frameworks/base/core/java/android/content/pm/ProcessInfo.java
    //
    // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
    //   Settings > Editor > Code Style > Formatter Control
    //@formatter:off


    /**
     * Creates a new ProcessInfo.
     *
     * @param name
     *   The name of the process, fully-qualified based on the app's package name.
     * @param deniedPermissions
     *   If non-null, these are permissions that are not allowed in this process.
     * @param gwpAsanMode
     *   Indicates if the process has requested GWP-ASan to be enabled, disabled, or left unspecified.
     * @param memtagMode
     *   Indicates if the process has requested Memtag to be enabled (in sync or async mode),
     *   disabled, or left unspecified.
     * @param nativeHeapZeroInitialized
     *   Enable automatic zero-initialization of native heap memory allocations.
     * @param useEmbeddedDex
     *   Enable use of embedded dex in the APK, rather than extracted or locally compiled variants.
     *   If false (default), the parent app's configuration determines behavior.
     */
    @DataClass.Generated.Member
    public ProcessInfo(
            @NonNull String name,
            @Nullable ArraySet<String> deniedPermissions,
            @ApplicationInfo.GwpAsanMode int gwpAsanMode,
            @ApplicationInfo.MemtagMode int memtagMode,
            @ApplicationInfo.NativeHeapZeroInitialized int nativeHeapZeroInitialized,
            boolean useEmbeddedDex) {
        this.name = name;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, name);
        this.deniedPermissions = deniedPermissions;
        this.gwpAsanMode = gwpAsanMode;
        com.android.internal.util.AnnotationValidations.validate(
                ApplicationInfo.GwpAsanMode.class, null, gwpAsanMode);
        this.memtagMode = memtagMode;
        com.android.internal.util.AnnotationValidations.validate(
                ApplicationInfo.MemtagMode.class, null, memtagMode);
        this.nativeHeapZeroInitialized = nativeHeapZeroInitialized;
        com.android.internal.util.AnnotationValidations.validate(
                ApplicationInfo.NativeHeapZeroInitialized.class, null, nativeHeapZeroInitialized);
        this.useEmbeddedDex = useEmbeddedDex;

        // onConstructed(); // You can define this method to get a callback
    }

    @DataClass.Generated.Member
    static Parcelling<ArraySet<String>> sParcellingForDeniedPermissions =
            Parcelling.Cache.get(
                    Parcelling.BuiltIn.ForInternedStringArraySet.class);
    static {
        if (sParcellingForDeniedPermissions == null) {
            sParcellingForDeniedPermissions = Parcelling.Cache.put(
                    new Parcelling.BuiltIn.ForInternedStringArraySet());
        }
    }

    @Override
    @DataClass.Generated.Member
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        // You can override field parcelling by defining methods like:
        // void parcelFieldName(Parcel dest, int flags) { ... }

        byte flg = 0;
        if (useEmbeddedDex) flg |= 0x20;
        if (deniedPermissions != null) flg |= 0x2;
        dest.writeByte(flg);
        dest.writeString(name);
        sParcellingForDeniedPermissions.parcel(deniedPermissions, dest, flags);
        dest.writeInt(gwpAsanMode);
        dest.writeInt(memtagMode);
        dest.writeInt(nativeHeapZeroInitialized);
    }

    @Override
    @DataClass.Generated.Member
    public int describeContents() { return 0; }

    /** @hide */
    @SuppressWarnings({"unchecked", "RedundantCast"})
    @DataClass.Generated.Member
    protected ProcessInfo(@NonNull Parcel in) {
        // You can override field unparcelling by defining methods like:
        // static FieldType unparcelFieldName(Parcel in) { ... }

        byte flg = in.readByte();
        boolean _useEmbeddedDex = (flg & 0x20) != 0;
        String _name = in.readString();
        ArraySet<String> _deniedPermissions = sParcellingForDeniedPermissions.unparcel(in);
        int _gwpAsanMode = in.readInt();
        int _memtagMode = in.readInt();
        int _nativeHeapZeroInitialized = in.readInt();

        this.name = _name;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, name);
        this.deniedPermissions = _deniedPermissions;
        this.gwpAsanMode = _gwpAsanMode;
        com.android.internal.util.AnnotationValidations.validate(
                ApplicationInfo.GwpAsanMode.class, null, gwpAsanMode);
        this.memtagMode = _memtagMode;
        com.android.internal.util.AnnotationValidations.validate(
                ApplicationInfo.MemtagMode.class, null, memtagMode);
        this.nativeHeapZeroInitialized = _nativeHeapZeroInitialized;
        com.android.internal.util.AnnotationValidations.validate(
                ApplicationInfo.NativeHeapZeroInitialized.class, null, nativeHeapZeroInitialized);
        this.useEmbeddedDex = _useEmbeddedDex;

        // onConstructed(); // You can define this method to get a callback
    }

    @DataClass.Generated.Member
    public static final @NonNull Parcelable.Creator<ProcessInfo> CREATOR
            = new Parcelable.Creator<ProcessInfo>() {
        @Override
        public ProcessInfo[] newArray(int size) {
            return new ProcessInfo[size];
        }

        @Override
        public ProcessInfo createFromParcel(@NonNull Parcel in) {
            return new ProcessInfo(in);
        }
    };

    @DataClass.Generated(
            time = 1706177470784L,
            codegenVersion = "1.0.23",
            sourceFile = "frameworks/base/core/java/android/content/pm/ProcessInfo.java",
            inputSignatures = "public @android.annotation.NonNull java.lang.String name\npublic @android.annotation.Nullable @com.android.internal.util.DataClass.ParcelWith(com.android.internal.util.Parcelling.BuiltIn.ForInternedStringArraySet.class) android.util.ArraySet<java.lang.String> deniedPermissions\npublic @android.content.pm.ApplicationInfo.GwpAsanMode int gwpAsanMode\npublic @android.content.pm.ApplicationInfo.MemtagMode int memtagMode\npublic @android.content.pm.ApplicationInfo.NativeHeapZeroInitialized int nativeHeapZeroInitialized\npublic  boolean useEmbeddedDex\nclass ProcessInfo extends java.lang.Object implements [android.os.Parcelable]\n@com.android.internal.util.DataClass(genGetters=true, genSetters=false, genParcelable=true, genAidl=false, genBuilder=false)")
    @Deprecated
    private void __metadata() {}


    //@formatter:on
    // End of generated code

}
