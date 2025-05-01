package com.example.employeeservice.repository

import com.example.employeeservice.model.Employee
import com.typesafe.scalalogging.LazyLogging
import scala.collection.concurrent.TrieMap
import scala.concurrent.{ExecutionContext, Future}

class EmployeeRepository(implicit ec: ExecutionContext) extends LazyLogging {
  // In-memory storage using TrieMap for thread safety
  private val employees = new TrieMap[String, Employee]()

  def findAll(): Future[Seq[Employee]] = {
    logger.debug(s"Finding all employees, total count: ${employees.size}")
    Future.successful(employees.values.toSeq)
  }

  def findById(id: String): Future[Option[Employee]] = {
    logger.debug(s"Finding employee by ID: $id")
    Future.successful(employees.get(id))
  }

  def save(employee: Employee): Future[Employee] = {
    logger.debug(s"Saving employee: $employee")
    employees.put(employee.id, employee)
    Future.successful(employee)
  }

  def update(id: String, employee: Employee): Future[Option[Employee]] = {
    logger.debug(s"Updating employee with ID: $id")
    if (employees.contains(id)) {
      val updatedEmployee = employee.copy(id = id)
      employees.put(id, updatedEmployee)
      Future.successful(Some(updatedEmployee))
    } else {
      logger.warn(s"Attempted to update non-existent employee with ID: $id")
      Future.successful(None)
    }
  }

  def delete(id: String): Future[Boolean] = {
    logger.debug(s"Deleting employee with ID: $id")
    Future.successful(employees.remove(id).isDefined)
  }
}
