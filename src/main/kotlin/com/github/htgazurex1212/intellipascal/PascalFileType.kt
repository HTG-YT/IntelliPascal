/*
 * IntelliPascal - Free Pascal for IntelliJ Platform
 * Copyright (C) 2022 HTGAzureX1212.
 *
 * IntelliPascal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * IntelliPascal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with IntelliPascal. If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.htgazurex1212.intellipascal

import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.Icon

open class PascalFileType protected constructor() : LanguageFileType(PascalLanguage.INSTANCE) {
  companion object {
    val INSTANCE = PascalFileType()

    val UNIT_EXTENSIONS = listOf("pas", "pp")
  }

  @NotNull
  override fun getDescription(): String = "Pascal file"

  @NotNull
  override fun getDefaultExtension(): String = "pas"

  @Nullable
  override fun getIcon(): Icon? = null

  @NotNull
  override fun getName(): String = "Pascal"
}
