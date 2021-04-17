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
package org.koin.androidx.experimental.dsl

import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.dsl.setIsViewModel
import org.koin.core.definition.BeanDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ScopeDSL
import org.koin.experimental.builder.create

/**
 * ViewModel DSL Extension
 * Allow to declare a ViewModel - be later inject into Activity/Fragment with dedicated injector
 *
 * @author Arnaud Giuliani
 *
 * @param qualifier - definition qualifier
 * @param override - allow definition override
 */
inline fun <reified T : ViewModel> ScopeDSL.viewModel(
        qualifier: Qualifier? = null,
        override: Boolean = false
): BeanDefinition<T> {
    val factory = factory(qualifier, override) { create<T>() }
    factory.setIsViewModel()
    return factory
}