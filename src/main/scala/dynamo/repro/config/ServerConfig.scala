package dynamo.repro.config

final case class ServerConfig(
  host: String,
  port: Int,
  corsAllowOrigins: List[String]
)
