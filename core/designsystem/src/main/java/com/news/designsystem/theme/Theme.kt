package com.news.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = Color.Blue,
    onPrimary = Color.White,
    primaryContainer = Color.LightGray,
    onPrimaryContainer = Color.DarkGray,
    secondary = Color.Green,
    onSecondary = Color.White,
    secondaryContainer = Color.LightGray,
    onSecondaryContainer = Color.DarkGray,
    tertiary = Color.Yellow,
    onTertiary = Color.Black,
    tertiaryContainer = Color.LightGray,
    onTertiaryContainer = Color.DarkGray,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color.LightGray,
    onErrorContainer = Color.DarkGray,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color.LightGray,
    onSurfaceVariant = Color.Black,
    inverseSurface = Color.White,
    inverseOnSurface = Color.Black,
    outline = Color.Gray,
)

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
val DarkDefaultColorScheme = darkColorScheme(
    primary = Color.Blue,
    onPrimary = Color.White,
    primaryContainer = Color.DarkGray,
    onPrimaryContainer = Color.LightGray,
    secondary = Color.Green,
    onSecondary = Color.White,
    secondaryContainer = Color.DarkGray,
    onSecondaryContainer = Color.LightGray,
    tertiary = Color.Yellow,
    onTertiary = Color.Black,
    tertiaryContainer = Color.DarkGray,
    onTertiaryContainer = Color.LightGray,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color.DarkGray,
    onErrorContainer = Color.LightGray,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceVariant = Color.DarkGray,
    onSurfaceVariant = Color.White,
    inverseSurface = Color.Black,
    inverseOnSurface = Color.White,
    outline = Color.Gray,
)

/**
 * Light Android theme color scheme
 */
@VisibleForTesting
val LightAndroidColorScheme = lightColorScheme(
    primary = Color.Blue,
    onPrimary = Color.White,
    primaryContainer = Color.LightGray,
    onPrimaryContainer = Color.DarkGray,
    secondary = Color.Green,
    onSecondary = Color.White,
    secondaryContainer = Color.LightGray,
    onSecondaryContainer = Color.DarkGray,
    tertiary = Color.Yellow,
    onTertiary = Color.Black,
    tertiaryContainer = Color.LightGray,
    onTertiaryContainer = Color.DarkGray,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color.LightGray,
    onErrorContainer = Color.DarkGray,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color.LightGray,
    onSurfaceVariant = Color.Black,
    inverseSurface = Color.White,
    inverseOnSurface = Color.Black,
    outline = Color.Gray,
)

/**
 * Dark Android theme color scheme
 */
@VisibleForTesting
val DarkAndroidColorScheme = darkColorScheme(
    primary = Color.Blue,
    onPrimary = Color.White,
    primaryContainer = Color.DarkGray,
    onPrimaryContainer = Color.LightGray,
    secondary = Color.Green,
    onSecondary = Color.White,
    secondaryContainer = Color.DarkGray,
    onSecondaryContainer = Color.LightGray,
    tertiary = Color.Yellow,
    onTertiary = Color.Black,
    tertiaryContainer = Color.DarkGray,
    onTertiaryContainer = Color.LightGray,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color.DarkGray,
    onErrorContainer = Color.LightGray,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceVariant = Color.DarkGray,
    onSurfaceVariant = Color.White,
    inverseSurface = Color.Black,
    inverseOnSurface = Color.White,
    outline = Color.Gray,
)
/**
 * Light Android gradient colors
 */
val LightAndroidGradientColors = GradientColors(container = DSGray70)

/**
 * Dark Android gradient colors
 */
val DarkAndroidGradientColors = GradientColors(container = Color.Black)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = DSGray70)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)

/**
 * Now in Android theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param androidTheme Whether the theme should use the Android theme color scheme instead of the
 *        default theme.
 * @param disableDynamicTheming If `true`, disables the use of dynamic theming, even when it is
 *        supported. This parameter has no effect if [androidTheme] is `true`.
 */
@Composable
fun NewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = when {
        androidTheme -> if (darkTheme) DarkAndroidColorScheme else LightAndroidColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }
    // Gradient colors
    val emptyGradientColors = GradientColors(container = colorScheme.surfaceColorAtElevation(2.dp))
    val defaultGradientColors = GradientColors(
        top = colorScheme.inverseOnSurface,
        bottom = colorScheme.primaryContainer,
        container = colorScheme.surface,
    )
    val gradientColors = when {
        androidTheme -> if (darkTheme) DarkAndroidGradientColors else LightAndroidGradientColors
        !disableDynamicTheming && supportsDynamicTheming() -> emptyGradientColors
        else -> defaultGradientColors
    }
    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )
    val backgroundTheme = when {
        androidTheme -> if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
        else -> defaultBackgroundTheme
    }
    val tintTheme = when {
        androidTheme -> TintTheme()
        !disableDynamicTheming && supportsDynamicTheming() -> TintTheme(colorScheme.primary)
        else -> TintTheme()
    }
    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = NewsTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
