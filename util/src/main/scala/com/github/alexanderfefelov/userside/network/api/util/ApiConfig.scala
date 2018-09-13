package com.github.alexanderfefelov.userside.network.api.util

import com.typesafe.config.{Config, ConfigFactory}

trait ApiConfig {

  val CONFIG_PREFIX_API: String = "userside-network-api"

  val config: Config = ConfigFactory.load()

}
