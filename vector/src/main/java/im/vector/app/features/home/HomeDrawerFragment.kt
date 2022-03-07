/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.features.home


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.app.ActivityOptionsCompat

import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import im.vector.app.BuildConfig
import im.vector.app.R
import im.vector.app.core.extensions.observeK
import im.vector.app.core.extensions.replaceChildFragment
import im.vector.app.core.platform.VectorBaseFragment
import im.vector.app.core.utils.startSharePlainTextIntent
import im.vector.app.databinding.FragmentHomeDrawerBinding
import im.vector.app.features.analytics.plan.MobileScreen
import im.vector.app.features.settings.VectorPreferences
import im.vector.app.features.settings.VectorSettingsActivity
import im.vector.app.features.spaces.SpaceListFragment
import im.vector.app.features.usercode.UserCodeActivity
import im.vector.app.features.workers.signout.SignOutUiWorker
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.util.toMatrixItem
import javax.inject.Inject

class HomeDrawerFragment @Inject constructor(
        private val session: Session,
        private val vectorPreferences: VectorPreferences,
        private val avatarRenderer: AvatarRenderer
) : VectorBaseFragment<FragmentHomeDrawerBinding>() {

    private lateinit var sharedActionViewModel: HomeSharedActionViewModel

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeDrawerBinding {
        return FragmentHomeDrawerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedActionViewModel = activityViewModelProvider.get(HomeSharedActionViewModel::class.java)

        if (savedInstanceState == null) {
            replaceChildFragment(R.id.homeDrawerGroupListContainer, SpaceListFragment::class.java)
        }
        session.getUserLive(session.myUserId).observeK(viewLifecycleOwner) { optionalUser ->
            val user = optionalUser?.getOrNull()
            if (user != null) {
                avatarRenderer.render(user.toMatrixItem(), views.homeDrawerHeaderAvatarView)
                views.homeDrawerUsernameView.text = user.displayName
                views.homeDrawerUserIdView.text = user.userId
            }
        }
        // Profile
        views.homeDrawerHeader.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            navigator.openSettings(requireActivity(), directAccess = VectorSettingsActivity.EXTRA_DIRECT_ACCESS_GENERAL)
        }
        // Settings
        views.homeDrawerHeaderSettingsView.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            navigator.openSettings(requireActivity())
        }
        // Sign out
        views.homeDrawerHeaderSignoutView.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            SignOutUiWorker(requireActivity()).perform()
        }

        views.homeDrawerQRCodeButton.debouncedClicks {
            UserCodeActivity.newIntent(requireContext(), sharedActionViewModel.session.myUserId).let {
                val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                requireActivity(),
                                views.homeDrawerHeaderAvatarView,
                                ViewCompat.getTransitionName(views.homeDrawerHeaderAvatarView) ?: ""
                        )
                startActivity(it, options.toBundle())
            }
        }

        views.community1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")

//            val url = "https://community.holedo.im/?u=$calci14"
//            // initializing object for custom chrome tabs.
//            // initializing object for custom chrome tabs.
//            val customIntent = CustomTabsIntent.Builder()
//
//            // below line is setting toolbar color
//            // for our custom chrome tab.
//
//            // below line is setting toolbar color
//            // for our custom chrome tab.
//            customIntent.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.palette_element_green))
//
//            customIntent.setStartAnimations(requireContext(),android.R.anim.slide_in_left,android.R.anim.slide_out_right)  //animation for this
//            // we are calling below method after
//            // setting our toolbar color.
//
//            // we are calling below method after
//            // setting our toolbar color.
//            openCustomTab(requireActivity(), customIntent.build(), Uri.parse(url))

            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://community.holedo.im/?u=$calci14"))
            startActivity(i)
        }

        views.about1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(" https://about.holedo.im/?u=$calci14"))
            startActivity(i)
        }


        views.Badges1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://badges.holedo.im/?u=$calci14"))
            startActivity(i)
        }

        views.help1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://jobs.holedo.im/?u=$calci14"))
            startActivity(i)
        }


        views.newsbutton1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://news.holedo.im/?u=$calci14"))
            startActivity(i)
        }

        views.jobsbutton1.setOnClickListener {
            val calci123 = sharedActionViewModel.session.myUserId
            val calci135 = calci123.replace(":holedo.com", "")
            val calci147 = calci135.replace("@", "")
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://jobs.holedo.im/?u=$calci147"))
            startActivity(i)
        }

        views.profileebutton1.setOnClickListener {

            val usernameorigaa = sharedActionViewModel.session.myUserId
            val usernamefinaa = usernameorigaa.replace(":holedo.com", "")
            val usernamefinba = usernamefinaa.replace("@", "")

            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://profile.holedo.im/$usernamefinba"))
            startActivity(i)

        }

        views.homeDrawerInviteFriendButton.debouncedClicks {
            session.permalinkService().createPermalink(sharedActionViewModel.session.myUserId)?.let { permalink ->
                analyticsTracker.screen(MobileScreen(screenName = MobileScreen.ScreenName.InviteFriends))
                val text = getString(R.string.invite_friends_text, permalink)

                startSharePlainTextIntent(
                        fragment = this,
                        activityResultLauncher = null,
                        chooserTitle = getString(R.string.invite_friends),
                        text = text,
                        extraTitle = getString(R.string.invite_friends_rich_title)
                )
            }
        }

        // Debug menu
        views.homeDrawerHeaderDebugView.isVisible = BuildConfig.DEBUG && vectorPreferences.developerMode()
        views.homeDrawerHeaderDebugView.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            navigator.openDebug(requireActivity())
        }
    }

//    fun openCustomTab(activity: Activity, customTabsIntent: CustomTabsIntent, uri: Uri?) {
//        // package name is the default package
//        // for our custom chrome tab
//        val packageName = "com.android.chrome"
//        if (packageName != null) {
//
//            // we are checking if the package name is not null
//            // if package name is not null then we are calling
//            // that custom chrome tab with intent by passing its
//            // package name.
//            customTabsIntent.intent.setPackage(packageName)
//
//            // in that custom tab intent we are passing
//            // our url which we have to browse.
//            customTabsIntent.launchUrl(activity, uri!!)
//        } else {
//            // if the custom tabs fails to load then we are simply
//            // redirecting our user to users device default browser.
//            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
//        }
//    }

}
