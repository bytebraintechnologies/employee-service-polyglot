package com.example.employeeservice.controller.health

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.typesafe.scalalogging.LazyLogging
import spray.json._
import com.example.employeeservice.controller.JsonFormats._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.lang.management.ManagementFactory
import scala.jdk.CollectionConverters._

/**
 * Health check controller for the Employee Service
 */
class HealthController extends LazyLogging with SprayJsonSupport {
  
  // Get runtime information
  private val runtime = Runtime.getRuntime
  private val operatingSystem = ManagementFactory.getOperatingSystemMXBean
  private val memoryMXBean = ManagementFactory.getMemoryMXBean
  private val threadMXBean = ManagementFactory.getThreadMXBean
  
  val routes: Route = {
    pathPrefix("health") {
      get {
        logger.info("Health check requested")
        
        // Collect system information
        val systemInfo = Map(
          "os" -> System.getProperty("os.name"),
          "version" -> System.getProperty("os.version"),
          "arch" -> System.getProperty("os.arch"),
          "scala_version" -> scala.util.Properties.versionString,
          "java_version" -> System.getProperty("java.version")
        )
        
        // Collect resource information
        val resourceInfo = Map(
          "available_processors" -> operatingSystem.getAvailableProcessors,
          "jvm_free_memory" -> runtime.freeMemory(),
          "jvm_total_memory" -> runtime.totalMemory(),
          "jvm_max_memory" -> runtime.maxMemory(),
          "heap_memory_usage" -> memoryMXBean.getHeapMemoryUsage.toString,
          "non_heap_memory_usage" -> memoryMXBean.getNonHeapMemoryUsage.toString,
          "thread_count" -> threadMXBean.getThreadCount
        )
        
        // Build health response
        val healthData = Map(
          "status" -> "UP",
          "service" -> "employee-service-scala",
          "time" -> LocalDateTime.now.format(DateTimeFormatter.ISO_DATE_TIME),
          "system" -> systemInfo,
          "resources" -> resourceInfo
        )
        
        complete(StatusCodes.OK -> healthData)
      }
    }
  }
}