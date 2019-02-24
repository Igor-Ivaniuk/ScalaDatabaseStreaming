package com.igori.service_s

import akka.NotUsed
import akka.http.scaladsl.model.HttpEntity.ChunkStreamPart
import akka.stream.scaladsl.Source
import org.springframework.stereotype.Component
import spray.json._

@Component
class JsonService {

  def sourceToJsonSource[T](source: Source[T, NotUsed])(implicit writer: JsonWriter[T]): Source[ChunkStreamPart, NotUsed] = {

    val separated: Source[ChunkStreamPart, NotUsed] =
      source
        .map(t => Some(t.toJson.compactPrint))
        .scan[Option[ChunkStreamPart]](None)({
        case (None, Some(sourceElement)) => Some(ChunkStreamPart(sourceElement))
        case (_, Some(sourceElement)) => Some(ChunkStreamPart(s"\n\n$sourceElement"))
      }).mapConcat(_.toList)

    separated
  }
}
