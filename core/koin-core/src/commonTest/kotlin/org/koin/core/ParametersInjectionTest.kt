package org.koin.core

import kotlin.test.assertEquals
import kotlin.test.Test
import org.koin.Simple
import org.koin.core.logger.Level
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class ParametersInjectionTest {

    @Test
    fun `can create a single with parameters`() {

        val app = koinApplication {
            modules(
                module {
                    single { (i: Int) -> Simple.MySingle(i) }
                })
        }

        val koin = app.koin
        val a: Simple.MySingle = koin.get { parametersOf(42) }

        assertEquals(42, a.id)
    }

    @Test
    fun `can create a single with parameters - resolved in graph`() {

        val app = koinApplication {
            modules(
                module {
                    factory { Simple.MySingle(get()) }
                })
        }

        val koin = app.koin
        val a42: Simple.MySingle = koin.get { parametersOf(42) }
        assertEquals(42, a42.id)
        
        val a24: Simple.MySingle = koin.get { parametersOf(24) }
        assertEquals(24, a24.id)
    }

    @Test
    fun `can create a single with parameters - using param object resolution`() {

        val app = koinApplication {
            modules(
                module {
                    single { params -> Simple.MySingle(params.get()) }
                })
        }

        val koin = app.koin
        val a: Simple.MySingle = koin.get { parametersOf(42) }

        assertEquals(42, a.id)
    }

    @Test
    fun `can create a single with nullable parameters`() {

        val app = koinApplication {
            printLogger(Level.DEBUG)
            modules(
                    module {
                        single { (i: Int?) -> Simple.MySingleWithNull(i) }
                    })
        }

        val koin = app.koin
        val a: Simple.MySingleWithNull = koin.get { parametersOf(null) }

        assertEquals(null, a.id)
    }

    @Test
    fun `can get a single created with parameters - no need of give it again`() {

        val app = koinApplication {
            printLogger(Level.DEBUG)
            modules(
                module {
                    single { (i: Int) -> Simple.MySingle(i) }
                })
        }

        val koin = app.koin
        val a: Simple.MySingle = koin.get { parametersOf(42) }

        assertEquals(42, a.id)

        val a2: Simple.MySingle = koin.get()

        assertEquals(42, a2.id)
    }

    @Test
    fun `can create factories with params`() {

        val app = koinApplication {
            modules(
                module {
                    factory { (i: Int) -> Simple.MyIntFactory(i) }
                })
        }

        val koin = app.koin
        val a: Simple.MyIntFactory = koin.get { parametersOf(42) }
        val a2: Simple.MyIntFactory = koin.get { parametersOf(43) }

        assertEquals(42, a.id)
        assertEquals(43, a2.id)
    }

    @Test
    fun `chained factory injection`() {
        val koin = koinApplication {
            printLogger(Level.DEBUG)
            modules(
                module {
                    factory { (i: Int) -> Simple.MyIntFactory(i) }
                    factory { (s: String) -> Simple.MyStringFactory(s) }
                    factory { (i: Int, s: String) ->
                        Simple.AllFactory(
                            get { parametersOf(i) },
                            get { parametersOf(s) })
                    }
                })
        }.koin

        val f = koin.get<Simple.AllFactory> { parametersOf(42, "42") }

        assertEquals(42, f.ints.id)
        assertEquals("42", f.strings.s)
    }

    @Test
    fun `inject in graph`() {
        val app = koinApplication {
            printLogger(Level.DEBUG)
            modules(
                module {
                    single { Simple.MySingle(get()) }
                })
        }

        val koin = app.koin
        val a: Simple.MySingle = koin.get { parametersOf(42) }

        assertEquals(42, a.id)
    }

    @Test
    fun `chained factory injection - graph`() {
        val koin = koinApplication {
            printLogger(Level.DEBUG)
            modules(
                module {
                    factory { Simple.MyIntFactory(get()) }
                    factory { Simple.MyStringFactory(get()) }
                    factory { (i: Int, s: String) ->
                        Simple.AllFactory(
                            get { parametersOf(i) },
                            get { parametersOf(s) })
                    }
                })
        }.koin

        val f = koin.get<Simple.AllFactory> { parametersOf(42, "42") }

        assertEquals(42, f.ints.id)
        assertEquals("42", f.strings.s)
    }
}