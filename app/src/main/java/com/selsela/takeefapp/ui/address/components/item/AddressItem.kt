package com.selsela.takeefapp.ui.address

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.Place
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.DividerColor
import com.selsela.takeefapp.ui.theme.FavBg
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Preview
@Composable
 fun AddressItem(
    place: Place,
    selectAddress: (Place, Int) -> Unit,
    updateSheetSelected: (Place,Int) -> Unit,
    onSelect: () -> Unit
) {
    var isFav by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                selectAddress(place, if (isFav) 1 else 0)
                updateSheetSelected(place, if (isFav) 1 else 0)
                onSelect()
            }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "",
                modifier = Modifier.padding(end = 6.6.dp, top = 20.dp)
            )
            Column(
                modifier = Modifier
                    .paddingTop(20)
                    .weight(1f)
            )
            {
                Text(
                    text = place.address ?: "",
                    style = text12,
                    color = TextColor
                )
            }
            IconButton(
                modifier = Modifier
                    .paddingTop(15)
                    .clip(CircleShape)
                    .background(FavBg)
                    .size(30.dp),
                onClick = {
                    isFav = !isFav
                }
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.favorite
                    ),
                    contentDescription = "",
                    colorFilter = if (isFav) ColorFilter.tint(Red) else ColorFilter.tint(
                        SecondaryColor
                    )
                )
            }


        }

        Divider(
            Modifier
                .padding(top = 14.dp)
                .fillMaxWidth(),
            color = DividerColor,
            thickness = 1.dp
        )
    }
}
