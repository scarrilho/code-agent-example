# Coding Agent

## About

This is a simple coding agent built with **Koog**.

It is inspired by the article [How to Build an Agent](https://ampcode.com/how-to-build-an-agent).

The Koog setup is based on the examples from [koog-tutorials](https://github.com/svtk/koog-tutorials).

## Features

The agent uses the following tools to interact with code files:

- **ReadFile** - Reads the content of a specified file
- **CreateFile** - Creates a new file with the given content
- **ModifyFile** - Modifies an existing file using search and replace

## Environment Setup

Copy the example file and fill in your own values:

```bash
cp .env.example .env
```

# Running the Agent
There are two ways to run the agent:
1. Via the IntelliL IDEA
2. Via command line: `./gradlew run --console=plain -q`

The agent will start an interactive session where you can give it coding tasks. It will use its tools to read, create, or modify files based on your instructions.

Don't forget to put your API key in .env file (for example: `OPENAI_API_KEY=your-api-key-here`)
