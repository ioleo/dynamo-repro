package dynamo.repro

import _root_.dynamo.repro.config.{ApplicationConfig, LoggingConfig, ServerConfig}
import _root_.dynamo.repro.controller.EventController
import _root_.dynamo.repro.dynamo.{DynamoDatabase, EventsDynamoStore}
import _root_.dynamo.repro.routes.{EventRoutes, HttpApp}
import zhttp.http.middleware.Cors.CorsConfig
import zhttp.http.{Http, Middleware}
import zhttp.service.Server
import zio.*
import zio.aws.core.config.AwsConfig
import zio.aws.dynamodb.DynamoDb
import zio.aws.netty.NettyHttpClient
import zio.dynamodb.DynamoDBExecutor
import zio.logging.backend.SLF4J
import zio.schema.codec.*

object Application extends ZIOAppDefault:

  lazy val program =
    ZIO.service[LoggingConfig].flatMap { logCfg =>
      logCfg.level {
        (for
          _         <- ZIO.logInfo("Application starting...")
          serverCfg <- ZIO.service[ServerConfig]
          httpApp   <- ZIO.service[HttpApp]
          cors       = CorsConfig(anyOrigin = false, allowedOrigins = serverCfg.corsAllowOrigins.contains(_))
          _         <- ZIO.logInfo(s"Starting HTTP server at ${serverCfg.host}:${serverCfg.port}...")
          _         <- Server.start(serverCfg.port, httpApp.routes @@ Middleware.cors(cors))
        yield ())
          .onInterrupt(ZIO.logInfo("Gracefully shutting down..."))
          .catchAllDefect { case defect =>
            ZIO.logFatal(s"Shutting down due to fatal defect:\n$defect")
          }
          .absorb
          .catchAllTrace { case (_, trace) =>
            val message = trace.prettyPrint
            ZIO.logFatal(s"Shutting down due to fatal defect:\n$message")
          }
      }
    }

  override val run =
    program.provide(
      ApplicationConfig.live,
      HttpApp.live,
      EventRoutes.live,
      EventController.live,
      EventsDynamoStore.live.orDie,
      NettyHttpClient.default.orDie,
      DynamoDatabase.commonAwsConfig,
      AwsConfig.configured(),
      DynamoDb.live,
      DynamoDBExecutor.live
    )

  override val bootstrap: ZLayer[Any, Nothing, Unit] =
    Runtime.removeDefaultLoggers >>> SLF4J.slf4j

end Application
