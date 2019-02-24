package com.igori.service_s

import java.util

import akka.NotUsed
import akka.stream.scaladsl._
import com.igori.model.ReviewDTO
import com.igori.model_s.ReviewDTOS
import com.igori.model_s.Tables.{Review, ReviewRow}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import slick.driver.MySQLDriver.api._
import slick.lifted.TableQuery

import scala.collection.JavaConverters._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

@Component
class ReviewServiceS @Autowired()(private val dbHolder: DbHolder) {

  val reviewsTable = TableQuery[Review];

  def loadReviews(customerId: Int): util.List[ReviewDTO] = {
    val reviewsByCustomer = reviewsTable.filter(row => row.customerId === customerId)
    val action: DBIO[Seq[ReviewRow]] = reviewsByCustomer.result
    val execFuture: Future[Seq[ReviewRow]] = dbHolder.db.run(action)
    val resultRows = Await.result(execFuture, 30 seconds)
    resultRows.map(row => toDto(row))
      .asJava
  }

  def loadReviewsDTOStream(customerId: Int): Source[ReviewDTOS, NotUsed] = {
    val reviewsByCustomer = reviewsTable.filter(row => row.customerId === customerId)
    val dbStream = dbHolder.db.stream(reviewsByCustomer.result).mapResult(row => toDtoS(row))
    Source.fromPublisher(dbStream)
  }

  private def toDto(row: ReviewRow): ReviewDTO = {
    ReviewDTO.builder()
      .id(row.id)
      .customerId(row.customerId)
      .rating(row.rating)
      .reviewText(row.reviewText.get)
      .build()
  }

  private def toDtoS(row: ReviewRow): ReviewDTOS = {
    val dto = ReviewDTOS(row.id, row.customerId, row.rating, row.reviewText.get)
    dto
  }
}
