package dynamo.repro.config

import com.typesafe.config.ConfigFactory
import zio.*
import zio.config.*
import zio.config.magnolia.*
import zio.config.syntax.*
import zio.config.typesafe.*

import ConfigDescriptor.*

final case class ApplicationConfig(
  server: ServerConfig,
  logging: LoggingConfig,
  dynamodb: DynamoDbConfig
)

object ApplicationConfig:

  @scala.annotation.nowarn("msg=infix")
  private val configuration =
    (
      nested("server")(descriptor[ServerConfig]) zip
        nested("logging")(LoggingConfig.configuration) zip
        nested("dynamodb")(descriptor[DynamoDbConfig])
    ).to[ApplicationConfig]

  private val typesafeConfig = TypesafeConfig.fromResourcePath(configuration)

  val live =
    typesafeConfig.narrow(_.server) ++
      typesafeConfig.narrow(_.logging) ++
      typesafeConfig.narrow(_.dynamodb)
