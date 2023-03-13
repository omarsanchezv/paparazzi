package com.example.library.generator

import java.io.File

fun main() {
    val path = System.getProperty("user.dir") ?: error("Can't get user dir")

    File(path).walk().filter {
        (it.path.contains("/library/src/test/java")) && it.extension == "kt" && !it.name.equals("PaparazziTestCleaner.kt")
    }
        .forEach {
            if (it.readText().contains("// AUTO-GENERATED FILE by PaparazziTestGenerator")) {
                it.delete()
            }
        }
}