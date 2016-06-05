package com.yadu

/**
  * Created by yadu on 5/6/16.
  */

import com.yadu.codegen.SampleData
import slick.driver.PostgresDriver.api._
import com.yadu.core.DriverHelper._
import com.yadu.repository.Tables
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, Matchers, WordSpec}

import scala.concurrent.Await

class SampleQueries extends WordSpec with Matchers with BeforeAndAfter with ScalaFutures{

  before {
    val init = db.run(DBIO.seq(
      Tables.schema.drop,
      Tables.schema.create,
      Tables.employeeTable ++= SampleData.employeesSampleData
    )
    )
    import scala.concurrent.duration._
    Await.result(init, 5.seconds)
  }

  after {
    println("testing completed")
  }

  "get employees" should {
    "return all the employees in the database" in {
      val res = getEmployees
      assert(res.futureValue.size === 5)
    }
  }


  def getEmployees = {
    val res = db.run(Tables.employeeTable.result)
    res
  }


}
