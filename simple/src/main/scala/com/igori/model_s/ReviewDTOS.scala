package com.igori.model_s

import spray.json.DefaultJsonProtocol

/**
  * Created by IgorIvaniuk on 23.07.2016.
  */
case class ReviewDTOS(
                       id: Int,
                       customerId: Int,
                       rating: Int,
                       reviewText: String
                     )

object ReviewDTOSJsonProtocol extends DefaultJsonProtocol {
  implicit val reviewFormat = jsonFormat4(ReviewDTOS)
}

