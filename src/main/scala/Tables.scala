import slick.driver.H2Driver.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

// A Suppliers table with 6 columns: id, name, street, city, state, zip
class Suppliers(tag: Tag)
  extends Table[Supplier](tag, "SUPPLIERS") {

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[Supplier] =
    (id, name, street, city, state, zip) <> (Supplier.tupled, Supplier.unapply)

  // This is the primary key column:
  def id: Rep[Int] = column[Int]("SUP_ID", O.PrimaryKey)

  def name: Rep[String] = column[String]("SUP_NAME")

  def street: Rep[String] = column[String]("STREET")

  def city: Rep[String] = column[String]("CITY")

  def state: Rep[String] = column[String]("STATE")

  def zip: Rep[String] = column[String]("ZIP")
}

case class Supplier(
                     id: Int,
                     name: String,
                     street: String,
                     city: String,
                     state: String,
                     zip: String
                   )

// A Coffees table with 5 columns: name, supplier id, price, sales, total
class Coffees(tag: Tag)
  extends Table[Coffee](tag, "COFFEES") {

  def * : ProvenShape[Coffee] =
    (name, supID, price, sales, total, id) <> (Coffee.tupled, Coffee.unapply)

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("COF_NAME")

  def price: Rep[Double] = column[Double]("PRICE")

  def sales: Rep[Int] = column[Int]("SALES")

  def total: Rep[Int] = column[Int]("TOTAL")

  // A reified foreign key relation that can be navigated to create a join
  def supplier: ForeignKeyQuery[Suppliers, Supplier] =
    foreignKey("SUP_FK", supID, TableQuery[Suppliers])(_.id)

  def supID: Rep[Int] = column[Int]("SUP_ID")
}

case class Coffee(
                   name: String,
                   supID: Int,
                   price: Double,
                   sales: Int,
                   total: Int,
                   id: Int = 0
                 )