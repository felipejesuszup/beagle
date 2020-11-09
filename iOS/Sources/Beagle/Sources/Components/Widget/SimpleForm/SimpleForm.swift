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

public struct SimpleForm: ServerDrivenComponent, HasContext, AutoInitiableAndDecodable {
    
    public var context: Context?
    public let onSubmit: [Action]?
    public let children: [ServerDrivenComponent]
    public var widgetProperties: WidgetProperties
    
// sourcery:inline:auto:SimpleForm.Init
    public init(
        context: Context? = nil,
        onSubmit: [Action]? = nil,
        children: [ServerDrivenComponent],
        widgetProperties: WidgetProperties = WidgetProperties()
    ) {
        self.context = context
        self.onSubmit = onSubmit
        self.children = children
        self.widgetProperties = widgetProperties
    }
// sourcery:end
    
    public init(
        context: Context? = nil,
        onSubmit: [Action]? = nil,
        widgetProperties: WidgetProperties = WidgetProperties(),
        @ChildrenBuilder
        _ children: () -> [ServerDrivenComponent]
    ) {
        self.init(context: context, onSubmit: onSubmit, children: children(), widgetProperties: widgetProperties)
    }
    
    public init(
        context: Context? = nil,
        onSubmit: [Action]? = nil,
        widgetProperties: WidgetProperties = WidgetProperties(),
        @ChildBuilder
        _ children: () -> ServerDrivenComponent
    ) {
        self.init(context: context, onSubmit: onSubmit, children: [children()], widgetProperties: widgetProperties)
    }
}