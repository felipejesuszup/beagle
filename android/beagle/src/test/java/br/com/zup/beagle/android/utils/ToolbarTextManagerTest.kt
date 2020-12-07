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

import androidx.appcompat.widget.Toolbar
import br.com.zup.beagle.android.BaseTest
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
class ToolbarTextManagerTest : BaseTest() {

    @Test
    fun `should return the title that was defined`() {

//        val toolbarTextManager = ToolbarTextManager()
//        val contextMock = mockk<BeagleActivity>(relaxed = true)
//        val navigationBarMock = mockk<NavigationBar>()
//        val textAppearanceMock = 1
//        val toolbarMock = mockk<Toolbar>()
//        val contentInsetZero = 0
//        val contentInsetLeftZero = 0
//        val contentInsetRightZero = 0
//
//        every { contextMock.getToolbar() } returns toolbarMock
//        every { navigationBarMock.title } returns ""
//        every { toolbarMock.contentInsetStartWithNavigation = contentInsetZero } just runs
//        every { toolbarMock.setContentInsetsAbsolute(contentInsetLeftZero, contentInsetRightZero) } just runs
//
//        val textViewResult = toolbarTextManager.generateTitle(contextMock, navigationBarMock, textAppearanceMock, toolbarMock)
//
//        Assertions.assertEquals("", textViewResult.text)
    }
}