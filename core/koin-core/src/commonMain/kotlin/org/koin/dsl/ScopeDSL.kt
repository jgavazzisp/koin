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
package org.koin.dsl

import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.definition.Definitions
import org.koin.core.definition.Options
import org.koin.core.module.addDefinition
import org.koin.core.qualifier.Qualifier

/**
 * DSL Scope Definition
 */
@Suppress("UNUSED_PARAMETER")
class ScopeDSL(val scopeQualifier: Qualifier, val definitions: HashSet<BeanDefinition<*>>) {

    @Deprecated("Can't use Single in a scope. Use Scoped instead", level = DeprecationLevel.ERROR)
    inline fun <reified T> single(
        qualifier: Qualifier? = null,
        override: Boolean = false,
        noinline definition: Definition<T>
    ): BeanDefinition<T> {
        error("Scoped definition is deprecated and has been replaced with Single scope definitions")
    }

    inline fun <reified T> scoped(
        qualifier: Qualifier? = null,
        override: Boolean = false,
        noinline definition: Definition<T>
    ): BeanDefinition<T> {
        val def = Definitions.createSingle(
            qualifier,
            definition,
            Options(isCreatedAtStart = false, override = override),
            scopeQualifier = scopeQualifier
        )
        definitions.addDefinition(def)
        return def
    }

    inline fun <reified T> factory(
        qualifier: Qualifier? = null,
        override: Boolean = false,
        noinline definition: Definition<T>
    ): BeanDefinition<T> {
        val def = Definitions.createFactory(
            qualifier,
            definition,
            Options(isCreatedAtStart = false, override = override),
            scopeQualifier = scopeQualifier
        )
        definitions.addDefinition(def)
        return def
    }
}