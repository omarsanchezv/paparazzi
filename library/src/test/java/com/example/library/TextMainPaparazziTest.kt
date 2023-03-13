// AUTO-GENERATED FILE by PaparazziTestGenerator
// DO NOT MODIFY
package com.example.library

import com.example.library.generator.PaparazziTest
import kotlin.Suppress
import kotlin.Unit
import org.junit.Test

@Suppress("RedundantVisibilityModifier", "RedundantUnitReturnType")
public class TextMainPaparazziTest : PaparazziTest() {
  @Test
  public fun text(): Unit {
    paparazziRule.snapshot { TextPreview() }
  }
}
