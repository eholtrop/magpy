package com.dpal.persistance.sqldelight

import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory {
  actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(Database.Schema, "test.db")
  }
}