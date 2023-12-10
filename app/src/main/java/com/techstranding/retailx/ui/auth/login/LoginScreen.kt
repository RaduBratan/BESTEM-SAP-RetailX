package com.techstranding.retailx.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techstranding.retailx.R
import com.techstranding.retailx.ui.theme.noFontPadding
import com.techstranding.retailx.ui.theme.plusJakartaSans
import com.techstranding.retailx.ui.utils.isValidEmail
import com.techstranding.retailx.ui.utils.isValidPassword
import com.techstranding.retailx.ui.views.LoadingAnimation
import com.techstranding.retailx.ui.views.OutlinedButton
import com.techstranding.retailx.ui.views.Popup
import com.techstranding.retailx.ui.views.TransparentIconButton

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onFinishClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val sentLoginDataState = viewModel.sentLoginDataState.collectAsState()

    val typedEmailState = remember { mutableStateOf(TextFieldValue()) }
    var typedEmailError by remember { mutableStateOf(false) }
    val isTypedEmailValid = typedEmailState.value.isValidEmail()

    val typedPasswordState = remember { mutableStateOf(TextFieldValue()) }
    var typedPasswordError by remember { mutableStateOf(false) }
    val isTypedPasswordValid = typedPasswordState.value.isValidPassword()

    var isPopupVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (sentLoginDataState.value) {
            SentLoginDataState.Idle -> {
                Unit
            }

            SentLoginDataState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.TopCenter)
                )
            }

            is SentLoginDataState.Success -> {
                onFinishClick()
                viewModel.resetState()
            }

            SentLoginDataState.EmailBad -> {
                typedEmailError = true
                viewModel.resetState()
            }

            SentLoginDataState.PasswordBad -> {
                typedPasswordError = true
                viewModel.resetState()
            }

            SentLoginDataState.Error -> {
                isPopupVisible = true
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 52.dp,
                    horizontal = 36.dp
                ),
        ) {
            TransparentIconButton(
                imageVector = Icons.Filled.ArrowBack,
                onClick = onBackClick
            )
            
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(id = R.string.login_prompt_message),
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.15.sp,
                ),
            )
            
            Column {
                AuthTextField(
                    modifier = Modifier.padding(top = 24.dp),
                    hint = stringResource(id = R.string.login_email_hint),
                    sentAuthData = typedEmailState,
                    support = if (typedEmailError) stringResource(id = R.string.login_email_error) else stringResource(
                        id = R.string.login_email_support
                    ),
                    keyboardType = KeyboardType.Email,
                    isPassword = false,
                    isErrorVisible = typedEmailError,
                )

                AuthTextField(
                    modifier = Modifier.padding(top = 24.dp),
                    sentAuthData = typedPasswordState,
                    hint = stringResource(id = R.string.login_password_hint),
                    support = if (typedPasswordError) stringResource(id = R.string.login_password_error) else stringResource(
                        id = R.string.login_password_support
                    ),
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    isErrorVisible = typedPasswordError,
                )

                OutlinedButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    label = stringResource(id = R.string.login_button_title),
                    onClick = {
                        typedEmailError = false
                        typedPasswordError = false
                        viewModel.enterAccount(
                            email = typedEmailState.value.text,
                            password = typedPasswordState.value.text,
                        )
                    },
                    isButtonEnabled = isTypedEmailValid && isTypedPasswordValid,
                )
            }
        }

        Popup(
            title = stringResource(id = R.string.error_title),
            message = stringResource(id = R.string.login_send_data_error_message),
            hasNegativeAction = false,
            isPopupVisible = isPopupVisible,
            onConfirm = {
                isPopupVisible = false
                viewModel.resetState()
            },
            onDismiss = {
                isPopupVisible = false
                viewModel.resetState()
            }
        )
    }
}

@Composable
internal fun AuthTextField(
    modifier: Modifier = Modifier,
    sentAuthData: MutableState<TextFieldValue>,
    hint: String,
    support: String,
    keyboardType: KeyboardType,
    isPassword: Boolean,
    isErrorVisible: Boolean = false,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    val imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility

    val visualTransformation =
        if (isPassword) {
            if (isPasswordVisible)
                VisualTransformation.None else PasswordVisualTransformation()
        } else VisualTransformation.None

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        isError = isErrorVisible,
        label = {
            Text(
                text = hint,
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                style = TextStyle(platformStyle = noFontPadding),
                maxLines = 1,
            )
        },
        supportingText = {
            Text(
                text = support,
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                style = TextStyle(platformStyle = noFontPadding),
                maxLines = 1,
            )
        },
        trailingIcon = {
            if (isPassword) {
                TransparentIconButton(
                    imageVector = imageVector,
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        value = sentAuthData.value,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = { newValue ->
            sentAuthData.value = newValue
        }
    )
}