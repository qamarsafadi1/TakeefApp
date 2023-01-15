package com.selsela.takeefapp.ui.support

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.InputEditText
import com.selsela.takeefapp.ui.theme.Gray
import com.selsela.takeefapp.ui.theme.Gray3
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text10
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Preview
@Composable
fun SupportScreen() {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.89f)
                .background(Color.White)
                .padding(start = 29.dp, end = 20.dp)
        ) {
            item {
                AdminItem()
            }
            item {
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = "29-11-2022.08:00AM",
                    style = text12,
                    color = SecondaryColor2,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            items(2){
                MeItem()
            }
        }
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 14.dp)
                .padding(bottom = 28.dp)
                .fillMaxWidth()
                .requiredHeight(93.dp)
                .background(TextColor, RoundedCornerShape(25.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MessageEditText()
            Spacer(modifier = Modifier.width(8.dp))
            Image(painter = painterResource(id = R.drawable.send), contentDescription = "")

        }
    }

}

@Composable
fun AdminItem() {

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.Center) {

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Gray3, shape = CircleShape)
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mjloho), contentDescription = "",
                    modifier = Modifier
                        .width(20.dp)
                        .height(19.dp)
                )
            }
            Text(
                text = "ادارة النظام",
                style = text10,
                color = TextColor,
                modifier = Modifier.paddingTop(6)
            )

        }
        Spacer(modifier = Modifier.width(17.dp))
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    LightBlue.copy(0.10f), RoundedCornerShape(
                        topEnd = 23.dp, topStart = 23.dp,
                        bottomEnd = 23.dp, bottomStart = 0.dp
                    )
                )
                .padding(horizontal = 18.dp, vertical = 24.dp)
        ) {
            Text(
                text = "مرحبا بك في تطبيق ام جي \n يسعدنا الرد علي استفساراتكم واقتراحاتكم ",
                style = text12,
                color = TextColor,

                )
        }
    }

}
@Composable
fun MeItem() {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(17.dp))
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    Purple40.copy(0.10f), RoundedCornerShape(
                        topEnd = 23.dp, topStart = 23.dp,
                        bottomEnd = 0.dp, bottomStart = 23.dp
                    )
                )
                .padding(horizontal = 18.dp, vertical = 24.dp)
        ) {
            Text(
                text = "مرحبا بك في تطبيق ام جي \n يسعدنا الرد علي استفساراتكم واقتراحاتكم ",
                style = text12,
                color = TextColor,

                )
        }
    }

}

@Composable
private fun MessageEditText() {
    var message by remember {
        mutableStateOf("")
    }
    InputEditText(
        text =
        message,
        modifier = Modifier.padding(
            top = 0.dp,
            start = 5.dp
        ),
        onValueChange = {
            message = it
        },
        textStyle = text12,
        hint = "كتابة نص الرسالة هنا",
        cornerRaduis = 30.dp,
        fillMax = 0.85f
    )
}