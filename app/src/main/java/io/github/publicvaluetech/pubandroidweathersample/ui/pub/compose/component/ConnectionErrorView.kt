package io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.PubTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.Theme

@Composable
fun ConnectionErrorView(
    refresh: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(Theme.dimensions.space.space500),
        backgroundColor = Theme.colors.background,
        shape = RoundedCornerShape(Theme.dimensions.size.cardCornerRadius),
        elevation = Theme.dimensions.space.space0
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_cloud_off_black_24dp),
                contentDescription = null,
                tint = Theme.colors.onBackground,
                modifier = Modifier
                    .padding(Theme.dimensions.space.space500)
                    .size(Theme.dimensions.size.bigIcon)
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = Theme.dimensions.space.space500,
                        end = Theme.dimensions.space.space500
                    )
            ) {
                Text(
                    style = Theme.typography.mediumText,
                    text = stringResource(id = R.string.state_error_offline),
                    color = Theme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(Theme.dimensions.space.space100))
                Text(
                    style = Theme.typography.normalMultiline,
                    text = stringResource(id = R.string.state_error_offline_desc),
                    color = Theme.colors.onBackground,
                    modifier = Modifier.alpha(0.5F)
                )
                TextButton(
                    contentPadding = PaddingValues(
                        top = Theme.dimensions.space.space300,
                        bottom = Theme.dimensions.space.space300,
                        end = Theme.dimensions.space.space300
                    ),
                    onClick = refresh,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error_retry_24dp),
                        contentDescription = null,
                        tint = Theme.colors.onBackground,
                        modifier = Modifier
                            .size(Theme.dimensions.size.smallIcon)
                            .padding(Theme.dimensions.space.space100)
                    )
                    Text(
                        style = Theme.typography.boldText,
                        text = stringResource(id = R.string.reload_caption),
                        color = Theme.colors.onBackground,
                        modifier = Modifier
                            .padding(Theme.dimensions.space.space100)
                    )
                }
            }
        }
    }
}

@Preview
@Preview(name = "ErrorViewDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ErrorViewPreview() {
    PubTheme {
        ConnectionErrorView(refresh = {})
    }
}

fun errorView(error: Throwable?, onErrorRetry: () -> Unit): (@Composable () -> Unit)? = error?.let {
    @Composable { ConnectionErrorView(onErrorRetry) }
}
