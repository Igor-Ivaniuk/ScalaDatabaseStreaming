package com.igori.model_s

import spray.json.DefaultJsonProtocol

case class ReviewDTOS(
                       id: Int,
                       customerId: Int,
                       rating: Int,
                       reviewText: String
                     )

object ReviewDTOSJsonProtocol extends DefaultJsonProtocol {
  implicit val reviewFormat = jsonFormat4(ReviewDTOS)
}

