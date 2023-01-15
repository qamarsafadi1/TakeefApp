package com.selsela.takeefapp.ui.order.rate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.InputEditText
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.common.components.RatingBar
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.UnselectedColor
import com.selsela.takeefapp.ui.theme.YellowColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text260Book
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun RateSheetContent(onConfirm: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(
                horizontal = 24.dp,
                vertical = 10.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.bottomsheet),
            contentDescription = ""
        )

        LottieAnimationView(
            raw = R.raw.star,
            modifier = Modifier
                .width(119.dp)
                .height(154.dp)
        )

        Text(
            text = "تقييم الخدمة",
            style = text260Book,
            color = Color.White,
            modifier = Modifier.paddingTop(29)
        )
        QualityRating()
        PriceRating()
        SupervisorRating()
        NoteView()
        Column(
            Modifier
                .paddingTop(27)
                .fillMaxWidth()
        ) {
            Text(
                text = "التقييم يظهر فقط للادارة ويساعدنا في تحسين الخدمة",
                style = text11,
                color = SecondaryColor2
            )
            Text(
                text = "المقدمة من قبل مراكز الصيانة",
                style = text11,
                color = SecondaryColor2,
                modifier = Modifier.paddingTop(10)
            )
        }

        ElasticButton(
            onClick = { /*TODO*/ }, title = "إرسال التقييم",
            modifier = Modifier
                .padding(vertical = 30.dp)
                .fillMaxWidth()
                .requiredHeight(48.dp)
        )
    }
}

@Composable
private fun NoteView() {
    var note by remember { mutableStateOf("") }
    InputEditText(
        text = note,
        hint = "كتابة ملاحظة",
        modifier = Modifier.padding(top = 34.dp),
        onValueChange = {
            note = it
        },
        textStyle = text12
    )
}

@Composable
private fun QualityRating() {
    Row(
        modifier = Modifier
            .paddingTop(46.3)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "جودة الخدمة ",
            style = text14,
            color = SecondaryColor2
        )
        var rating by remember { mutableStateOf(3.7f) }
        RatingBar(
            rating = rating,
            space = 2.dp,
            imageVectorEmpty = ImageVector.vectorResource(R.drawable.staricon),
            imageVectorFFilled = ImageVector.vectorResource(R.drawable.starfill),
            tintEmpty = UnselectedColor,
            tintFilled = YellowColor,
            animationEnabled = true,
            gestureEnabled = true,
            itemSize = 25.dp
        ) {
            rating = it
        }
    }
}

@Composable
private fun PriceRating() {
    Row(
        modifier = Modifier
            .paddingTop(37.3)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "سعر الخدمة",
            style = text14,
            color = SecondaryColor2
        )
        var rating by remember { mutableStateOf(3.7f) }
        RatingBar(
            rating = rating,
            space = 2.dp,
            imageVectorEmpty = ImageVector.vectorResource(R.drawable.staricon),
            imageVectorFFilled = ImageVector.vectorResource(R.drawable.starfill),
            tintEmpty = UnselectedColor,
            tintFilled = YellowColor,
            animationEnabled = true,
            gestureEnabled = true,
            itemSize = 25.dp
        ) {
            rating = it
        }
    }
}

@Composable
private fun SupervisorRating() {
    Row(
        modifier = Modifier
            .paddingTop(37.3)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "تعامل المشرف",
            style = text14,
            color = SecondaryColor2
        )
        var rating by remember { mutableStateOf(3.7f) }
        RatingBar(
            rating = rating,
            space = 2.dp,
            imageVectorEmpty = ImageVector.vectorResource(R.drawable.staricon),
            imageVectorFFilled = ImageVector.vectorResource(R.drawable.starfill),
            tintEmpty = UnselectedColor,
            tintFilled = YellowColor,
            animationEnabled = true,
            gestureEnabled = true,
            itemSize = 25.dp
        ) {
            rating = it
        }
    }
}