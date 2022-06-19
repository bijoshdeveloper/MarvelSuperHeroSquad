package com.sample.superherosquad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sample.superherosquad.ui.components.AppRoute
import com.sample.superherosquad.ui.home.HomeScreen
import com.sample.superherosquad.ui.theme.SuperHeroSquadTheme
import com.sample.superherosquad.ui.view_detail.DetailScreen
import com.sample.superherosquad.util.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity class which is the parent for all compose screens.
 **/
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SuperHeroSquadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainNavigationView()
                }
            }
        }
    }
}


@Composable
fun MainNavigationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoute.HomeScreen.route) {
        composable(route = AppRoute.HomeScreen.route) {
            HomeScreen(onDetailClicked = { route ->
                navController.navigate(route)
            })
        }

        composable(
            route = AppRoute.DetailScreen.route + "/{${Constants.CHARACTER_ID}}",
            arguments = listOf(navArgument(Constants.CHARACTER_ID) { type = NavType.IntType })

        ) {
            DetailScreen {
                navController.popBackStack()
            }
        }
    }
}