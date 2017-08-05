import scalatex.ScalatexReadme

lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "Scala.js DOM"

crossScalaVersions in ThisBuild := Seq("2.12.3", "2.11.11", "2.10.6", "2.13.0-M1")
scalaVersion in ThisBuild := crossScalaVersions.value.head

val commonSettings = Seq(
  version := "0.9.4-SNAPSHOT",
  organization := "org.scala-js",
  scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings")
)

normalizedName := "scalajs-dom"

commonSettings

homepage := Some(url("http://scala-js.org/"))

licenses += ("MIT", url("http://opensource.org/licenses/mit-license.php"))

scalacOptions ++= {
  if (isSnapshot.value)
    Seq.empty
  else {
    val a = baseDirectory.value.toURI
    val g = "https://raw.githubusercontent.com/scala-js/scala-js-dom"
    Seq(s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/")
  }
}

scalacOptions ++= {
  if (scalaJSVersion.startsWith("0.6.")) Seq("-P:scalajs:sjsDefinedByDefault")
  else Nil
}

scmInfo := Some(ScmInfo(
    url("https://github.com/scala-js/scala-js-dom"),
    "scm:git:git@github.com:scala-js/scala-js-dom.git",
    Some("scm:git:git@github.com:scala-js/scala-js-dom.git")))

publishMavenStyle := true

/*publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}*/

////////////////// My Publish Uandrew1965//////////////////////////////////////////////////////////
publishTo := {
    val corporateRepo = "http://toucan.simplesys.lan/"
    if (isSnapshot.value)
        Some("snapshots" at corporateRepo + "artifactory/libs-snapshot-local")
    else
        Some("releases" at corporateRepo + "artifactory/libs-release-local")
}
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
////////////////// End My Publish Uandrew1965//////////////////////////////////////////////////////////

pomExtra := (
    <developers>
      <developer>
        <id>lihaoyi</id>
        <name>Li Haoyi</name>
        <url>https://github.com/lihaoyi/</url>
      </developer>
      <developer>
        <id>sjrd</id>
        <name>Sébastien Doeraene</name>
        <url>https://github.com/sjrd/</url>
      </developer>
      <developer>
        <id>gzm0</id>
        <name>Tobias Schlatter</name>
        <url>https://github.com/gzm0/</url>
      </developer>
    </developers>
)

pomIncludeRepository := { _ => false }

lazy val readme = ScalatexReadme(
  folder = "readme",
  url = "https://github.com/scala-js/scala-js-dom/tree/master",
  source = "Index",
  targetFolder = "target/site",
  autoResources = Seq("example-opt.js")
).settings(
  scalaVersion := "2.12.3",
  (resources in Compile) += (fullOptJS in (example, Compile)).value.data
)

lazy val example = project.
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  dependsOn(root)
