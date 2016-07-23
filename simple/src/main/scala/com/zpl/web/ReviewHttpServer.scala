package com.zpl.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

/**
  * Created by IgorIvaniuk on 23.07.2016.
  */


class ReviewHttpServer {
  implicit val actorSystem = ActorSystem("reviews")
  implicit val materializer = ActorMaterializer()

  val route = path("loadStream") {
    get {
      parameters("customerId") { (customerId) =>
        complete(s"CustomerId = $customerId")
      }
    }
  }

  val banner =
    s"""
       |....###....##....##.##....##....###.......##.....##.########.########.########.
       |...##.##...##...##..##...##....##.##......##.....##....##.......##....##.....##
       |..##...##..##..##...##..##....##...##.....##.....##....##.......##....##.....##
       |.##.....##.#####....#####....##.....##....#########....##.......##....########.
       |.#########.##..##...##..##...#########....##.....##....##.......##....##.......
       |.##.....##.##...##..##...##..##.....##....##.....##....##.......##....##.......
       |.##.....##.##....##.##....##.##.....##....##.....##....##.......##....##.......
    """.stripMargin

  def serve(): Unit = {
    println(banner)
    Http().bindAndHandle(route, "localhost", 8081)
    println(s"Akka Server online at http://localhost:8081/")
  }
}


