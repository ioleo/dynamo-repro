package dynamo.repro.controller

import dynamo.repro.model.Event
import dynamo.repro.dynamo.EventsDynamoStore
import zio.*

final case class EventControllerLive(store: EventsDynamoStore) extends EventController:

  def create(event: Event): ZIO[Any, Nothing, Unit] = store.put(event).orDie

  def find(id: String): ZIO[Any, Nothing, Option[Event]] = store.get(id).map(_.toOption).orDie
