package dynamo.repro.controller

import dynamo.repro.model.Event
import dynamo.repro.dynamo.EventsDynamoStore
import zio.*

trait EventController:

  def create(event: Event): ZIO[Any, Nothing, Unit]

  def find(id: String): ZIO[Any, Nothing, Option[Event]]

object EventController:

  val live: ZLayer[EventsDynamoStore, Nothing, EventController] = ZLayer(
    for dynamoStore <- ZIO.service[EventsDynamoStore]
    yield EventControllerLive(dynamoStore)
  )
