package com.selsela.takeefapp.ui.model.intro

import androidx.annotation.DrawableRes
import com.selsela.takeefapp.R

data class Intro(
    val title: String = "",
    val text: String = "",
    @DrawableRes val Image: Int = 0
) {


    fun listOfIntro(): List<Intro> {
        return listOf(
            Intro(
                "تنظيف , صيانة , تركيب ",
                "هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد هذا النص من مولد النص العربى، حيث يمكنك أن تولد مثل هذا النص أو العديد من النصوص الأخرى إضافة.",
                R.drawable.intro1
            ),
            Intro(
                "نصلك حيثما كنت",
                "هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد هذا النص من مولد النص العربى، حيث يمكنك أن تولد مثل هذا النص أو العديد من النصوص الأخرى إضافة.",
                R.drawable.intro2
            ),
            Intro(
                "وسائل دفع متنوعة",
                "هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد هذا النص من مولد النص العربى، حيث يمكنك أن تولد مثل هذا النص أو العديد من النصوص الأخرى إضافة.",
                R.drawable.intro3
            ),
        )
    }

}
