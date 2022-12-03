package com.github.htgazurex1212.intellipascal.pascalSdk

import com.github.htgazurex1212.intellipascal.pascalSdk.util.FreePascalSdkUtil
import com.github.htgazurex1212.intellipascal.util.SystemUtil
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.projectRoots.*
import com.intellij.openapi.util.SystemInfo
import org.jdom.Element
import org.jetbrains.annotations.NotNull
import java.io.File
import org.jetbrains.annotations.Nullable

class FreePascalSdkType : SdkType("FreePascal SDK") {
  companion object {
    private val LOGGER = Logger.getInstance(FreePascalSdkType::class.java.name)

    fun getInstance(): FreePascalSdkType = findInstance(FreePascalSdkType::class.java)
  }

  @Nullable
  override fun createAdditionalDataConfigurable(sdkModel: SdkModel, sdkModificator: SdkModificator): AdditionalDataConfigurable? {
    TODO("Not yet implemented")
  }

  @NotNull
  override fun getPresentableName(): String = "Free Pascal SDK"

  @NotNull
  override fun isValidSdkHome(path: String): Boolean {
    LOGGER.info("checking Free Pascal SDK path: $path")

    val fpcBinary = FreePascalSdkUtil.getFreePascalFpcBinary(path)
    return fpcBinary.isFile && fpcBinary.canExecute()
  }

  override fun saveAdditionalData(additionalData: SdkAdditionalData, additional: Element) {
    return
  }

  @Nullable
  override fun suggestHomePath(): String? {
    val paths = if (SystemInfo.isWindows) {
      listOf("""C:\fpc""")
    } else {
      listOf("/usr/lib/fpc", "/usr/share/fpc", "/usr/local/lib/fpc")
    }

    return paths.firstOrNull { File(it).isDirectory }
  }


  @NotNull
  override fun suggestSdkName(currentSdkName: String?, sdkHome: String): String {
    val version = getSdkVersion(sdkHome) ?: return "Free Pascal v?? at $sdkHome"

    return "Free Pascal v$version ${getSdkTarget(sdkHome)}"
  }

  @Nullable
  private fun getSdkTarget(sdkHome: String): String? {
    LOGGER.info("getting version for SDK: $sdkHome")

    try {
      return SystemUtil.executeAndGetStdout(sdkHome, FreePascalSdkUtil.getFreePascalFpcBinary(sdkHome).absolutePath, SystemUtil.SHORT_TIMEOUT, FreePascalSdkUtil.FREE_PASCAL_PARAMS_TARGET)
    } catch (exception: Exception) {
      LOGGER.error("error: ${exception.message}", exception)
    }

    return null
  }

  @Nullable
  private fun getSdkVersion(sdkHome: String): String? {
    LOGGER.info("getting version for SDK: $sdkHome")

    try {
      return SystemUtil.executeAndGetStdout(sdkHome, FreePascalSdkUtil.getFreePascalFpcBinary(sdkHome).absolutePath, SystemUtil.SHORT_TIMEOUT, FreePascalSdkUtil.FREE_PASCAL_PARAMS_VERSION)
    } catch (exception: Exception) {
      LOGGER.error("error: ${exception.message}", exception)
    }

    return null
  }
}
