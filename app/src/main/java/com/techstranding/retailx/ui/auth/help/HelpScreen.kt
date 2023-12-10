package com.techstranding.retailx.ui.auth.help

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techstranding.retailx.R
import com.techstranding.retailx.ui.theme.noFontPadding
import com.techstranding.retailx.ui.theme.plusJakartaSans
import com.techstranding.retailx.ui.views.TransparentIconButton

@Composable
fun HelpScreen(
    onBackClick: () -> Unit
) {
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

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(id = R.string.help_message1),
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.15.sp,
                ),
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.help_message2),
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.15.sp,
                ),
            )

            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)
                    .size(64.dp),
                imageVector = Icons.Filled.Info,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                contentDescription = null,
            )
        }
    }
}