package com.selsela.takeefapp.ui.intro.componenets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.intro.doneSelection
import com.selsela.takeefapp.utils.Extensions.Companion.log


@Composable
fun Indicators(selectedIndex: Int) {
    Row(
        Modifier
            .wrapContentSize(Alignment.BottomStart)
            .animateContentSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 3) {
            Image(
                painter =
                if (i == selectedIndex) {
                    doneSelection.add(i)
                    doneSelection.log("doneSelection")
                    painterResource(id = R.drawable.nowselected)
                } else {
                    if (doneSelection.contains(i).not())
                        painterResource(id = R.drawable.unselected)
                    else painterResource(id = R.drawable.selected)
                },
                contentDescription = "indicator",
                modifier = if (i == 1)
                    Modifier.padding(horizontal = 7.dp)
                else Modifier.padding(horizontal = 0.dp)
            )
        }
    }
}
