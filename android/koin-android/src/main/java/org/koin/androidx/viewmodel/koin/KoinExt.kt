package org.koin.androidx.viewmodel.koin

import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ViewModelOwnerDefinition
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

inline fun <reified T : ViewModel> Koin.viewModel(
        qualifier: Qualifier? = null,
        noinline state: BundleDefinition? = null,
        noinline owner: ViewModelOwnerDefinition,
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
        noinline parameters: ParametersDefinition? = null,
): Lazy<T> {
    return lazy(mode) { getViewModel(qualifier, state, owner, parameters) }
}

inline fun <reified T : ViewModel> Koin.getViewModel(
        qualifier: Qualifier? = null,
        noinline state: BundleDefinition? = null,
        noinline owner: ViewModelOwnerDefinition,
        noinline parameters: ParametersDefinition? = null,
): T {
    return getViewModel(qualifier, state, owner, T::class, parameters)
}

@OptIn(KoinInternalApi::class)
fun <T : ViewModel> Koin.getViewModel(
        qualifier: Qualifier? = null,
        state: BundleDefinition? = null,
        owner: ViewModelOwnerDefinition,
        clazz: KClass<T>,
        parameters: ParametersDefinition? = null,
): T {
    return scopeRegistry.rootScope.getViewModel(qualifier, state, owner, clazz, parameters)
}

@OptIn(KoinInternalApi::class)
fun <T : ViewModel> Koin.getViewModel(viewModelParameters: ViewModelParameter<T>): T {
    return scopeRegistry.rootScope.getViewModel(viewModelParameters)
}