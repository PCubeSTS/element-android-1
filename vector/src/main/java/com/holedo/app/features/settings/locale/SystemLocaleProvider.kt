/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.holedo.app.features.settings.locale

import android.content.Context
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

class SystemLocaleProvider @Inject constructor(
        private val context: Context
) {

    /**
     * Provides the device locale
     *
     * @return the device locale, or null in case of error
     */
    fun getSystemLocale(): Locale? {
        return try {
            val packageManager = context.packageManager
            val resources = packageManager.getResourcesForApplication("android")
            @Suppress("DEPRECATION")
            resources.configuration.locale
        } catch (e: Exception) {
            Timber.e(e, "## getDeviceLocale() failed")
            null
        }
    }
}