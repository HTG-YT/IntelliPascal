package com.github.htgazurex1212.intellipascal.pascalSdk.util

import com.github.htgazurex1212.intellipascal.util.FileUtil
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.SystemInfo
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.io.File
import java.util.regex.Pattern

object FreePascalSdkUtil {
  private val LOGGER = Logger.getInstance(FreePascalSdkUtil::class.java.name)

  const val FREE_PASCAL_PARAMS_VERSION = "-iV"
  const val FREE_PASCAL_PARAMS_TARGET = "-iTPTO"
  private val FREE_PASCAL_VERSION_REGEX = Pattern.compile("\\d+\\.\\d+\\.\\d+")
  private val DEFAULT_UNIX_BINARY_DIRECTORIES = listOf("/usr/bin", "/usr/local/bin")

  @NotNull
  fun getFreePascalFpcBinary(sdkHome: String): File {
    LOGGER.info("getting FPC executable from SDK: $sdkHome")
    return getFreePascalSdkBinary(sdkHome, "bin", "fpc")
  }

  @NotNull
  private fun getFreePascalSdkBinary(sdkHome: String, directory: String, executable: String): File {
    LOGGER.info("getting Free Pascal SDK binary: $sdkHome, directory: $directory, executable: $executable")

    val sdkHomeDirectory = File(sdkHome)
    var fullDirectory = File(sdkHome, directory)

    if (!fullDirectory.exists() && sdkHomeDirectory.isDirectory) {
      LOGGER.warn("${fullDirectory.absolutePath} not found; trying \$SdkHome/\$Version/bin")
      val currentVersion = getFreePascalVersionDirectory(sdkHomeDirectory)

      if (currentVersion != null) {
        fullDirectory = File(File(sdkHome, currentVersion), directory)

        if (!fullDirectory.exists()) {
          LOGGER.warn("${fullDirectory.absolutePath} not found; trying without \$SdkHome/\$Version")
          fullDirectory = File(sdkHome, currentVersion)
        }
      } else {
        fullDirectory = sdkHomeDirectory
      }
    }

    if (fullDirectory.exists()) {
      LOGGER.info("binary directory is found at ${fullDirectory.absolutePath}")

      for (targetDirectory in FileUtil.listDirectories(fullDirectory)) {
        val executablePath = getExecutable(targetDirectory.absolutePath, executable)
        if (executablePath.canExecute()) {
          LOGGER.info("executable found: ${executablePath.absolutePath}")
          return executablePath
        }
      }

      val executablePath = File(fullDirectory, executable)
      if (executablePath.exists() && executablePath.canExecute())
        return executablePath
    }

    if (SystemInfo.isUnix) {
      LOGGER.warn("${fullDirectory.absolutePath} not found; trying default unix executable path")

      for (defaultDirectory in DEFAULT_UNIX_BINARY_DIRECTORIES) {
        val executablePath = File(defaultDirectory, executable)
        if (executablePath.exists() && executablePath.canExecute())
          return executablePath
      }
    }

    LOGGER.error("binary directory not found")
    throw RuntimeException("Free Pascal SDK not found")
  }

  @Nullable
  fun getFreePascalVersionDirectory(sdkHomeDirectory: File): String? {
    var currentVersion: String? = null
    for (versionDirectory in FileUtil.listDirectories(sdkHomeDirectory))
      if (isFreePascalVersion(versionDirectory.name)
          && (currentVersion == null || isVersionLessThanOrEqual(currentVersion, versionDirectory.name)))
        currentVersion = versionDirectory.name

    return currentVersion
  }

  @NotNull
  private fun getExecutable(path: String, executable: String): File =
      File(path, executable + if (SystemInfo.isWindows) { ".exe" } else { "" })

  @NotNull
  private fun isFreePascalVersion(string: String): Boolean = FREE_PASCAL_VERSION_REGEX.matcher(string).matches()

  @NotNull
  private fun isVersionLessThanOrEqual(lhs: String, rhs: String): Boolean = lhs <= rhs
}
