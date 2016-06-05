package com.yadu.core

/**
  * Created by yadu on 5/6/16.
  */

import slick.driver.PostgresDriver.api._

object DriverHelper {
  val user = "postgres"
  val url = "jdbc:postgresql://localhost:5432/Demo"
  val password = "admin"
  val jdbcDriver = "org.postgresql.Driver"
  val db = Database.forURL(url, user, password, driver = jdbcDriver)
}