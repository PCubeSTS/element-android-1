/*
 *
 *  * Copyright 2019 New Vector Ltd
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package im.vector.riotx.features.home.createdirect

import android.os.Bundle
import com.airbnb.mvrx.fragmentViewModel
import im.vector.matrix.android.api.session.user.model.User
import im.vector.riotx.R
import im.vector.riotx.core.di.ScreenComponent
import im.vector.riotx.core.platform.VectorBaseFragment
import kotlinx.android.synthetic.main.fragment_create_direct_room.*
import javax.inject.Inject

class CreateDirectRoomFragment : VectorBaseFragment(), CreateDirectRoomController.Callback {

    override fun getLayoutResId() = R.layout.fragment_create_direct_room

    private val viewModel: CreateDirectRoomViewModel by fragmentViewModel()

    @Inject lateinit var createDirectRoomViewModelFactory: CreateDirectRoomViewModel.Factory
    @Inject lateinit var directRoomController: CreateDirectRoomController

    override fun injectWith(injector: ScreenComponent) {
        injector.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        viewModel.subscribe(this) { renderState(it) }
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        directRoomController.callback = this
        recyclerView.setController(directRoomController)
    }

    private fun renderState(state: CreateDirectRoomViewState) {
        directRoomController.setData(state)
    }

    override fun onItemClick(user: User) {
        vectorBaseActivity.notImplemented("IMPLEMENT ON USER CLICKED")
    }
}