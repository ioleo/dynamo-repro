addSbtPlugin("org.scalameta" % "sbt-scalafmt"  % "2.5.+")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix"  % "0.10.+")
addSbtPlugin("com.eed3si9n"  % "sbt-assembly"  % "2.1.+")
addSbtPlugin("com.eed3si9n"  % "sbt-buildinfo" % "0.11.+")
addSbtPlugin("io.spray"      % "sbt-revolver"  % "0.9.+")
addDependencyTreePlugin

// stay on 0.6.1 -- https://github.com/tkawachi/sbt-lock/issues/34
addSbtPlugin("com.github.tkawachi" % "sbt-lock" % "0.6.1")
