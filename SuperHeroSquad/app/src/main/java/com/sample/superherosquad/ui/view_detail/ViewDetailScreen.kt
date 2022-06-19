package com.sample.superherosquad.ui.view_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.superherosquad.R
import com.sample.superherosquad.model.local_source.CharacterEntity
import com.sample.superherosquad.ui.components.CommonProgressDialog
import com.sample.superherosquad.ui.theme.*
import com.sample.superherosquad.viewmodel.DetailViewModel
import timber.log.Timber

/**
 * Detail screen for showing super hero details.
 **/
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    onClosed: () -> Unit
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    val detailState = detailViewModel.uiDetailState.value

    val detailItem = detailState.data?.data?.results?.get(0)

    val characterFromDb =
        detailViewModel.characterFromDb?.collectAsState(initial = CharacterEntity(id = 0, characterName = ""))?.value

    CommonProgressDialog(showDialog = detailState.isLoading)

    val selectedButton = remember {
        mutableStateOf(false)
    }

    if (characterFromDb?.id != null && detailItem!=null) {
        selectedButton.value = characterFromDb.id == detailItem.id
    }

    val scaffoldState = rememberScaffoldState()

    Timber.i("${detailState.data}")

    if(detailState.isError){
        LaunchedEffect(Unit){
            scaffoldState.snackbarHostState.showSnackbar(message = detailState.errorMessage)
        }
    }

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        if (detailItem != null) {
            Column(modifier = Modifier.padding(paddingValues)) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${detailItem.thumbnail?.path}.${detailItem.thumbnail?.extension}")
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(id = R.string.image_desc),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(screenWidth)
                            .fillMaxWidth(),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = stringResource(id = R.string.close),
                        modifier = Modifier
                            .padding(start = 21.dp, top = 45.dp, end = 21.dp, bottom = 21.dp)
                            .clickable { onClosed() }
                    )
                }
                Text(
                    text = detailItem.name!!,
                    fontSize = 34.sp,
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                    color = TextColor,
                    lineHeight = 40.sp,
                    fontFamily = Roboto
                )
                Button(
                    onClick = {
                        if(selectedButton.value){
                            detailItem.id?.let { detailViewModel.deleteCharacter(characterId = it) }
                            selectedButton.value = false
                        }else{
                            detailViewModel.insertCharacter(characterEntity = detailItem.toCharacterEntity())
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = if (selectedButton.value) ButtonDefaults.buttonColors(
                        backgroundColor = DangerRed,
                        contentColor = Color.White
                    ) else ButtonDefaults.buttonColors(
                        backgroundColor = Purple700,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (selectedButton.value) 
                            stringResource(id = R.string.fire_from_squad) 
                        else stringResource(id = R.string.hire_to_squad),
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontFamily = Roboto
                    )
                }

                Text(
                    text = detailItem.description!!,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                    lineHeight = 24.sp,
                    fontFamily = Roboto,
                    color = TextMidColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen {}
}