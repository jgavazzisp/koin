---
title: Koin DSL
---

Below, the added keywords for Koin DSL.

## Getting Android context inside a Module

The `androidContext()` & `androidApplication()` functions allows you to get the `Context` instance in a Koin module, to help you simply write expression that requires the `Application` instance.

```kotlin
val appModule = module {

    // create a Presenter instance with injection of R.string.mystring resources from Android
    factory {
        MyPresenter(androidContext().resources.getString(R.string.mystring))
    }
}
```

