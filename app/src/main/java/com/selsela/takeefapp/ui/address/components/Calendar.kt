package com.selsela.takeefapp.ui.address.components

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import com.selsela.takeefapp.R
import java.text.SimpleDateFormat
import java.util.Locale


@Preview
@Composable
fun Calendar(
    onDateSelection: (String) -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides
                LayoutDirection.Ltr
    ) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val cal = java.util.Calendar.getInstance()
        val date = remember { mutableStateOf(dateFormat.format(cal.time)) }
        onDateSelection(date.value)
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                { CalendarView(ContextThemeWrapper(it, R.style.CalenderViewCustom)) },
                modifier = Modifier.fillMaxWidth(),
                update = { views ->
                    views.firstDayOfWeek = java.util.Calendar.SATURDAY
                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        cal.set(year, month, dayOfMonth)
                        date.value = dateFormat.format(cal.time).toString()
                        onDateSelection(date.value)
                    }
                })
        }
    }

}



