// AUTO-GENERATED FILE by PaparazziTestGenerator
// DO NOT MODIFY
package com.example.library

import com.example.library.generator.PaparazziTest
import kotlin.Suppress
import kotlin.Unit
import org.junit.Test

@Suppress("RedundantVisibilityModifier", "RedundantUnitReturnType")
public class TestScreenPaparazziTest : PaparazziTest() {
  @Test
  public fun screen(): Unit {
    paparazziRule.snapshot { ScreenPreview() }
  }
}
