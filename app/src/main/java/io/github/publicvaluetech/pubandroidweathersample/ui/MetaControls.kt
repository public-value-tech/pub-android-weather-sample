package io.github.publicvaluetech.pubandroidweathersample.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.publicvaluetech.pubandroidweathersample.Design
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.ClassicTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.Theme
import java.util.Locale

@Composable
fun MetaControls(
    modifier: Modifier = Modifier,
    currentLocale: Locale,
    currentDesign: Design,
    switchLanguage: (Locale) -> Unit,
    switchDesign: (Design) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
            onClick = {
                switchDesign(Design.CLASSIC)
            },
        ) {
            Box(
                modifier = Modifier
                    .then(
                        if (currentDesign == Design.CLASSIC) Modifier.border(
                            width = Theme.dimensions.space.space100,
                            color = Color.White,
                            shape = CircleShape
                        ) else Modifier
                    )
                    .padding(Theme.dimensions.space.space200)
                    .size(Theme.dimensions.size.iconButton)
                    .clip(CircleShape)
                    .background(Theme.colors.designBlue),
            )
        }
        IconButton(
            onClick = {
                switchDesign(Design.PUB)
            },
        ) {
            Box(
                modifier = Modifier
                    .then(
                        if (currentDesign == Design.PUB) Modifier.border(
                            width = Theme.dimensions.space.space100,
                            color = Color.White,
                            shape = CircleShape
                        ) else Modifier
                    )
                    .padding(Theme.dimensions.space.space200)
                    .size(Theme.dimensions.size.iconButton)
                    .clip(CircleShape)
                    .background(Theme.colors.designPurple),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                switchLanguage(Locale.ENGLISH)
            },
        ) {
            Icon(
                modifier = Modifier
                    .then(
                        if (currentLocale.language == Locale.ENGLISH.language) Modifier.border(
                            width = Theme.dimensions.space.space100,
                            color = Color.White,
                            shape = CircleShape
                        ) else Modifier
                    )
                    .padding(Theme.dimensions.space.space200)
                    .size(Theme.dimensions.size.iconButton)
                    .clip(CircleShape),
                tint = Color.Unspecified,
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_en_flag),
            )
        }
        IconButton(
            onClick = {
                switchLanguage(Locale.GERMAN)
            },
        ) {
            Icon(
                modifier = Modifier
                    .then(
                        if (currentLocale.language == Locale.GERMAN.language) Modifier.border(
                            width = Theme.dimensions.space.space100,
                            color = Color.White,
                            shape = CircleShape
                        ) else Modifier
                    )
                    .padding(Theme.dimensions.space.space200)
                    .size(Theme.dimensions.size.iconButton)
                    .clip(CircleShape),
                tint = Color.Unspecified,
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_de_flag),
            )
        }
    }
}

@Preview
@Preview(name = "ErrorViewDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MetaControlsPreview() {
    ClassicTheme {
        MetaControls(
            currentLocale = Locale.ENGLISH,
            currentDesign = Design.CLASSIC,
            switchLanguage = {},
            switchDesign = {}
        )
    }
}

fun metaControlsView(
    modifier: Modifier,
    currentLocale: Locale,
    currentDesign: Design,
    switchLanguage: (Locale) -> Unit,
    switchDesign: (Design) -> Unit
): (@Composable () -> Unit) =
    @Composable { MetaControls(modifier, currentLocale, currentDesign, switchLanguage, switchDesign) }
