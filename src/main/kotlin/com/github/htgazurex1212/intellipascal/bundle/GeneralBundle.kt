package com.github.htgazurex1212.intellipascal.bundle

import com.intellij.AbstractBundle
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.jetbrains.annotations.PropertyKey
import java.lang.ref.Reference
import java.lang.ref.SoftReference
import java.util.ResourceBundle

object GeneralBundle {
  private var bundleReference: Reference<ResourceBundle>? = null

  @Nullable
  private fun getBundleInstance(): ResourceBundle? {
    val bundle: ResourceBundle?

    if (bundleReference != null) {
      bundle = bundleReference!!.get()
    } else {
      bundle = ResourceBundle.getBundle("GeneralBundle")
      bundleReference = SoftReference(bundle)
    }

    return bundle
  }

  @NotNull
  fun message(key: @PropertyKey(resourceBundle = "GeneralBundle") String, vararg params: Any): String =
      AbstractBundle.message(getBundleInstance()!!, key, params)
}
