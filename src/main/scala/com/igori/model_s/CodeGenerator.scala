package com.igori.model_s

import slick.codegen.SourceCodeGenerator

object CodeGenerator extends App {
  val outputDir = ".//src/main/scala"
  val user = "user"
  val password = "user"
  val url = "jdbc:mysql://localhost:3306/sds?useSSL=false"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val profile  = "slick.jdbc.MySQLProfile"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "com.igori.model_s"

  SourceCodeGenerator.main(
    Array(profile, jdbcDriver, url, outputDir, pkg, user, password)
  )
}
