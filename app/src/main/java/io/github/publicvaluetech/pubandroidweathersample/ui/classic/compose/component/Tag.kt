package io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.component

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.ClassicTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.Theme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Tag(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colors.chipBackground,
    contentColor: Color = Theme.colors.onBackground,
    @DrawableRes iconId: Int,
    text: String,
    onClick: () -> Unit
) {
    Chip(
        modifier = modifier,
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Icon(
            modifier = modifier
                .padding(end = Theme.dimensions.space.space300)
                .size(Theme.dimensions.size.smallIcon),
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Theme.colors.onSurface,
        )
        Text(text = text)
    }
}

//region preview
@Preview
@Preview(name = "TagDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BR24TagPreview() {
    ClassicTheme {
        Tag(
            iconId = R.drawable.ic_heart,
            text = "Risiko: 13.4 %",
            onClick = {}
        )
    }
}
//endregion preview
