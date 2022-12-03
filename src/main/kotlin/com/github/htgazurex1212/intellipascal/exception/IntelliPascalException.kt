package com.github.htgazurex1212.intellipascal.exception

class IntelliPascalException : Exception {
  constructor()

  constructor(message: String) : super(message)

  constructor(message: String, cause: Throwable): super(message, cause)
}
