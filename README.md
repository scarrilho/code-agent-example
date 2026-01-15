# Coding Agent

## About

This is a simple coding agent built with [Koog](https://github.com/JetBrains/koog), a Kotlin framework for building AI agents by JetBrains.

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

```
--- Sample Coding Agent ---

You: Please create a Kotlin script file HelloWorld.kts that prints "Hello, World!" to the console
Assistant: Done — I created HelloWorld.kts containing a Kotlin script that prints "Hello, World!".

--- Ready for next input (type 'exit' to quit) ---

You: Edit HelloWorld.kts and add a comment at the top: "This is my first program"
Assistant: I've added the comment to the top of HelloWorld.kts.

--- Ready for next input (type 'exit' to quit) ---

You: Read the contents of HelloWorld.kts
Assistant: Here's the content of HelloWorld.kts:

// This is my first program
println("Hello, World!")

--- Ready for next input (type 'exit' to quit) ---

You: List the files in the current directory
Assistant: Here are the entries in the current directory:

- .env (file)
- .env.example (file)
- .git (dir)
- .gitignore (file)
- .gradle (dir)
- .idea (dir)
- .kotlin (dir)
- HelloWorld.kts (file)
- README.md (file)
- build (dir)
- build.gradle.kts (file)
- gradle (dir)
- gradle.properties (file)
- gradlew (file)
- gradlew.bat (file)
- settings.gradle.kts (file)
- src (dir)

--- Ready for next input (type 'exit' to quit) ---

You: exit
Goodbye!
```

