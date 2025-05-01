package com.example.employeeservice

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.example.employeeservice.controller.EmployeeController
import com.example.employeeservice.repository.EmployeeRepository
import com.example.employeeservice.service.EmployeeService
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object EmployeeServiceApp extends App with LazyLogging {
  
  // Load configuration
  val config = ConfigFactory.load()
  val interface = config.getString("http.interface")
  val port = config.getInt("http.port")
  val serviceName = config.getString("service.name")
  
  logger.info(s"Starting $serviceName...")
  
  // Create actor system
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, serviceName)
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  
  // Initialize components
  val repository = new EmployeeRepository()
  val service = new EmployeeService(repository)
  val controller = new EmployeeController(service)
  
  // Start HTTP server
  val routes: Route = controller.routes
  val serverBinding: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(routes)
  
  serverBinding.onComplete {
    case Success(binding) =>
      val address = binding.localAddress
      logger.info(s"$serviceName is running at http://${address.getHostString}:${address.getPort}/")
      
      // Add some sample data for testing
      addSampleData(service)
      
    case Failure(ex) =>
      logger.error(s"Failed to start $serviceName: ${ex.getMessage}", ex)
      system.terminate()
  }
  
  // Add some sample data
  private def addSampleData(service: EmployeeService): Unit = {
    import java.time.LocalDate
    
    val employees = Seq(
      EmployeeRequest(
        firstName = "John",
        lastName = "Doe",
        email = "john.doe@example.com",
        phone = Some("555-123-4567"),
        position = "Software Engineer",
        department = "Engineering",
        salary = BigDecimal("85000.00"),
        hireDate = Some(LocalDate.of(2020, 3, 15))
      ),
      EmployeeRequest(
        firstName = "Jane",
        lastName = "Smith",
        email = "jane.smith@example.com",
        phone = Some("555-987-6543"),
        position = "Product Manager",
        department = "Product",
        salary = BigDecimal("95000.00"),
        hireDate = Some(LocalDate.of(2019, 7, 10))
      ),
      EmployeeRequest(
        firstName = "Michael",
        lastName = "Johnson",
        email = "michael.johnson@example.com",
        phone = Some("555-234-5678"),
        position = "UX Designer",
        department = "Design",
        salary = BigDecimal("78000.00"),
        hireDate = Some(LocalDate.of(2021, 1, 5))
      )
    )
    
    logger.info("Adding sample employees...")
    employees.foreach { emp =>
      service.createEmployee(emp).onComplete {
        case Success(created) => logger.info(s"Created sample employee: ${created.firstName} ${created.lastName} with ID ${created.id}")
        case Failure(ex) => logger.error(s"Failed to create sample employee: ${ex.getMessage}", ex)
      }
    }
  }
}
