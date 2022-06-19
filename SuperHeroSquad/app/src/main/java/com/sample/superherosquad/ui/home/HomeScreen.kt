package com.sample.superherosquad.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.superherosquad.R
import com.sample.superherosquad.ui.components.AppRoute
import com.sample.superherosquad.ui.components.CommonProgressDialog
import com.sample.superherosquad.ui.components.SquadItem
import com.sample.superherosquad.ui.components.SuperHeroItem
import com.sample.superherosquad.ui.theme.Roboto
import com.sample.superherosquad.ui.theme.TextColor
import com.sample.superherosquad.viewmodel.HomeViewModel

/**
 * Home screen of the application.
 **/
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onDetailClicked: (detailRoute: String) -> Unit
) {
    val uiHomeState = homeViewModel.uiHomeState.value

    CommonProgressDialog(showDialog = uiHomeState.isLoading)

    val itemList = uiHomeState.data?.data?.results

    val squadList = homeViewModel.squadFromDb.collectAsState(initial = emptyList()).value

    val scaffoldState = rememberScaffoldState()

    if(uiHomeState.isError){
        LaunchedEffect(Unit){
            scaffoldState.snackbarHostState.showSnackbar(message = uiHomeState.errorMessage)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(modifier = Modifier.height(64.dp)) {
                Text(
                    text = stringResource(id = R.string.squad_maker),
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 12.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                        .height(24.dp),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal
                )
            }
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)) {
            if (squadList.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.my_squad),
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 10.dp),
                    color = TextColor,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 12.dp)
                ) {
                    items(squadList) { squadItem ->
                        SquadItem(characterEntity = squadItem, onClicked = { id ->
                            onDetailClicked(AppRoute.DetailScreen.route + "/$id")
                        })
                    }
                }
            }
            LazyColumn(state = rememberLazyListState()) {
                itemList?.let {
                    items(it.size) { index ->
                        SuperHeroItem(dataItem = itemList[index]!!, onClicked = { id ->
                            onDetailClicked(AppRoute.DetailScreen.route + "/$id")
                        })
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(onDetailClicked = {})
}