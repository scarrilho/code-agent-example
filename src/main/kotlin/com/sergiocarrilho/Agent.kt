package com.sergiocarrilho

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.core.tools.reflect.asTools
import ai.koog.agents.features.eventHandler.feature.handleEvents
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.all.simpleOpenAIExecutor
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val executor = simpleOpenAIExecutor(Config.get("OPENAI_API_KEY"))
    val model = OpenAIModels.Chat.GPT5Mini
    val systemPrompt = """
        You are a coding assistant whose goal is to help us solve coding tasks.
        You have access to file system tools. Use them to read, list, and edit files.
        If you need to use a tool, Koog handles the format, just call the function.
    """.trimIndent()

    fun createAgent() = AIAgent(
        promptExecutor = executor,
        llmModel = model,
        systemPrompt = systemPrompt,
        toolRegistry = ToolRegistry { tools(Tools().asTools()) }
    ) {
        handleEvents {
            onLLMCallStarting { ctx ->
                ctx.tools.forEach { tool -> println("Tool: ${tool.name}") }
            }
            onLLMCallCompleted { ctx -> println("LLM response: ${ctx.responses}") }
        }
    }

    println("--- Sample Coding Agent ---")

    generateSequence { print("\n\u001b[92mYou:\u001b[0m ").run { readlnOrNull()?.trim() } }
        .takeWhile { it !in listOf("exit", "quit") }
        .filter { it.isNotEmpty() }
        .forEach { input ->
            runCatching { createAgent().run(input) }
                .onSuccess { println("\u001b[93mAssistant:\u001b[0m $it") }
                .onFailure { println("error: ${it.message}") }
            println("\n--- Ready for next input (type 'exit' to quit) ---")
        }

    println("Goodbye!")
}

