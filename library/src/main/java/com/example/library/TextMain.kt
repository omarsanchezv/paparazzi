package com.example.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TextMain() {
    Text(
        text = "Im the text",
        fontSize = 20.sp,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Box(modifier = Modifier.background(Color.White)) {
        TextMain()
    }

}