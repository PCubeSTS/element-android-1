/*
 * Copyright (c) 2022 New Vector Ltd
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

package com.holedo.app.test.fakes

import com.holedo.app.features.onboarding.DirectLoginUseCase
import com.holedo.app.features.onboarding.OnboardingAction
import io.mockk.coEvery
import io.mockk.mockk
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig

class FakeDirectLoginUseCase {
    val instance = mockk<DirectLoginUseCase>()

    fun givenSuccessResult(action: OnboardingAction.LoginOrRegister, config: HomeServerConnectionConfig?, result: FakeSession) {
        coEvery { instance.execute(action, config) } returns Result.success(result)
    }

    fun givenFailureResult(action: OnboardingAction.LoginOrRegister, config: HomeServerConnectionConfig?, cause: Throwable) {
        coEvery { instance.execute(action, config) } returns Result.failure(cause)
    }
}