package com.zpl.service_s

import java.util

import akka.NotUsed
import com.zpl.model.ReviewDTO
import com.zpl.model_s.Tables.{Review, ReviewRow}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import slick.backend.DatabasePublisher
import slick.driver.MySQLDriver.api._
import slick.lifted.TableQuery

import scala.collection.JavaConverters._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import akka.stream.scaladsl._

/**
  * Created by IgorIvaniuk on 08.05.2016.
  */
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

  def loadReviewsDbStream(customerId: Int): DatabasePublisher[ReviewDTO] = {
    val reviewsByCustomer = reviewsTable.filter(row => row.customerId === customerId)
    dbHolder.db.stream(reviewsByCustomer.result).mapResult(row => toDto(row))
  }

  def loadReviewsDTOStream(customerId: Int): Source[ReviewDTO,NotUsed] = {
    Source.fromPublisher(loadReviewsDbStream(customerId))
  }

  private def toDto(row: ReviewRow): ReviewDTO = {
    ReviewDTO.builder()
      .id(row.id)
      .customerId(row.customerId)
      .rating(row.rating)
      .reviewText(row.reviewText.get)
      .build()
  }
}
