package com.selsela.takeefapp.ui.order.special

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.order.cell.DateView
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.Gray2
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun SpecialOrderDetailsView() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(horizontal = 19.dp)
    ) {

        Card(
            Modifier
                .padding(bottom = 8.4.dp, top = 14.dp)
                .fillMaxSize()
                .padding(bottom = 8.4.dp)

        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                columns = GridCells.Fixed(3),
                userScrollEnabled = false,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item(
                    span = {
                        GridItemSpan(3)
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 21.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "رقم الطلب",
                                style = text11,
                                color = SecondaryColor
                            )
                            Text(
                                text = "#12342",
                                style = text16Bold,
                                color = TextColor,
                                modifier = Modifier.paddingTop(9)
                            )
                        }
                        DateView()

                    }
                }
                item(
                    span = {
                        GridItemSpan(3)
                    }
                ) {

                    Row(
                        Modifier
                            .paddingTop(9)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "عنوان : ",
                            style = text11,
                            color = SecondaryColor
                        )
                        Text(
                            text = "اسم العنوان يوضع هنا في هذه المساحة",
                            style = text12,
                            color = TextColor
                        )
                    }
                }
                item(
                    span = {
                        GridItemSpan(3)
                    }
                ) {
                    Text(
                        text = "التفاصيل",
                        style = text11,
                        color = SecondaryColor,
                        modifier = Modifier.paddingTop(8)
                    )
                }
                item(
                    span = {
                        GridItemSpan(3)
                    }
                ) {

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = stringResource(id = R.string.temp_details),
                            style = text12,
                            color = TextColor.copy(0.56f),
                            modifier = Modifier
                                .paddingTop(8)
                                .weight(1f)
                        )

                    }

                }
                item(
                    span = {
                        GridItemSpan(3)
                    }
                ) {
                    Text(
                        text = "المرفقات",
                        style = text11,
                        color = SecondaryColor,
                        modifier = Modifier.paddingTop(9)
                    )


                }

                items(5) { photo ->
                    AttachmentItem()
                }
            }

        }

    }
}

@Composable
fun AttachmentItem() {
    Image(
        painter = painterResource(id = R.drawable.attachment1),
        contentDescription = "",
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = Gray2, RoundedCornerShape(4.dp)),
        contentScale = ContentScale.FillBounds
    )
}
