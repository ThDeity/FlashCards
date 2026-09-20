package com.example.flashcards.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flashcards.addSet.AddSet
import com.example.flashcards.createCard.CreatingCard
import com.example.flashcards.list.FlashcardsSetListScreenWithViewModel
import com.example.flashcards.observeSet.ObserveSet

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ){
        composable("list"){
            FlashcardsSetListScreenWithViewModel(
                onAddClick = { navController.navigate("addSet") },
                onEdit = { setId -> navController.navigate("observe/$setId") },
                onAdd = { setId -> navController.navigate("addCard/$setId") }
            )
        }

        composable("addSet") {
            AddSet(
                onDone = { navController.popBackStack() }
            )
        }

        composable(
            route = "addCard/{setId}",
            arguments = listOf(navArgument("setId") { type = NavType.LongType })
        ) { entry ->
            val setId = entry.arguments?.getLong("setId") ?: 0L
            CreatingCard(
                setsId = setId,
                onDone = { navController.popBackStack() }
            )
        }

        composable(
            route = "observe/{setId}",
            arguments = listOf(navArgument("setId") { type = NavType.LongType })
        ) { backStackEntry ->
            val setId = backStackEntry.arguments?.getLong("setId") ?: 0L
            ObserveSet(
                setId = setId,
                onAdd = { setId -> navController.navigate("addCard/$setId") },
                onDone = { navController.popBackStack() }
            )
        }
    }
}