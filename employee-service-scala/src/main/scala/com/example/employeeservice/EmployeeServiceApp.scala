package com.example.employeeservice

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
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
  
  // Define routes
  val routes: Route = concat(
    pathEndOrSingleSlash {
      get {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Welcome to Employee Service (Scala)"))
      }
    },
    path("health") {
      get {
        val healthData = Map(
          "status" -> "UP",
          "service" -> serviceName,
          "version" -> "1.0",
          "timestamp" -> System.currentTimeMillis().toString
        )
        
        complete(StatusCodes.OK, healthData.toString())
      }
    }
  )
  
  // Start HTTP server
  val serverBinding: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(routes)
  
  serverBinding.onComplete {
    case Success(binding) =>
      val address = binding.localAddress
      logger.info(s"$serviceName is running at http://${address.getHostString}:${address.getPort}/")
    case Failure(ex) =>
      logger.error(s"Failed to start $serviceName: ${ex.getMessage}", ex)
      system.terminate()
  }
}
