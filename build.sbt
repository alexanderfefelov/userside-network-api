name := "userside-network-api"
organization := "com.github.alexanderfefelov"

lazy val scalaV = "2.12.6"

lazy val jodaV = "2.10"
lazy val logbackClassicV = "1.2.3"
lazy val postgresqlJdbcDriverV = "42.2.5"
lazy val scalikejdbcV = "3.3.1"
lazy val scalikejdbcSyntaxSupportMacroV = scalikejdbcV
lazy val scalikejdbcJodaTimeV = scalikejdbcV
lazy val typesafeConfigV = "1.3.1"

lazy val root = (project in file("."))
  .aggregate(db, util)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val commonSettings = Seq(
  organization := "com.github.alexanderfefelov",
  scalaVersion := scalaV,
  version := "0.1.0-SNAPSHOT",
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion,
    "builtBy" -> {System.getProperty("user.name")},
    "builtOn" -> {java.net.InetAddress.getLocalHost.getHostName},
    "builtAt" -> {new java.util.Date()},
    "builtAtMillis" -> {System.currentTimeMillis()}
  )
)

lazy val db = (project in file("db"))
  .aggregate(dbRepository, dbUtil)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val dbTargetPackage = "com.github.alexanderfefelov.userside.network.api.db"

lazy val dbRepositoryTargetPackage = dbTargetPackage + ".repository"
lazy val dbRepository = (project in file("db/repository"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "userside-network-api-db-repository",
    commonSettings,
    buildInfoPackage := dbRepositoryTargetPackage
  )

lazy val dbUtilTargetPackage = dbTargetPackage + ".util"
lazy val dbUtil = (project in file("db/util"))
  .dependsOn(util)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "userside-network-api-db-util",
    commonSettings,
    buildInfoPackage := dbUtilTargetPackage,
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcV,
      "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikejdbcSyntaxSupportMacroV,
      "org.scalikejdbc" %% "scalikejdbc-joda-time" % scalikejdbcJodaTimeV,
      "org.postgresql" % "postgresql" % postgresqlJdbcDriverV,
      "ch.qos.logback" % "logback-classic" % logbackClassicV
    )
  )

lazy val utilTargetPackage = "com.github.alexanderfefelov.userside.network.api.util"
lazy val util = (project in file("util"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "userside-network-api-util",
    commonSettings,
    buildInfoPackage := utilTargetPackage,
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % typesafeConfigV
    )
  )
