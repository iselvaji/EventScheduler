package com.taskwork.eventscheduler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taskwork.eventscheduler.presentation.eventschedule.EventScheduleScreen
import com.taskwork.eventscheduler.presentation.eventslist.EventsListScreen
import com.taskwork.eventscheduler.presentation.navigation.Route
import com.taskwork.eventscheduler.ui.theme.EventSchedulerTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventSchedulerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.EVENT_LIST
                    ) {
                        composable(Route.EVENT_LIST) {
                            EventsListScreen(
                                getViewModel(),
                                onNextClick = {
                                    println("onNextClick in main ............")
                                    navController.navigate(Route.EVENT_SCHEDULE)
                                }
                            )
                        }
                        composable(Route.EVENT_SCHEDULE) {
                            EventScheduleScreen(
                                getViewModel(),
                                onNextClick = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
