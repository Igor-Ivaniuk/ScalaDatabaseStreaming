package com.zpl.model_s

import slick.codegen.SourceCodeGenerator

/**
  * Created by IgorIvaniuk on 20.04.2016.
  */
object CodeGenerator extends App {
  val outputDir = ".//src/main/scala";
  val username = "user";
  val password = "user";
  val url = "jdbc:mysql://localhost/zpl";
  val jdbcDriver = "com.mysql.jdbc.Driver";
  val slickDriver = "slick.driver.MySQLDriver";
  val pkg = "com.zpl.model_s";

  SourceCodeGenerator.main(
    Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password)
  )
}
