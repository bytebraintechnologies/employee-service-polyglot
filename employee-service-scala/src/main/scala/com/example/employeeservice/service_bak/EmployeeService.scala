package com.example.employeeservice.service

import com.example.employeeservice.model.{Employee, EmployeeRequest}
import com.example.employeeservice.repository.EmployeeRepository
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

class EmployeeService(repository: EmployeeRepository)(implicit ec: ExecutionContext) extends LazyLogging {
  
  def getAllEmployees(): Future[Seq[Employee]] = {
    logger.info("Getting all employees")
    repository.findAll()
  }
  
  def getEmployeeById(id: String): Future[Option[Employee]] = {
    logger.info(s"Getting employee with ID: $id")
    repository.findById(id)
  }
  
  def createEmployee(request: EmployeeRequest): Future[Employee] = {
    logger.info(s"Creating new employee: $request")
    val employee = Employee(
      firstName = request.firstName,
      lastName = request.lastName,
      email = request.email,
      phone = request.phone,
      position = request.position,
      department = request.department,
      salary = request.salary,
      hireDate = request.hireDate.getOrElse(java.time.LocalDate.now())
    )
    repository.save(employee)
  }
  
  def updateEmployee(id: String, request: EmployeeRequest): Future[Option[Employee]] = {
    logger.info(s"Updating employee with ID: $id")
    val employee = Employee(
      id = id,
      firstName = request.firstName,
      lastName = request.lastName,
      email = request.email,
      phone = request.phone,
      position = request.position,
      department = request.department,
      salary = request.salary,
      hireDate = request.hireDate.getOrElse(java.time.LocalDate.now())
    )
    repository.update(id, employee)
  }
  
  def deleteEmployee(id: String): Future[Boolean] = {
    logger.info(s"Deleting employee with ID: $id")
    repository.delete(id)
  }
}
