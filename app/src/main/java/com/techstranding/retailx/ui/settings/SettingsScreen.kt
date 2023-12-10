package com.techstranding.retailx.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techstranding.retailx.R
import com.techstranding.retailx.ui.theme.noFontPadding
import com.techstranding.retailx.ui.theme.plusJakartaSans
import com.techstranding.retailx.ui.views.LoadingAnimation
import com.techstranding.retailx.ui.views.Popup
import com.techstranding.retailx.ui.views.TransparentIconButton

@Composable
internal fun SettingsScreen(
    // modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val sentLogOutDataState = viewModel.sentLogOutDataState.collectAsState()

    var isLogOutErrorPopupVisible by remember { mutableStateOf(false) }
    var isLogOutPopupVisible by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        when (sentLogOutDataState.value) {
            SentLogOutDataState.Idle -> {
                Unit
            }

            SentLogOutDataState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.TopCenter)
                )
            }

            is SentLogOutDataState.Success -> {
                viewModel.resetState()
            }

            SentLogOutDataState.Error -> {
                isLogOutPopupVisible = false
                isLogOutErrorPopupVisible = true
            }
        }

        Popup(
            title = stringResource(id = R.string.error_title),
            message = stringResource(
                id = R.string.settings_log_out_error_message
            ),
            isPopupVisible = isLogOutErrorPopupVisible,
            hasNegativeAction = false,
            onConfirm = {
                isLogOutErrorPopupVisible = false
                viewModel.resetState()
            },
            onDismiss = {
                isLogOutErrorPopupVisible = false
                viewModel.resetState()
            },
        )

        SettingsView(
            onLogOutClick = { viewModel.exitAccount() },
        )
    }
}

@Composable
private fun SettingsView(
    onLogOutClick: () -> Unit,
) {
    var isLogOutPopupVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.settings_title),
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.15.sp,
                ),
            )

            Text(
                modifier = Modifier
                    .padding(top = 24.dp),
                text = stringResource(id = R.string.settings_category_account_title),
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.15.sp,
                ),
            )

            SettingCard(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 2.dp,
                    )
                    .clip(RoundedCornerShape(16.dp)),
                icon = Icons.Filled.Logout,
                text = stringResource(id = R.string.settings_log_out_text),
                subtext = stringResource(id = R.string.settings_log_out_subtext),
                shape = RoundedCornerShape(16.dp),
                onSettingClick = { isLogOutPopupVisible = true },
            )


            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.settings_category_info_title),
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.15.sp,
                ),
            )

            SettingCard(
                modifier = Modifier
                    .padding(
                        top = 2.dp,
                        bottom = 8.dp,
                    )
                    .clip(RoundedCornerShape(16.dp)),
                icon = Icons.Filled.Code,
                text = stringResource(
                    id = R.string.settings_app_version_text,
                    LocalContext.current.packageManager.getPackageInfo(
                        LocalContext.current.packageName,
                        0
                    ).versionCode
                ),
                shape = RoundedCornerShape(16.dp),
                onSettingClick = {},
            )
        }
    }

    Popup(
        title = stringResource(
            id = R.string.question_title
        ),
        message = stringResource(
            id = R.string.settings_log_out_prompt_message
        ),
        isPopupVisible = isLogOutPopupVisible,
        onConfirm = {
            if (isLogOutPopupVisible) {
                onLogOutClick()
                isLogOutPopupVisible = false
            }
        },
        onDismiss = {
            isLogOutPopupVisible = false
        },
    )
}

@Composable
private fun SettingCard(
    modifier: Modifier,
    icon: ImageVector,
    text: String,
    subtext: String? = null,
    shape: RoundedCornerShape,
    onSettingClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onSettingClick),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .padding(vertical = 8.dp)
                    .size(24.dp),
                imageVector = icon,
                contentDescription = null,
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = text,
                    fontFamily = plusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        platformStyle = noFontPadding,
                        letterSpacing = 0.15.sp,
                    ),
                )

                if (subtext != null) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = subtext,
                        fontFamily = plusJakartaSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = TextStyle(
                            platformStyle = noFontPadding,
                            letterSpacing = 0.15.sp,
                        ),
                    )
                }
            }
        }
    }
}