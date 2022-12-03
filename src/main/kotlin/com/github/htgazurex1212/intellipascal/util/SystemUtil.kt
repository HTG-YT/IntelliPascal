package com.github.htgazurex1212.intellipascal.util

import com.github.htgazurex1212.intellipascal.bundle.GeneralBundle
import com.github.htgazurex1212.intellipascal.exception.IntelliPascalException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.io.File
import java.util.concurrent.ExecutionException

object SystemUtil {
  private val LOGGER = Logger.getInstance(SystemUtil::class.java.name)

  const val SHORT_TIMEOUT = 5 * 1000

  @Nullable
  fun executeAndGetStdout(workingDirectory: String, executablePath: String, timeoutNanoseconds: Int, vararg parameters: String): String? {
    lateinit var processOutput: ProcessOutput
    try {
      processOutput = getProcessOutput(timeoutNanoseconds, workingDirectory, executablePath, *parameters)
    } catch (exception: ExecutionException) {
      return null
    }

    val exitCode = processOutput.exitCode
    val stdout = processOutput.stdout.trim()
    val stderr = processOutput.stderr.trim()

    if (exitCode != 0 && stdout.isEmpty()) {
      LOGGER.warn("command $executablePath errored with exit code $exitCode")
      LOGGER.warn("stderr: $stderr")

      throw IntelliPascalException(GeneralBundle.message("error.exit.code", executablePath, exitCode, stderr))
    }

    if (stdout.isEmpty()) {
      return null
    }

    return stdout
  }

  @NotNull
  private fun executeGeneralCommandLine(generalCommandLine: GeneralCommandLine, timeout: Int): ProcessOutput {
    val processHandler = CapturingProcessHandler(generalCommandLine)
    return if (timeout < 0) {
      processHandler.runProcess()
    } else {
      processHandler.runProcess(timeout)
    }
  }

  @NotNull
  private fun getProcessOutput(timeout: Int, workingDirectory: String, executablePath: String, vararg arguments: String): ProcessOutput {
    if (!File(workingDirectory).isFile || !File(executablePath).canExecute())
      return ProcessOutput()

    val generalCommandLine = GeneralCommandLine()
        .withWorkDirectory(workingDirectory)
        .withExePath(executablePath)
        .withParameters(*arguments)

    return executeGeneralCommandLine(generalCommandLine, timeout)
  }
}
