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
 *
 */

package com.android.systemui.communal.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.android.systemui.R

/** Wraps around a widget rendered in communal mode. */
class CommunalWidgetWrapper(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    init {
        id = R.id.communal_widget_wrapper
    }
}
