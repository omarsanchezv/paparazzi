package com.example.library


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonMain() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(50.dp)
    ) {
        Icon(Icons.Rounded.Add, contentDescription = "", tint = Color.White)
        Text(text = "Im a Button")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ButtonMain()
}