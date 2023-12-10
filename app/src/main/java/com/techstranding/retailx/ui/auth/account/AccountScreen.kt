package com.techstranding.retailx.ui.auth.account

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techstranding.retailx.R
import com.techstranding.retailx.ui.theme.md_theme_light_secondary
import com.techstranding.retailx.ui.theme.noFontPadding
import com.techstranding.retailx.ui.theme.plusJakartaSans
import com.techstranding.retailx.ui.views.OutlinedButton
import com.techstranding.retailx.ui.views.TransparentButton

@Composable
fun AccountScreen(
    onLoginClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .size(600.dp)
                .offset(x = -(200).dp, y = -(300).dp),
        ) {
            drawCircle(
                color = md_theme_light_secondary.copy(alpha = 0.6f),
                radius = size.minDimension / 2,
                style = Stroke(width = 100f)
            )
        }

        Canvas(
            modifier = Modifier
                .size(800.dp)
                .offset(x = 200.dp, y = 300.dp),
        ) {
            drawCircle(
                color = md_theme_light_secondary.copy(alpha = 0.6f),
                radius = size.minDimension / 2,
                style = Stroke(width = 150f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 52.dp,
                    horizontal = 36.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LocalConfiguration.current.screenHeightDp.dp / 4),
                text = stringResource(id = R.string.app_name),
                fontFamily = plusJakartaSans,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center,
                ),
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    label = stringResource(id = R.string.account_button_login_title),
                    onClick = { onLoginClick() }
                )

                TransparentButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    label = stringResource(id = R.string.account_button_help_title),
                    onClick = { onHelpClick() }
                )
            }
        }
    }
}