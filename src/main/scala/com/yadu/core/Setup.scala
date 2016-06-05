package com.yadu.core

import com.yadu.repository.Tables
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await

/**
  * Created by yadu on 5/6/16.
  */
object Setup extends App {

  val setUpQueries = DBIO.seq(
    Tables.schema.drop,
    Tables.schema.create
  )
  val res = DriverHelper.db.run(setUpQueries)
  import scala.concurrent.duration._
  Await.result(res, 10.seconds)
}
