package dynamo.repro.routes.dto

import zio.json.*

final case class ErrorResponse(msg: String, details: Option[String])

object ErrorResponse:

  given JsonDecoder[ErrorResponse] = DeriveJsonDecoder.gen[ErrorResponse]
  given JsonEncoder[ErrorResponse] = DeriveJsonEncoder.gen[ErrorResponse]

  val InternalServerError =
    ErrorResponse("Internal server error", None)
