package com.example.employeeservice.config

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.circe.{Decoder, Encoder}
import io.circe.syntax._

object JsonSupport {
  // Custom encoders and decoders for LocalDate
  implicit val encodeLocalDate: Encoder[LocalDate] = Encoder.encodeString.contramap[LocalDate] { date =>
    date.format(DateTimeFormatter.ISO_LOCAL_DATE)
  }

  implicit val decodeLocalDate: Decoder[LocalDate] = Decoder.decodeString.emap { str =>
    try {
      Right(LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE))
    } catch {
      case e: Exception => Left(s"Failed to parse date: ${e.getMessage}")
    }
  }
}
