package com.yadu.usage

/**
  * Created by yadu on 5/6/16.
  */

import java.sql.Date

import com.yadu.repository.{Employee, Tables, User}
import slick.driver.PostgresDriver.api._
import Tables._
import com.yadu.core.{CustomImplicits, DriverHelper}
import CustomImplicits._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object SimpleQueries {

  val db = DriverHelper.db

  def getEmployees: Unit = {
    val query = for {
      employees <- employeeTable
    } yield employees

    val future = db.run(query.result)
    val res = Await.result(future, 5.seconds)
    println("employees : " + res)
  }

  def simpleQueries = {
    val res = for {
      filter <- db.run(employeeTable.filter(_.firstName like "S%").result)
      _ = println("Employee names starting with 'S' = " + filter)
      first <- db.run(employeeTable.sortBy(_.firstName.asc).take(1).result)
      _ = println("First employee in alphabetic order is : " + first)
      count <- db.run(employeeTable.size.result)
      _ = println("No of employees = " + count)
      groups <- db.run(employeeTable.groupBy(_.gender).map({
        case (k, v) => (k, v.size)
      }).result)
      _ = println("Groups => " + groups)
    } yield groups

    res.onFailure {
      case ex => ex.printStackTrace()
    }

    Thread.sleep(5000)
  }

  def joinQueriesInFor = {
    val joinQuery = for {
      e <- employeeTable
      u <- userTable if e.id === u.employeeId
    } yield (e.firstName, u.userName)

    db.run(joinQuery.result).map {
      x => println("Joined Res => " + x)
    }.recoverFn
    Thread.sleep(5000)
  }

  def joinQueries = {
    db.run(employeeTable.join(userTable).on(_.id === _.employeeId)
      .map(x => (x._1.firstName, x._2.userName)).result).map { x =>
      println("Join Res == " + x)
    }.recoverFn
    Thread.sleep(5000)
  }

  def combineActions = {

    val action1 = employeeTable.filter(_.id === 101L).result
    val action2 = userTable.filter(_.employeeId === 101L).result

    val actions = DBIO.sequence(Seq(action1, action2))


    val res = db.run(actions)
    res.map {
      x => println("seq actions = " + x.flatten)
    }.recoverFn

    val andTenActions = action1.andThen(action2)
    db.run(andTenActions).map {
      x => println("and then 2nd action result = " + x)
    }.recoverFn

    val zipActions = action1 zip action2
    db.run(zipActions).map {
      x => println("zipped res = " + x)
    }.recoverFn

    Thread.sleep(5000)
  }


  def transactionSuccessQueries = {

    val e = Employee(0, "Mahendra", "Dhoni", new Date(new java.util.Date().getTime), false, "Male")

    val u = User(0, "mahi", "dhoni@gmail.com", "india", false, 105)

    val insertAction = employeeTable returning employeeTable.map(x => x) += e
    val fullAction = insertAction.flatMap { e =>
      val newUser = u.copy(id = e.id)
      userTable returning userTable += newUser
    }

    db.run(fullAction).map {
      x => println("new user is " + x)
    }.recoverFn

    Thread.sleep(5000)
  }

  def transactionFailedQueries = {

    val e = Employee(0, "Mahendra", "Dhoni", new Date(new java.util.Date().getTime), false, "Male")

    val u = User(101, "mahi", "dhoni@gmail.com", "india", false, 105)

    val insertAction = employeeTable returning employeeTable.map(x => x) += e
    val fullAction = insertAction.flatMap { e =>
      val newUser = u.copy(id = e.id)
      //should fail with duplicate primary key 101, so it should rollback
      userTable returning userTable forceInsert newUser
    }

    db.run(fullAction.transactionally).map {
      x => println("new user is " + x)
    }.recoverFn

    Thread.sleep(5000)
  }

  def plainQueries = {
    val q = sql"""select * from employee."Employee" """.as[Employee]
    db.run(q).map {
      x => println("plain query res = " + x)
    }.recoverFn

    Thread.sleep(5000)
  }

}


object TestApp extends App {
  //  SimpleQueries.getEmployees
  //  SimpleQueries.simpleQueries
  //  SimpleQueries.joinQueriesInFor
  //  SimpleQueries.joinQueries
  //  SimpleQueries.combineActions
  //  SimpleQueries.transactionSuccessQueries
//  SimpleQueries.transactionFailedQueries
  SimpleQueries.plainQueries
}