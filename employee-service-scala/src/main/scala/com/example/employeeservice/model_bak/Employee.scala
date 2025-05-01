package com.example.employeeservice.model

import java.util.UUID
import java.time.LocalDate

case class Employee(
  id: String = UUID.randomUUID().toString,
  firstName: String,
  lastName: String,
  email: String,
  phone: Option[String] = None,
  position: String,
  department: String,
  salary: BigDecimal,
  hireDate: LocalDate = LocalDate.now()
)

case class EmployeeRequest(
  firstName: String,
  lastName: String,
  email: String,
  phone: Option[String],
  position: String,
  department: String,
  salary: BigDecimal,
  hireDate: Option[LocalDate]
)

case class ErrorResponse(
  message: String,
  timestamp: Long = System.currentTimeMillis()
)
