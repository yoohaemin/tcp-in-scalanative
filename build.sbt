
scalaVersion in ThisBuild := "2.11.12"

// Set to false or remove if you want to show stubs as linking errors
nativeLinkStubs := true

cancelable in Global := true

libraryDependencies ++= {
  Seq()
}
enablePlugins(ScalaNativePlugin)
