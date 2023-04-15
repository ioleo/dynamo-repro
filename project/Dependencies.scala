import sbt.*

object Dependencies {

  object v {
    val scala                   = "3.2.1"
    val scalafixOrganizeImports = "0.6.0"

    // framework base
    val zio            = "2.0.+"
    val zioConfig      = "3.0.+"
    val zioInteropCats = "23.0.+"
    val zioJson        = "0.5.0"
    val zioSchema      = "0.4.10"

    // logging
    val zioLogging = "2.1.+"
    val janino     = "3.1.+"
    val coralogix  = "2.0.+"

    // http server
    val tapir   = "1.1.+"
    val zioHttp = "2.0.0-RC10"

    // dynamo db
    val zioDynamoDb = "0.2.8+5-aa0fb167-SNAPSHOT"
    val zioAws      = "5.20.42.1"
    val netty       = "4.1.86.Final"
  }

  lazy val rootDependencies =
    zio ++ zioConfig ++ zioJson ++ logging ++ tapir ++ zioHttp ++ zioSchema ++ dynamoDb

  lazy val zio = Seq(
    "dev.zio" %% "zio"              % v.zio,
    "dev.zio" %% "zio-interop-cats" % v.zioInteropCats
  )

  lazy val zioConfig = Seq(
    "dev.zio" %% "zio-config"          % v.zioConfig,
    "dev.zio" %% "zio-config-magnolia" % v.zioConfig,
    "dev.zio" %% "zio-config-typesafe" % v.zioConfig
  )

  lazy val zioJson = Seq(
    "dev.zio" %% "zio-json" % v.zioJson
  )

  lazy val logging = Seq(
    "dev.zio"            %% "zio-logging"       % v.zioLogging,
    "dev.zio"            %% "zio-logging-slf4j" % v.zioLogging,
    "org.codehaus.janino" % "janino"            % v.janino,
    "com.coralogix.sdk"   % "coralogix-sdk"     % v.coralogix,
    "com.coralogix.sdk"   % "logback-appender"  % v.coralogix
  )

  lazy val tapir = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-core"              % v.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-zio"               % v.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server"   % v.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-json-zio"          % v.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % v.tapir
  )

  lazy val zioHttp = Seq(
    "io.d11" %% "zhttp" % v.zioHttp
  )

  lazy val zioSchema = Seq(
    "dev.zio" %% "zio-schema"            % v.zioSchema,
    "dev.zio" %% "zio-schema-derivation" % v.zioSchema,
    "dev.zio" %% "zio-schema-protobuf"   % v.zioSchema
  )

  lazy val dynamoDb = Seq(
    "dev.zio" %% "zio-dynamodb" % v.zioDynamoDb,
    "io.netty" % "netty-all"    % v.netty
  )

  lazy val excludedDependencies: Seq[sbt.librarymanagement.InclExclRule] = Seq(
    "org.scala-lang.modules" % "scala-collection-compat_2.13"
  )
}
