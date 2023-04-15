package dynamo.repro.config

import zio.*
import zio.config.*

final case class LoggingConfig(level: LogLevel)

object LoggingConfig {

  given configuration: ConfigDescriptor[LoggingConfig] =
    ConfigDescriptor
      .string("logLevel")
      .mapEither {
        case "debug" | "DEBUG"                       => Right(LogLevel.Debug)
        case "info" | "INFO"                         => Right(LogLevel.Info)
        case "warn" | "WARN" | "warning" | "WARNING" => Right(LogLevel.Warning)
        case "error" | "ERROR"                       => Right(LogLevel.Error)
        case "fatal" | "FATAL"                       => Right(LogLevel.Fatal)
        case "trace" | "TRACE"                       => Right(LogLevel.Trace)
        case "off" | "OFF" | "none" | "NONE"         => Right(LogLevel.None)
        case other                                   => Left(s"Invalid log level value $other")
      }
      .map(LoggingConfig.apply)
}
