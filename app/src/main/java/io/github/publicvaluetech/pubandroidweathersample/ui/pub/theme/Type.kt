package io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

object Type {
    val mediumText = TextStyle(
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
    ).withDefaultFontFamily()
    val boldText = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 12.sp,
    ).withDefaultFontFamily()
    val normalMultiline = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ).withDefaultFontFamily()

    val bold600 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 20.sp,
    )

    val weatherLocationInputText = TextStyle(
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
        fontSize = 20.sp,
        background = Color.Transparent,
        textDecoration = TextDecoration.None,
    )
    val weatherLocationListItem = TextStyle(
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        textDecoration = TextDecoration.None,
    )

    // NORMAL
    val normal250 = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 13.sp,
    )
    val normal300 = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 14.sp,
    )
    val normal400 = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
    )
    val normal500 = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp,
    )
    val normal600 = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 20.sp,
    )
    val normal800 = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp,
    )
    val normal1600 = TextStyle(
        fontStyle = FontStyle.Normal,
        fontSize = 48.sp,
    )
    val medium600 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
        fontSize = 20.sp,
    )
}

private fun TextStyle.withDefaultFontFamily(): TextStyle {
    val default = FontFamily.SansSerif
    return if (fontFamily != null) this else copy(fontFamily = default)
}
