package com.example.employeeservice.controller

import spray.json._

/**
 * JSON formats for various data types needed by the application
 */
object JsonFormats extends DefaultJsonProtocol {
  
  // Format for Map[String, Any]
  implicit object AnyJsonFormat extends JsonFormat[Any] {
    def write(x: Any): JsValue = x match {
      case n: Int => JsNumber(n)
      case n: Long => JsNumber(n)
      case n: Double => JsNumber(n)
      case n: BigDecimal => JsNumber(n)
      case s: String => JsString(s)
      case b: Boolean if b => JsTrue
      case b: Boolean if !b => JsFalse
      case m: Map[_, _] => mapFormat[String, Any].write(m.asInstanceOf[Map[String, Any]])
      case l: Seq[_] => JsArray(l.map(write).toVector)
      case l: List[_] => JsArray(l.map(write).toVector)
      case a: Array[_] => JsArray(a.map(write).toVector)
      case o: Option[_] => o match {
        case Some(v) => write(v)
        case None => JsNull
      }
      case null => JsNull
      case x => JsString(x.toString)
    }

    def read(value: JsValue): Any = value match {
      case JsNumber(n) => n
      case JsString(s) => s
      case JsTrue => true
      case JsFalse => false
      case JsArray(elements) => elements.map(read).toList
      case JsObject(fields) => fields.map { case (name, value) => name -> read(value) }.toMap
      case JsNull => null
    }
  }
  
  // Format for Map[String, Any]
  implicit val mapFormat: RootJsonFormat[Map[String, Any]] = new RootJsonFormat[Map[String, Any]] {
    def write(map: Map[String, Any]): JsValue = JsObject(
      map.map { case (key, value) => key -> AnyJsonFormat.write(value) }
    )
    
    def read(value: JsValue): Map[String, Any] = value match {
      case JsObject(fields) => fields.map { case (key, value) => key -> AnyJsonFormat.read(value) }.toMap
      case _ => throw DeserializationException("Map expected")
    }
  }
}