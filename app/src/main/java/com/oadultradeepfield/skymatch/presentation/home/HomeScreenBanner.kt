package com.oadultradeepfield.skymatch.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oadultradeepfield.skymatch.R
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme

@Composable
fun HomeScreenBanner(modifier: Modifier = Modifier) {
  Box(modifier = modifier.background(Color.Black)) {
    // Background image with gradient overlay
    Image(
        painter = painterResource(id = R.drawable.banner_bg),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier =
            Modifier.fillMaxSize().drawWithContent {
              drawContent()
              drawRect(
                  brush =
                      Brush.verticalGradient(
                          colors = listOf(Color.Transparent, Color.Black),
                          startY = size.height * 0.7f,
                          endY = size.height,
                      )
              )
            },
        alpha = 0.9f,
        colorFilter =
            ColorFilter.tint(
                color = MaterialTheme.colorScheme.primary,
                blendMode = BlendMode.Multiply,
            ),
    )

    // Content layer (above the gradient)
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.BlurOn,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(48.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "SkyMatch",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontSize = 40.sp,
        )
      }

      Spacer(modifier = Modifier.size(28.dp))

      // TODO: Navigate to search screen
      ConstellationSearchBar(onSearchClick = {}, modifier = Modifier.fillMaxWidth())
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenBanner() {
  AppTheme(dynamicColor = false) { HomeScreenBanner() }
}
