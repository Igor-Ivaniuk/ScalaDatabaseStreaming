package com.zpl.service_s

import akka.NotUsed
import akka.http.scaladsl.model.HttpEntity.ChunkStreamPart
import akka.stream.scaladsl.Source
import spray.json._

/**
  * Created by IgorIvaniuk on 23.07.2016.
  */
class JsonService {

  def sourceToJsonSource[T](source: Source[T, NotUsed])(implicit writer: JsonWriter[T]): Source[ChunkStreamPart, NotUsed] = {

    val commaSeparated: Source[ChunkStreamPart, NotUsed] =
      source
        .map(t => Some(t.toJson.compactPrint))
        .scan[Option[ChunkStreamPart]](None)({
        case (None, Some(sourceElement)) => Some(ChunkStreamPart(sourceElement))
        case (_, Some(sourceElement)) => Some(ChunkStreamPart(s", $sourceElement"))
      }).mapConcat(_.toList)

    Source.single(ChunkStreamPart("["))
      .concat(commaSeparated)
      .concat(Source.single(ChunkStreamPart("]")))
  }
}
