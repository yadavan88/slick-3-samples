package com.reactore
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = employeeTable.schema ++ userTable.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  case class Employee(id: Long, firstName: String, lastName: String, dOB: java.sql.Date, isDeleted: Boolean = false, gender: String)
  implicit def GetResultEmployee(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Date], e3: GR[Boolean]): GR[Employee] = GR{
    prs => import prs._
    Employee.tupled((<<[Long], <<[String], <<[String], <<[java.sql.Date], <<[Boolean], <<[String]))
  }
  /** Table description of table Employee. Objects of this class serve as prototypes for rows in queries. */
  class EmployeeTable(_tableTag: Tag) extends Table[Employee](_tableTag, Some("employee"), "Employee") {
    def * = (id, firstName, lastName, dOB, isDeleted, gender) <> (Employee.tupled, Employee.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(dOB), Rep.Some(isDeleted), Rep.Some(gender)).shaped.<>({r=>import r._; _1.map(_=> Employee.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    val id: Rep[Long] = column[Long]("EmployeeId", O.AutoInc, O.PrimaryKey)
    val firstName: Rep[String] = column[String]("FirstName", O.Length(150,varying=true))
    val lastName: Rep[String] = column[String]("LastName", O.Length(150,varying=true))
    val dOB: Rep[java.sql.Date] = column[java.sql.Date]("DOB")
    val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
    val gender: Rep[String] = column[String]("Gender")
  }
  lazy val employeeTable = new TableQuery(tag => new EmployeeTable(tag))

  case class User(id: Long, userName: String, email: String, password: String, isDeleted: Boolean = false, employeeId: Long)
  implicit def GetResultUser(implicit e0: GR[Long], e1: GR[String], e2: GR[Boolean]): GR[User] = GR{
    prs => import prs._
    User.tupled((<<[Long], <<[String], <<[String], <<[String], <<[Boolean], <<[Long]))
  }
  /** Table description of table User. Objects of this class serve as prototypes for rows in queries. */
  class UserTable(_tableTag: Tag) extends Table[User](_tableTag, Some("employee"), "User") {
    def * = (id, userName, email, password, isDeleted, employeeId) <> (User.tupled, User.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userName), Rep.Some(email), Rep.Some(password), Rep.Some(isDeleted), Rep.Some(employeeId)).shaped.<>({r=>import r._; _1.map(_=> User.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    val id: Rep[Long] = column[Long]("UserId", O.AutoInc, O.PrimaryKey)
    val userName: Rep[String] = column[String]("UserName", O.Length(150,varying=true))
    val email: Rep[String] = column[String]("Email", O.Length(150,varying=true))
    val password: Rep[String] = column[String]("Password", O.Length(150,varying=true))
    val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
    val employeeId: Rep[Long] = column[Long]("EmployeeId")
  }
  lazy val userTable = new TableQuery(tag => new UserTable(tag))
}
