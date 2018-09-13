libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.2.5"
)

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "3.3.1")
