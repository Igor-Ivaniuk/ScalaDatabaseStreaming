package com.igori.service_s

import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Component
import slick.driver.MySQLDriver.api._

@Component
class DbHolder extends DisposableBean {

  val db = Database.forConfig("mydb")

  override def destroy(): Unit = {
    db.close()
  }
}
