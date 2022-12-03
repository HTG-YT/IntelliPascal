package com.github.htgazurex1212.intellipascal.util

import org.jetbrains.annotations.NotNull
import java.io.File

object FileUtil {
  @NotNull
  fun listDirectories(baseDirectory: File): Iterable<File> =
      baseDirectory.listFiles { file: File -> file.isDirectory }!!.asIterable()
}
