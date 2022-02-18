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

package im.vector.app.features.onboarding.ftueauth

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.mvrx.withState
import im.vector.app.core.di.ActiveSessionHolder
import im.vector.app.core.dialogs.GalleryOrCameraDialogHelper
import im.vector.app.core.extensions.singletonEntryPoint
import im.vector.app.core.resources.ColorProvider
import im.vector.app.databinding.FragmentFtueProfilePictureBinding
import im.vector.app.features.home.AvatarRenderer
import im.vector.app.features.onboarding.OnboardingAction
import im.vector.app.features.onboarding.OnboardingViewState
import org.matrix.android.sdk.api.util.MatrixItem
import javax.inject.Inject

class FtueAuthChooseProfilePictureFragment @Inject constructor(
        private val activeSessionHolder: ActiveSessionHolder,
        colorProvider: ColorProvider
) : AbstractFtueAuthFragment<FragmentFtueProfilePictureBinding>(), GalleryOrCameraDialogHelper.Listener {

    private val galleryOrCameraDialogHelper = GalleryOrCameraDialogHelper(this, colorProvider)
    private val avatarRenderer: AvatarRenderer by lazy { requireContext().singletonEntryPoint().avatarRenderer() }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFtueProfilePictureBinding {
        return FragmentFtueProfilePictureBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        views.profilePictureView.setOnClickListener {
            galleryOrCameraDialogHelper.show()
        }

        views.profilePictureSubmit.setOnClickListener {
            withState(viewModel) {
                viewModel.handle(OnboardingAction.SaveSelectedProfilePicture)
            }
        }

        views.profilePictureSkip.setOnClickListener { viewModel.handle(OnboardingAction.UpdateProfilePictureSkipped) }
    }

    override fun updateWithState(state: OnboardingViewState) {
        views.profilePictureSubmit.isEnabled = state.personalizationState.selectedPictureUri != null

        val session = activeSessionHolder.getActiveSession()
        val matrixItem = MatrixItem.UserItem(
                id = session.myUserId,
                displayName = state.personalizationState.displayName ?: ""
        )
        avatarRenderer.render(matrixItem, localUri = state.personalizationState.selectedPictureUri, imageView = views.profilePictureView)
    }

    override fun onImageReady(uri: Uri?) {
        if (uri == null) {
            Toast.makeText(requireContext(), "Cannot retrieve cropped value", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.handle(OnboardingAction.ProfilePictureSelected(uri))
        }
    }

    override fun resetViewModel() {
        // Nothing to do
    }
}
