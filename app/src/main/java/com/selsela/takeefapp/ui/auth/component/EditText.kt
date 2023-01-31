package com.selsela.takeefapp.ui.auth.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.common.EditText
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun EditTextView(viewModel: AuthViewModel) {
    EditText(
        onValueChange = {
            viewModel.mobile.value = it
            if (it.isEmpty() || viewModel.isValid.value.not()) {
                viewModel.isValid.value = true
                viewModel.errorMessage.value = ""
            }
        },
        text = viewModel.mobile.value,
        hint = "59XXXXXXX",
        inputType = KeyboardType.Phone,
        trailing = {
            Text(
                text = "966", style = text14,
                color = Color.White
            )
        },
        modifier = Modifier.padding(top = 16.dp),
        borderColor = viewModel.validateBorderColor()
    )

    AnimatedVisibility(visible = viewModel.isValid.value.not()) {
        Text(
            text = viewModel.errorMessage.value,
            style = text12,
            color = Red,
            modifier = Modifier.paddingTop(12)
        )
    }
}