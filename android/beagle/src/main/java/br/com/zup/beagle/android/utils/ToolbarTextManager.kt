/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
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

package br.com.zup.beagle.android.utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.core.widget.TextViewCompat
import br.com.zup.beagle.R
import br.com.zup.beagle.android.components.layout.NavigationBar

internal class ToolbarTextManager {

    companion object {
        private const val TEXT_APPEARANCE_ZERO = 0
    }

    fun generateTitle(
        context: Context,
        navigationBar: NavigationBar,
        textAppearance: Int,
        id: Int = R.id.beagle_toolbar_text
    ) = TextView(context).apply {
        this.id = id
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        this.layoutParams = params.apply {
            gravity = Gravity.CENTER
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }
        text = navigationBar.title
        if (textAppearance != TEXT_APPEARANCE_ZERO) {
            TextViewCompat.setTextAppearance(this, textAppearance)
        }
    }

    fun centerTitle(
        toolbar: Toolbar,
        titleTextView: TextView
    ) {
        toolbar.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            val idealX = ((toolbar.width - titleTextView.width) / 2).toFloat()
            val lastToolbarView = toolbar.children.find {
                it.right == toolbar.width
            }
            val lastToolbarViewStart = lastToolbarView?.left ?: 0
            if (idealX + titleTextView.width > lastToolbarViewStart) {
                val idealXAdjusted = idealX - (idealX + titleTextView.width - lastToolbarViewStart)
                titleTextView.x = idealXAdjusted
            } else {
                titleTextView.x = idealX
            }
        }
    }

}