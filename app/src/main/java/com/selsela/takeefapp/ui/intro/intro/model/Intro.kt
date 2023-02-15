package com.selsela.takeefapp.ui.intro.intro.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.selsela.takeefapp.R

@Keep
data class Intro(
    val title: String = "",
    val text: String = "",
    @DrawableRes val Image: Int = 0
) {


    fun listOfIntro(context: Context): List<Intro> {
        return listOf(
            Intro(
                context.getString(R.string.intro_title_1),
                context.getString(R.string.intro_description_1),
                R.drawable.intro1
            ),
            Intro(
                context.getString(R.string.intro_title_2),
                context.getString(R.string.intro_description_2),
                R.drawable.intro2
            ),
            Intro(
                context.getString(R.string.intro_title_3),
                context.getString(R.string.intro_description_3),
                R.drawable.intro3
            ),
        )
    }

}
