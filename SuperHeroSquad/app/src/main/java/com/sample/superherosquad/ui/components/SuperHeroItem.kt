package com.sample.superherosquad.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.superherosquad.model.remote_source.model.ResultsItem
import com.sample.superherosquad.ui.theme.BorderGray
import com.sample.superherosquad.ui.theme.DividerGray
import com.sample.superherosquad.ui.theme.Roboto
import com.sample.superherosquad.ui.theme.TextColor

/**
 * Super hero item cell component for super hero list.
 **/
@Composable
fun SuperHeroItem(dataItem: ResultsItem, onClicked: (characterId: Int) -> Unit) {
    Column {
        Row(modifier = Modifier
            .clickable {
                onClicked(dataItem.id!!)
            }
            .fillMaxWidth()
            .padding(16.dp)
            .height(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${dataItem.thumbnail?.path}.${dataItem.thumbnail?.extension}")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .border(width = 1.dp, color = BorderGray, shape = CircleShape)
            )
            Text(
                text = dataItem.name.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp),
                color = TextColor,
                fontFamily = Roboto
            )
        }
        Divider(
            color = DividerGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 66.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSuperHeroItem() {
    SuperHeroItem(dataItem = ResultsItem(), onClicked = {})
}