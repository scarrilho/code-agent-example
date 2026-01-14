package com.sergiocarrilho

import ai.koog.agents.core.tools.annotations.LLMDescription
import ai.koog.agents.core.tools.annotations.Tool
import ai.koog.agents.core.tools.reflect.ToolSet
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import java.io.File

class Tools : ToolSet {
    private val fs = FileSystem.SYSTEM
    private fun String.toAbsolutePath(): Path = File(this).absolutePath.toPath()

    @Tool
    @LLMDescription("Gets the full content of a file provided by the user.")
    fun readFile(@LLMDescription("The name of the file to read") filename: String): String =
        filename.toAbsolutePath().also { println("tool: read_file($it)") }.let { path ->
            if (fs.exists(path)) fs.read(path) { readUtf8() } else "Error: File not found: $path"
        }

    @Tool
    @LLMDescription("Lists the files in a directory provided by the user.")
    fun listFiles(@LLMDescription("The path to a directory to list files from") path: String): String =
        path.toAbsolutePath().also { println("tool: list_files($it)") }.let { resolved ->
            runCatching {
                fs.takeIf { it.exists(resolved) }?.list(resolved)?.joinToString("\n") {
                    "${it.name} (${if (fs.metadata(it).isDirectory) "dir" else "file"})"
                } ?: "Error: Path not found"
            }.getOrElse { "Error listing files: ${it.message}" }
        }

    @Tool
    @LLMDescription("Replaces first occurrence of old_str with new_str in file. If old_str is empty, appends or creates file.")
    fun editFile(
        @LLMDescription("The path to the file to edit") path: String,
        @LLMDescription("The string to replace (empty to append)") oldStr: String,
        @LLMDescription("The replacement or appended content") newStr: String
    ): String = path.toAbsolutePath().also { println("tool: edit_file($it)") }.let { p ->
        val exists = fs.exists(p)
        val content = if (exists) fs.read(p) { readUtf8() } else ""
        when {
            oldStr.isEmpty() -> { fs.write(p) { writeUtf8(content + newStr) }; if (exists) "Appended to $p" else "Created $p" }
            !exists -> "Error: File not found"
            !content.contains(oldStr) -> "Error: old_str not found"
            else -> { fs.write(p) { writeUtf8(content.replaceFirst(oldStr, newStr)) }; "File edited" }
        }
    }
}