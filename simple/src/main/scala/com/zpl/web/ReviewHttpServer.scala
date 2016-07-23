package com.zpl.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.zpl.service_s.{JsonService, ReviewServiceS}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.zpl.model_s.ReviewDTOSJsonProtocol._


/**
  * Created by IgorIvaniuk on 23.07.2016.
  */

@Component
class ReviewHttpServer @Autowired()(private val reviewService: ReviewServiceS) {
  implicit val actorSystem = ActorSystem("reviews")
  implicit val materializer = ActorMaterializer()

  val jsonService = new JsonService

  val route = path("loadStream") {
    get {

      parameters("customerId") { (customerId) =>
        complete {
          val reviewsSource = reviewService.loadReviewsDTOStream(customerId.toInt)
          val chunkedStreamSource = jsonService.sourceToJsonSource(reviewsSource)

          HttpResponse(
            entity = HttpEntity.Chunked(
              MediaTypes.`application/json`,
              chunkedStreamSource
            )
          )
        }
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

  println(banner)
  Http().bindAndHandle(route, "localhost", 8081)
  println(s"Akka Server online at http://localhost:8081/")
  //  def serve(): Unit = {
  //    println(banner)
  //    Http().bindAndHandle(route, "localhost", 8081)
  //    println(s"Akka Server online at http://localhost:8081/")
  //  }
}


