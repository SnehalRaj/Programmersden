package utils

import com.typesafe.config.ConfigFactory



trait Config {
  private val config = ConfigFactory.load()
  private val databaseConfig = config.getConfig("database")
  val url = databaseConfig.getString("url")
}
