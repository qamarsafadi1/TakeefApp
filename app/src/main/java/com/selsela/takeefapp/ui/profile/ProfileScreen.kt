package com.selsela.takeefapp.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.common.AsyncImage
import com.selsela.takeefapp.ui.common.EditText
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.InputEditText
import com.selsela.takeefapp.ui.profile.delete.DeleteAccountSheet
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Bold
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text14White
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.mStartActivityForResult
import com.selsela.takeefapp.utils.Extensions.Companion.showSuccess
import com.selsela.takeefapp.utils.Extensions.Companion.uploadImages
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import de.palm.composestateevents.EventEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    goToLogin: () -> Unit,
    onBack: () -> Unit
) {
    Color.Transparent.ChangeStatusBarOnlyColor(true)
    val coroutineScope = rememberCoroutineScope()
    val paySheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val viewState: AuthUiState by viewModel.uiState.collectAsStateLifecycleAware(AuthUiState())
    val context = LocalContext.current
    var user by remember {
        viewModel.user
    }
    ProfileContent(
        viewModel = viewModel,
        uiState = viewState,
        onSave = viewModel::updateProfile,
        onDelete = viewModel::deleteAccount,
        onBack = onBack,
        coroutineScope,
        paySheetState
    )
    /**
     * Handle Ui state from flow
     */

    EventEffect(
        event = viewState.onSuccess,
        onConsumed = viewModel::onSuccess
    ) { message ->
        context.showSuccess(message)
    }
    EventEffect(
        event = viewState.onDeleteAccount,
        onConsumed = viewModel::onDeleteAccount
    ) { message ->
        LocalData.clearData()
        user = null
        viewModel.userLoggedIn.value = false
        goToLogin()
    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = viewModel::onFailure
    ) { error ->
        if (error.status == 403) {
            LocalData.clearData()
            goToLogin()
        }

            Common.handleErrors(
                error.responseMessage,
                error.errors,
                context
            )
    }

}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ProfileContent(
    viewModel: AuthViewModel,
    uiState: AuthUiState,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit,
    coroutineScope: CoroutineScope,
    paySheetState: ModalBottomSheetState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(bottom = 40.dp)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .requiredHeight(88.dp)
                    .background(Color.White)
                    .padding(top = 30.dp)
                    .padding(horizontal = 6.dp),
            ) {

                IconButton(
                    onClick = { onBack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.backbutton),
                        contentDescription = ""
                    )
                }
                Text(
                    text = stringResource(id = R.string.profile),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style = text14Meduim
                )
                ElasticButton(
                    onClick = { onSave() },
                    title = stringResource(id = R.string.save_lbl),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 14.dp)
                        .width(107.dp)
                        .height(44.dp),
                    isLoading = uiState.isLoading
                )

            }
            ImageChooser(viewModel = viewModel)
            Column(
                modifier = Modifier
                    .padding(top = 47.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .background(TextColor, RoundedCornerShape(33.dp))
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProfileForm(viewModel)

            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                Modifier
                    .padding(horizontal = 22.dp, vertical = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.delete_account),
                        style = text14Bold,
                        color = TextColor
                    )
                    Text(
                        text = stringResource(R.string.delete_account_lbl_1),
                        style = text12,
                        color = SecondaryColor,
                        modifier = Modifier.paddingTop(12)
                    )
                }
                IconButton(onClick = {
                    coroutineScope.launch {
                        if (paySheetState.isVisible)
                            paySheetState.hide()
                        else paySheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }

                }) {
                    Image(
                        painter = painterResource(id = R.drawable.deletetrash),
                        contentDescription = ""
                    )
                }
            }
        }
        DeleteAccountSheet(sheetState = paySheetState,uiState) {
            onDelete()
        }
    }
}

@Composable
private fun ImageChooser(viewModel: AuthViewModel) {
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<String>(LocalData.user?.avatar ?: "")
    }
    var isImageCaptured by remember {
        mutableStateOf<Boolean>(false)
    }
    val imagePicker = mStartActivityForResult(
        context = context,
    ) { file, bitmap ->
        if (file != null) {
            isImageCaptured = true
            imageUri = file.absolutePath
            viewModel.avatar = file
        }
    }
    Box(modifier = Modifier.defaultMinSize(minHeight = 115.dp, minWidth = 115.dp)) {
        imageUri.let {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        clip = false
                    )
            ) {
                AsyncImage(
                    it,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .size(98.dp)
                        .shadow(
                            elevation = 25.dp,
                            shape = CircleShape,
                            clip = false
                        )
                )
            }


        }
        IconButton(
            onClick = {
                isImageCaptured = false
                uploadImages(context, imagePicker, false)
            },
            modifier = Modifier
                .padding(top = 25.dp, end = 10.dp)
                .align(Alignment.BottomStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.changeimage),
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun ProfileForm(viewModel: AuthViewModel) {
    Column(
        Modifier
            .padding(bottom = 24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.full_name),
            style = text11,
            modifier = Modifier.padding(top = 61.dp)
        )
        InputEditText(
            onValueChange = {
                viewModel.name.value = it
                if (!viewModel.isNameValid.value)
                    viewModel.isNameValid.value = true

            },
            text = viewModel.name.value,
            isValid = viewModel.isNameValid.value,
            validationMessage = viewModel.errorMessageName.value,
            hint = stringResource(R.string.name),
            inputType = KeyboardType.Text,
            borderColor = viewModel.validateNameBorderColor(),
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(R.string.email),
            style = text11,
            modifier = Modifier.padding(top = 9.dp)
        )
        InputEditText(
            onValueChange = {
                viewModel.email.value = it
            },
            text = viewModel.email.value,
            hint = stringResource(R.string.email),
            inputType = KeyboardType.Email,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.mobile),
            style = text11,
            modifier = Modifier.padding(top = 6.8.dp)
        )
        EditTextView(viewModel)
    }
}

@Composable
private fun EditTextView(viewModel: AuthViewModel) {
    EditText(
        onValueChange = {
            viewModel.mobile.value = it
            if (!viewModel.isValid.value)
                viewModel.isValid.value = true

        },
        text = viewModel.mobile.value,
        textStyle = text14White,
        hint = "59XXXXXXX",
        inputType = KeyboardType.Phone,
        borderColor = viewModel.validateBorderColor(),
        trailing = {
            Text(
                text = "966", style = text14,
                color = SecondaryColor.copy(0.67f)
            )
        },
        modifier = Modifier.padding(top = 16.dp)
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
