name := "db-reverse"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.6"

lazy val p = (project in file(".")).enablePlugins(ScalikejdbcPlugin)

scalikejdbcGeneratorSettings in Compile ~= { settings =>
  settings.copy(
    tableNamesToSkip = Seq("pbl_map_polygon")
  )
}
