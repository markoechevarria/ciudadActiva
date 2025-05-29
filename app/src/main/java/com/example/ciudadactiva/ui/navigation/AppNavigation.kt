package com.example.ciudadactiva.ui.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ciudadactiva.ui.screen.auth.AuthViewModel
import com.example.ciudadactiva.ui.screen.auth.LoginScreen
import com.example.ciudadactiva.ui.screen.auth.RegisterScreen
import com.example.ciudadactiva.ui.screen.home.HomeScreen
import com.example.ciudadactiva.ui.screen.report.ReportViewModel
import com.example.ciudadactiva.ui.screens.home.MisReportsScreen
import com.example.ciudadactiva.ui.screens.report.CategoryScreen
import com.example.ciudadactiva.ui.screens.report.IAResultScreen
import com.example.ciudadactiva.ui.screens.report.LocationScreen
import com.example.ciudadactiva.ui.screens.report.PhotoScreen
import com.example.ciudadactiva.ui.screens.report.SubmitScreen
import com.example.ciudadactiva.ui.screens.report.SuccessScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {

        // — Auth —
        composable("login") {
            val authVm: AuthViewModel = viewModel()
            LoginScreen(
                authViewModel = authVm,
                onLoginSuccess = { navController.navigate("home") },
                onRegisterClick = { navController.navigate("register") }
            )
        }

        composable("register") {
            val authVm: AuthViewModel = viewModel()
            RegisterScreen(
                authViewModel = authVm,
                onRegistered = { navController.popBackStack() }
            )
        }

        // — Home —
        composable("home") {
            HomeScreen(
                onDrawerClick = { /* abrir tu Drawer */ }
            )
        }
        composable("home/misreports") {
            MisReportsScreen(viewModel())
        }

        // — Report Flow —
        composable("report/category") {
            val reportVm: ReportViewModel = viewModel()
            CategoryScreen(
                viewModel = reportVm,
                onNext = { navController.navigate("report/photo") }
            )
        }
        composable("report/photo") {
            val reportVm: ReportViewModel = viewModel()
            PhotoScreen(
                viewModel = reportVm,
                onNext = {
                    navController.navigate(
                        if (reportVm.iaFlow) "report/iaresult" else "report/location"
                    )
                }
            )
        }
        composable("report/iaresult") {
            val reportVm: ReportViewModel = viewModel()
            IAResultScreen(
                viewModel = reportVm,
                onNext = { navController.navigate("report/location") }
            )
        }
        composable("report/location") {
            val reportVm: ReportViewModel = viewModel()
            LocationScreen(
                viewModel = reportVm,
                onNext = { navController.navigate("report/submit") }
            )
        }
        composable("report/submit") {
            val reportVm: ReportViewModel = viewModel()
            SubmitScreen(
                viewModel = reportVm,
                onSubmitSuccess = { navController.navigate("report/success") }
            )
        }
        composable("report/success") {
            SuccessScreen(
                onDone = { navController.popBackStack("home", inclusive = false) }
            )
        }
    }
}
