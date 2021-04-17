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
@file:Suppress("UNCHECKED_CAST")

package org.koin.core.instance

import org.koin.core.Koin
import org.koin.core.definition.BeanDefinition
import org.koin.core.error.InstanceCreationException
import org.koin.core.logger.Level
import org.koin.core.parameter.DefinitionParameters
import org.koin.mp.KoinPlatformTools

/**
 * Koin Instance Holder
 * create/get/release an instance of given definition
 */
abstract class InstanceFactory<T>(private val _koin: Koin, val beanDefinition: BeanDefinition<T>) {

    /**
     * Retrieve an instance
     * @param context
     * @return T
     */
    abstract fun get(context: InstanceContext): T

    /**
     * Create an instance
     * @param context
     * @return T
     */
    open fun create(context: InstanceContext): T {
        if (_koin.logger.isAt(Level.DEBUG)) {
            _koin.logger.debug("| create instance for $beanDefinition")
        }
        try {
            val parameters: DefinitionParameters = context.parameters
            context.scope.addParameters(parameters)
            val value = beanDefinition.definition(
                context.scope,
                parameters
            )
            context.scope.clearParameters()
            return value
        } catch (e: Exception) {
            val stack = KoinPlatformTools.getStackTrace(e)
            _koin.logger.error("Instance creation error : could not create instance for $beanDefinition: $stack")
            throw InstanceCreationException("Could not create instance for $beanDefinition", e)
        }
    }

    /**
     * Is instance created
     */
    abstract fun isCreated(): Boolean

    /**
     * Drop the instance
     */
    abstract fun drop()

    companion object {
        const val ERROR_SEPARATOR = "\n\t"
    }
}