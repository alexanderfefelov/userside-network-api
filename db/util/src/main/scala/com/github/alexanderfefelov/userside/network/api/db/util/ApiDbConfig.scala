package com.github.alexanderfefelov.userside.network.api.db.util

import com.github.alexanderfefelov.userside.network.api.util._

trait ApiDbConfig extends ApiConfig {

  val CONFIG_PREFIX_API_DB: String = s"$CONFIG_PREFIX_API-db"

  val dbDriver: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.driver")
  val dbUrl: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.url")
  val dbUsername: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.username")
  val dbPassword: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.password")

}
