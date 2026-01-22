# Coding Agent

## About

This is a simple coding agent built with [Koog](https://github.com/JetBrains/koog), supporting the article in Medium [How an AI Coding Agent Works (in ~100 lines of Kotlin)](https://medium.com/@sergiocarrilho/building-an-ai-coding-agent-in-100-lines-of-kotlin-f9af28e6aeb0)

It is inspired by the article [How to Build an Agent](https://ampcode.com/how-to-build-an-agent).

The Koog setup is based on the examples from [koog-tutorials](https://github.com/svtk/koog-tutorials).

## Features

The agent uses OpenAI's GPT-5 Mini model and provides three file system tools:

| Tool | Description |
|------|-------------|
| **readFile** | Reads the full content of a specified file |
| **listFiles** | Lists files and directories in a given path |
| **editFile** | Replaces text in a file, or appends/creates if the search string is empty |

## Architecture

- **Agent.kt** - Main entry point with interactive REPL loop and agent configuration
- **Tools.kt** - File system tools exposed to the LLM via Koog's annotation-based tool definition
- **Config.kt** - Environment configuration for API keys

## Technology Stack

- **Language**: Kotlin 2.2.21
- **Framework**: Koog 0.6.0
- **LLM**: OpenAI GPT-5 Mini
- **File I/O**: Okio 3.6.0
- **Build**: Gradle with Kotlin DSL

## Environment Setup

Copy the example file and fill in your own values:

```bash
cp .env.example .env
```

Add your OpenAI API key to the `.env` file:

```
OPENAI_API_KEY=your-api-key-here
```

Note: I know, keeping credentials in a file is unsafe. This .env file is used in this project for the sake of simplicity. 

## Running the Agent

There are two ways to run the agent:

1. **Via IntelliJ IDEA** - Run the `main` function in `Agent.kt`
2. **Via command line**:
   ```bash
   ./gradlew run --console=plain -q
   ```

The agent will start an interactive session where you can give it coding tasks. It will use its tools to read, list, and modify files based on your instructions.

Type `exit` or `quit` to end the session.

## Example Interaction

The first prompt uses the **editFile** tool to create and write contents to a file
```
--- Sample Coding Agent ---
You: Make me a new file in kotlin called HelloWorld.kts file and implement hello world in it
LLM response: [Call(id=call_mpE6zrRSWsCfs9kbmBlV..., tool=editFile, parts=[Text(text={"path":"HelloWorld.kts","oldStr":"","newStr":"// HelloWorld.kts - Kotlin script\n// Run with: kotlinc -script HelloWorld.kts or kotlin HelloWorld.kts\n\nprintln(\"Hello, World!\")\n"})], metaInfo=ResponseMetaInfo(timestamp=..., totalTokensCount=459, inputTokensCount=323, outputTokensCount=136, additionalInfo={}, metadata=null))]
tool: edit_file(~/Developer/code-agent-example/HelloWorld.kts)
LLM response: [Assistant(parts=[Text(text=I created HelloWorld.kts with a simple Kotlin script that prints "Hello, World!"...

Assistant: I created HelloWorld.kts with a simple Kotlin script that prints "Hello, World!".

// HelloWorld.kts - Kotlin script
// Run with: kotlinc -script HelloWorld.kts or kotlin HelloWorld.kts

println("Hello, World!")

--- Ready for next input (type 'exit' to quit) ---
```

The next prompt uses the **readFile** to check for comments, and **editFile** to remove them
```
You: Remove all comments from the file HelloWorld.kts

LLM response: [Call(id=call_1dD9vhPwkRyzFYblrUMU...., tool=readFile, ...)
tool: read_file(~/Developer/code-agent-example/HelloWorld.kts)

LLM response: [Call(id=call_CHViKIW4NYqNIyUPRoRZ...., tool=editFile, ...]
tool: edit_file(~/Developer/code-agent-example/HelloWorld.kts)

LLM response: [Assistant(parts=[Text(text=I removed the comments and updated HelloWorld.kts. The file now contains:

println("Hello, World!"))], metaInfo=ResponseMetaInfo(timestamp=...)]
Assistant: I removed the comments and updated HelloWorld.kts. The file now contains:

println("Hello, World!")
--- Ready for next input (type 'exit' to quit) ---

You: exit
Goodbye!
```

