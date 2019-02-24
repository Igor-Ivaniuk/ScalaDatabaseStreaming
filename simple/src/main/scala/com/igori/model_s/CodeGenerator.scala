package com.igori.model_s

import slick.codegen.SourceCodeGenerator

object CodeGenerator extends App {
  val outputDir = ".//src/main/scala";
  val username = "user";
  val password = "user";
  val url = "jdbc:mysql://localhost/igori";
  val jdbcDriver = "com.mysql.jdbc.Driver";
  val slickDriver = "slick.driver.MySQLDriver";
  val pkg = "com.igori.model_s";

  SourceCodeGenerator.main(
    Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password)
  )
}
