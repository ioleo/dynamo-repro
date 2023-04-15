import Dependencies.*
import Settings.*

addCommandAlias("fmt", "; scalafmt; scalafmtSbt; Test / scalafmt")
addCommandAlias("fmtCheck", "; scalafmtCheck; scalafmtSbtCheck; Test / scalafmtCheck")
addCommandAlias("updateLock", ";unlock;reload;lock;reload")

lazy val root = (project in file(".")).minimalSettings.settings(
  name                      := "dynamorepro",
  ThisBuild / version       := "0.1.0-SNAPSHOT",
  Compile / run / mainClass := Some("dynamo.repro.Application"),
  assembly / mainClass      := (Compile / run / mainClass).value,
  libraryDependencies       := rootDependencies,
  excludeDependencies ++= excludedDependencies
)
