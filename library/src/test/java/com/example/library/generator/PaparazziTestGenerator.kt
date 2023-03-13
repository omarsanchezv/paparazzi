package com.example.library.generator

import com.android.utils.usLocaleDecapitalize
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import okio.Path
import okio.Path.Companion.toPath
import org.junit.Test
import java.io.File

/**
 * Finds all files in the components module which have Compose previews
 * and generates Paparazzi screenshot tests for them.
 *
 * The generated tests can then be used to record screenshots with
 * ./gradlew components:recordPaparazziInternalDebug
 *
 * To verify that the current implementation matches the recorded screenshots
 * ./gradlew components:verifyPaparazziInternalDebug
 */
fun main() {
    val path = System.getProperty("user.dir") ?: error("Can't get user dir")

    File(path).walk()
        .filter { (it.path.contains("/library/src/main/java")) && it.extension == "kt" }
        .forEach {
            if (it.readText().contains("@Snapshot")) {
                processFileWithPreviews(it)
            }
        }
}

/**
 * Reads the given file, finds the names of all the functions annotated with @Preview
 * and uses them to generate a Paparazzi test file with one test for each preview.
 */
private fun processFileWithPreviews(file: File) {
    val lines = file.readLines()
    val previewNames = mutableListOf<String>()
    var saveNextFunctionName = false
    var packageName = ""
    lines.forEachIndexed { i, line ->
        if (i == 0) {
            packageName = line.split(" ").last()
        }
        if (line.contains("@Snapshot")) {
            saveNextFunctionName = true
        }
        if (saveNextFunctionName && line.startsWith("fun ")) {
            previewNames += line.split(" ")[1].removeSuffix("()")
            saveNextFunctionName = false
        }
    }
    val pathString = file.path.replace("src/main", "src/test").split("java").first() + "java"
    val testFilePath = pathString.toPath()
    generatePaparazziTest(
        packageName,
        file.nameWithoutExtension + "PaparazziTest",
        testFilePath,
        previewNames
    )
}

fun generatePaparazziTest(
    packageName: String,
    fileName: String,
    path: Path,
    previewNames: List<String>
) {
    val classBuilder = TypeSpec.classBuilder(fileName)
        .superclass(PaparazziTest::class)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                // KotlinPoet does not let us remove redundant public modifiers or Unit return types for the functions,
                // but we don't mind for generated code as long as the tests work
                .addMember("\"RedundantVisibilityModifier\", \"RedundantUnitReturnType\"")
                .build()
        )
    previewNames.forEach {
        classBuilder.addFunction(
            FunSpec.builder(it.removeSuffix("Preview").usLocaleDecapitalize())
                .addStatement("paparazziRule.snapshot { $it() }")
                .addAnnotation(Test::class)
                .build()
        )
    }
    val testFile = FileSpec.builder(packageName, fileName)
        .addType(classBuilder.build())
        .addFileComment("AUTO-GENERATED FILE by PaparazziTestGenerator\nDO NOT MODIFY")
        .build()
    val nioPath = path.toNioPath()
    testFile.writeTo(nioPath)
}