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

import android.content.res.TypedArray
import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import br.com.zup.beagle.R
import br.com.zup.beagle.android.components.layout.NavigationBar
import br.com.zup.beagle.android.view.BeagleActivity
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Given a toolbar")
class ToolbarTextManagerTest {

    private val toolbarTextManager = mockk<ToolbarTextManager>()
    private val contextMock = mockk<BeagleActivity>(relaxed = true)
    private val navigationBarMock = mockk<NavigationBar>()
    private val textAppearanceMock = 1
    private val id = R.id.beagle_toolbar_text
    private val toolbarMock = mockk<Toolbar>()
    private val textViewMock = mockk<TextView>()
    private var typedArrayMock = mockk<TypedArray>()
    private val contentInsetZero = 0
    private val contentInsetLeftZero = 0
    private val contentInsetRightZero = 0
    private val gravity = Gravity.CENTER
    private val maxLines = 1
    private val ellipsize = TextUtils.TruncateAt.END
    private val title = "title"

    @Test
    fun `should be verify if the passed id matches the id of the textView`() {
        //given
        every { contextMock.getToolbar() } returns toolbarMock
        every {
            toolbarTextManager.generateTitle(contextMock, navigationBarMock, textAppearanceMock)
        } returns textViewMock
        every { toolbarMock.addView(textViewMock) } just runs
        every { toolbarTextManager.centerTitle(toolbarMock, textViewMock) } just runs
        every { toolbarMock.contentInsetStartWithNavigation = contentInsetZero } just runs
        every { toolbarMock.setContentInsetsAbsolute(contentInsetLeftZero, contentInsetRightZero) } just runs

        //when
        every { textViewMock.id } returns id

        //then
        Assertions.assertEquals(textViewMock.id, id)
    }

    @Test
    fun `should be verified if the layout passed corresponds to the layout defined for the textView`() {
        //given
        every { contextMock.getToolbar() } returns toolbarMock
        every {
            toolbarTextManager.generateTitle(contextMock, navigationBarMock, textAppearanceMock)
        } returns textViewMock
        every { toolbarMock.addView(textViewMock) } just runs
        every { toolbarTextManager.centerTitle(toolbarMock, textViewMock) } just runs
        every { toolbarMock.contentInsetStartWithNavigation = contentInsetZero } just runs
        every { toolbarMock.setContentInsetsAbsolute(contentInsetLeftZero, contentInsetRightZero) } just runs

        //when
        every { textViewMock.gravity } returns Gravity.CENTER
        every { textViewMock.maxLines } returns 1
        every { textViewMock.ellipsize } returns TextUtils.TruncateAt.END

        //then
        Assertions.assertEquals(textViewMock.gravity, gravity)
        Assertions.assertEquals(textViewMock.maxLines, maxLines)
        Assertions.assertEquals(textViewMock.ellipsize, ellipsize)
    }

    @Test
    fun `check that the title of the toolbar matches the title that has been defined`() {
        //given
        every { contextMock.getToolbar() } returns toolbarMock
        every { typedArrayMock.getBoolean(R.styleable.BeagleToolbarStyle_centerTitle, false) } returns true
        every {
            toolbarTextManager.generateTitle(contextMock, navigationBarMock, textAppearanceMock)
        } returns textViewMock
        every { toolbarMock.addView(textViewMock) } just runs
        every { toolbarTextManager.centerTitle(toolbarMock, textViewMock) } just runs
        every { toolbarMock.contentInsetStartWithNavigation = contentInsetZero } just runs
        every { toolbarMock.setContentInsetsAbsolute(contentInsetLeftZero, contentInsetRightZero) } just runs

        //when
        every { toolbarMock.title } returns title

        //then
        Assertions.assertEquals(toolbarMock.title, title)
    }
}