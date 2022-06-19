package com.sample.superherosquad.ui.components

/**
 * Sealed class for managing screen routes.
 **/
sealed class AppRoute(var route:String) {
    object HomeScreen: AppRoute("home")
    object DetailScreen: AppRoute("detail")
}