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

package com.holedo.app.features.autocomplete.command

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.holedo.app.R
import com.holedo.app.core.epoxy.ClickListener
import com.holedo.app.core.epoxy.VectorEpoxyHolder
import com.holedo.app.core.epoxy.VectorEpoxyModel
import com.holedo.app.core.epoxy.onClick

@EpoxyModelClass(layout = R.layout.item_autocomplete_command)
abstract class AutocompleteCommandItem : VectorEpoxyModel<AutocompleteCommandItem.Holder>() {

    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var parameters: String? = null

    @EpoxyAttribute
    var description: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        holder.nameView.text = name
        holder.parametersView.text = parameters
        holder.descriptionView.text = description
    }

    class Holder : VectorEpoxyHolder() {
        val nameView by bind<TextView>(R.id.commandName)
        val parametersView by bind<TextView>(R.id.commandParameter)
        val descriptionView by bind<TextView>(R.id.commandDescription)
    }
}