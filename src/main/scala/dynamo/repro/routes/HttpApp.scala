package dynamo.repro.routes

import zhttp.http.{Http, Request, Response}
import zio.*

final case class HttpApp(routes: Http[Any, Throwable, Request, Response])

object HttpApp:

  val live =
    ZLayer {
      for eventRoutes <- ZIO.service[EventRoutes].map(_.routes)
      yield HttpApp(
        eventRoutes
      )
    }
