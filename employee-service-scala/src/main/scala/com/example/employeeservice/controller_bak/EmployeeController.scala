package com.example.employeeservice.controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.employeeservice.model.{EmployeeRequest, ErrorResponse}
import com.example.employeeservice.service.EmployeeService
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class EmployeeController(service: EmployeeService)(implicit ec: ExecutionContext) 
    extends LazyLogging with FailFastCirceSupport {
  
  val routes: Route = {
    pathPrefix("api" / "employees") {
      concat(
        pathEnd {
          concat(
            get {
              logger.debug("GET request to fetch all employees")
              onComplete(service.getAllEmployees()) {
                case Success(employees) => complete(employees)
                case Failure(ex) => 
                  logger.error(s"Error fetching all employees: ${ex.getMessage}", ex)
                  complete(StatusCodes.InternalServerError -> ErrorResponse(s"Failed to get employees: ${ex.getMessage}"))
              }
            },
            post {
              entity(as[EmployeeRequest]) { request =>
                logger.debug(s"POST request to create employee: $request")
                onComplete(service.createEmployee(request)) {
                  case Success(employee) => complete(StatusCodes.Created -> employee)
                  case Failure(ex) => 
                    logger.error(s"Error creating employee: ${ex.getMessage}", ex)
                    complete(StatusCodes.InternalServerError -> ErrorResponse(s"Failed to create employee: ${ex.getMessage}"))
                }
              }
            }
          )
        },
        path(Segment) { id =>
          concat(
            get {
              logger.debug(s"GET request to fetch employee with ID: $id")
              onComplete(service.getEmployeeById(id)) {
                case Success(Some(employee)) => complete(employee)
                case Success(None) => 
                  logger.warn(s"Employee not found with ID: $id")
                  complete(StatusCodes.NotFound -> ErrorResponse(s"Employee not found with ID: $id"))
                case Failure(ex) => 
                  logger.error(s"Error fetching employee $id: ${ex.getMessage}", ex)
                  complete(StatusCodes.InternalServerError -> ErrorResponse(s"Failed to get employee: ${ex.getMessage}"))
              }
            },
            put {
              entity(as[EmployeeRequest]) { request =>
                logger.debug(s"PUT request to update employee with ID: $id")
                onComplete(service.updateEmployee(id, request)) {
                  case Success(Some(employee)) => complete(employee)
                  case Success(None) => 
                    logger.warn(s"Employee not found with ID: $id")
                    complete(StatusCodes.NotFound -> ErrorResponse(s"Employee not found with ID: $id"))
                  case Failure(ex) => 
                    logger.error(s"Error updating employee $id: ${ex.getMessage}", ex)
                    complete(StatusCodes.InternalServerError -> ErrorResponse(s"Failed to update employee: ${ex.getMessage}"))
                }
              }
            },
            delete {
              logger.debug(s"DELETE request for employee with ID: $id")
              onComplete(service.deleteEmployee(id)) {
                case Success(true) => complete(StatusCodes.NoContent)
                case Success(false) => 
                  logger.warn(s"Employee not found with ID: $id")
                  complete(StatusCodes.NotFound -> ErrorResponse(s"Employee not found with ID: $id"))
                case Failure(ex) => 
                  logger.error(s"Error deleting employee $id: ${ex.getMessage}", ex)
                  complete(StatusCodes.InternalServerError -> ErrorResponse(s"Failed to delete employee: ${ex.getMessage}"))
              }
            }
          )
        }
      )
    }
  }
}
