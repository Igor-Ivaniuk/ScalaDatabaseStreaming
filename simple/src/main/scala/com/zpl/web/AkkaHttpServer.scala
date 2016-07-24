package com.zpl.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.CorsDirectives._
import com.zpl.model_s.ReviewDTOSJsonProtocol._
import com.zpl.service_s.{JsonService, ReviewServiceS}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


/**
  * Created by IgorIvaniuk on 23.07.2016.
  */

@Component
class AkkaHttpServer @Autowired()(private val reviewService: ReviewServiceS) {
  implicit val actorSystem = ActorSystem("reviews")
  implicit val materializer = ActorMaterializer()

  val jsonService = new JsonService

  val route: Route = cors() {
    path("rest" / "loadStream") {
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
}


