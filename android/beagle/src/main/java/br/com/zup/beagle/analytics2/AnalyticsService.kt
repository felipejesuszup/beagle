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

package br.com.zup.beagle.analytics2

import android.view.View
import br.com.zup.beagle.android.action.ActionAnalytics
import br.com.zup.beagle.android.logger.BeagleMessageLogs
import br.com.zup.beagle.android.widget.RootView
import java.util.*

internal object AnalyticsService {

    private val queueOfReportsWaitingConfig: Queue<DataReport> = LinkedList()
    private var analyticsProvider: AnalyticsProvider? = null

    private lateinit var analyticsConfig: AnalyticsConfig

    fun initialConfig(analyticsProvider: AnalyticsProvider? = null) {
        this.analyticsProvider = analyticsProvider
        startSessionAndGetConfig()
    }

    private fun startSessionAndGetConfig() {
        analyticsProvider?.let { analyticsProvider ->
            analyticsProvider.startSession {
                analyticsProvider.getConfig { analyticsConfig ->
                    this@AnalyticsService.analyticsConfig = analyticsConfig
                }
                reportElementsOnQueue()
            }
        }
    }

    private fun reportElementsOnQueue() {
        while (queueIsNotEmpty()) {
            queueOfReportsWaitingConfig.remove().report()
        }
    }

    private fun queueIsNotEmpty() = !queueOfReportsWaitingConfig.isEmpty()

    fun createActionRecord(
        rootView: RootView,
        origin: View,
        action: ActionAnalytics,
        analyticsHandleEvent: AnalyticsHandleEvent? = null
    ) {
        analyticsProvider?.let {
            val dataActionReport = ActionRecordFactory.preGenerateActionAnalyticsConfig(
                rootView,
                origin,
                action,
                analyticsHandleEvent
            )
            if (isAnalyticsConfigInitialized()) {
                reportActionIfShould(dataActionReport)
            } else {
                addReportOnQueue(dataActionReport)
            }
        }
    }

    fun reportActionIfShould(dataActionReport: DataActionReport) {
        val config = createAConfigFromActionAnalyticsOrAnalyticsConfig(dataActionReport.action)
        if (shouldReport(config)) {
            analyticsProvider?.createRecord(ActionRecordFactory.generateActionAnalyticsConfig(dataActionReport, config))
        }
    }

    private fun createAConfigFromActionAnalyticsOrAnalyticsConfig(action: ActionAnalytics): ActionAnalyticsConfig {
        action.analytics?.let { actionAnalytics ->
            return ActionAnalyticsConfig(
                enable = actionAnalytics.enable,
                attributes = actionAnalytics.attributes,
                additionalEntries = actionAnalytics.additionalEntries)
        }
        return actionAnalyticsFromConfig(action)
    }

    private fun actionAnalyticsFromConfig(action: ActionAnalytics): ActionAnalyticsConfig {
        val key = action.type
        val attributeList = analyticsConfig.actions[key]
        return ActionAnalyticsConfig(enable = attributeList != null, attributes = attributeList)
    }

    private fun shouldReport(actionAnalyticsConfig: ActionAnalyticsConfig) = actionAnalyticsConfig.enable

    //screen
    fun createScreenRecord(dataScreenReport: DataScreenReport) {
        analyticsProvider?.let {
            if (isAnalyticsConfigInitialized()) {
                reportScreen(dataScreenReport)
            } else {
                addReportOnQueue(dataScreenReport)
            }
        }
    }

    private fun reportScreen(dataScreenReport: DataScreenReport) {
        if (shouldReportScreen()) {
            val screenIdentifier = dataScreenReport.screenIdentifier
            if (dataScreenReport.isLocalScreen) {
                analyticsProvider?.createRecord(
                    ScreenReportFactory.generateLocalScreenAnalyticsRecord(screenIdentifier)
                )
            } else {
                analyticsProvider?.createRecord(
                    ScreenReportFactory.generateRemoteScreenAnalyticsRecord(screenIdentifier)
                )
            }
        }
    }

    private fun isAnalyticsConfigInitialized() = this::analyticsConfig.isInitialized

    private fun shouldReportScreen() = analyticsConfig.enableScreenAnalytics ?: false

    private fun addReportOnQueue(dataReport: DataReport){
        analyticsProvider?.let{
            if(queueOfReportsWaitingConfig.size < it.getMaximumItemsInQueue()){
                queueOfReportsWaitingConfig.add(dataReport)
            }
            else{
                BeagleMessageLogs.analyticsQueueIsFull(it.getMaximumItemsInQueue())
                queueOfReportsWaitingConfig.remove()
                queueOfReportsWaitingConfig.add(dataReport)
            }
        }
    }
}
