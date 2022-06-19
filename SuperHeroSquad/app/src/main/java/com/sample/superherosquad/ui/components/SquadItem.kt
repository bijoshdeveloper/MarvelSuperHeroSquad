package com.sample.superherosquad.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.superherosquad.model.local_source.CharacterEntity
import com.sample.superherosquad.ui.theme.BorderGray
import com.sample.superherosquad.ui.theme.Roboto
import com.sample.superherosquad.ui.theme.TextColor

/**
 * Squad item cell component for showing my squad member list.
 **/
@Composable
fun SquadItem(
    characterEntity: CharacterEntity,
    onClicked: (id: Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                characterEntity.id?.let { onClicked(it) }
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(characterEntity.characterThumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)
                .border(width = 1.dp, color = BorderGray, shape = CircleShape),
        )
        Text(
            text = characterEntity.characterName,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 5.dp)
                .width(64.dp),
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
            softWrap = true,
            maxLines = 2,
            color = TextColor,
            fontFamily = Roboto
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSquadItem() {
    SquadItem(characterEntity = CharacterEntity(id = 0, characterName = "Name"), onClicked = {})
}