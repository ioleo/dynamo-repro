package dynamo.repro.routes

import java.util.concurrent.TimeUnit

import dynamo.repro.controller.EventController
import dynamo.repro.model.Event
import dynamo.repro.routes.dto.*
import sttp.model.StatusCode
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir.*
import sttp.tapir.{AnyEndpoint, Endpoint}
import zhttp.http.{Http, Request, Response}
import zio.*
import zio.interop.catz.*
import zio.json.*

final case class EventRoutes(routes: Http[Any, Throwable, Request, Response], endpoints: List[AnyEndpoint])

object EventRoutes:

  val create: Endpoint[Unit, Event, ErrorResponse, Unit, Any] =
    endpoint.post
      .in("events")
      .in(jsonBody[Event])
      .description("An endpoint for creating events.")
      .errorOut(statusCode(StatusCode.InternalServerError).and(jsonBody[ErrorResponse]))
      .out(statusCode(StatusCode.Ok))

  val find: Endpoint[Unit, String, ErrorResponse, Option[Event], Any] =
    endpoint.get
      .in("events" / path[String]("id"))
      .description("An endpoint for finding events.")
      .errorOut(statusCode(StatusCode.InternalServerError).and(jsonBody[ErrorResponse]))
      .out(statusCode(StatusCode.Ok).and(jsonBody[Option[Event]]))

  val live: URLayer[EventController, EventRoutes] =
    ZLayer(
      for controller <- ZIO.service[EventController]
      yield EventRoutes(
        ZioHttpInterpreter().toHttp(
          List(
            create.zServerLogic(controller.create),
            find.zServerLogic(controller.find)
          )
        ),
        List(create, find)
      )
    )

end EventRoutes
