/*
 * Copyright 2017-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.koin.core.instance

import org.koin.core.Koin
import org.koin.core.definition.BeanDefinition

/**
 * Factory Instance Holder
 *
 * @author Arnaud Giuliani
 */
class FactoryInstanceFactory<T>(koin: Koin, beanDefinition: BeanDefinition<T>) :
    InstanceFactory<T>(koin, beanDefinition) {

    override fun isCreated(): Boolean = false

    override fun drop() {
        beanDefinition.callbacks.onClose?.invoke(null)
    }

    override fun get(context: InstanceContext): T {
        return create(context)
    }
}