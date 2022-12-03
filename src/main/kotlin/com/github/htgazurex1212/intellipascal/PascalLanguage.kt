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

import com.intellij.lang.Language
import org.jetbrains.annotations.NotNull

open class PascalLanguage protected constructor() : Language("Pascal") {
  companion object {
    val INSTANCE = PascalLanguage()
  }

  @NotNull
  override fun isCaseSensitive(): Boolean = false

  @NotNull
  override fun getDisplayName(): String = "Pascal File"
}
