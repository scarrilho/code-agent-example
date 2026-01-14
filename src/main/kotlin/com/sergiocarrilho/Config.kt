package com.sergiocarrilho

import java.io.File

object Config {
    private val env = File(".env").takeIf { it.exists() }?.readLines()
        ?.filter { it.contains("=") && !it.startsWith("#") }
        ?.associate { it.substringBefore("=") to it.substringAfter("=").trim() }
        ?: emptyMap()

    fun get(key: String): String = System.getenv(key) ?: env[key]
        ?: error("$key not set. Set env variable or add to .env file")
}

