package com.github.htgazurex1212.intellipascal.pascalSdk

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.projectRoots.SdkType

class FreePascalSdkType : SdkType("FreePascal SDK") {
  companion object {
    private val LOGGER = Logger.getInstance(FreePascalSdkType::class.java.name)

    fun getInstance(): FreePascalSdkType = findInstance(FreePascalSdkType::class.java)
  }
}
