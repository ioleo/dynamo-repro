package dynamo.repro.model

import java.time.Instant

import zio.json.JsonCodec
import zio.schema.{DeriveSchema, Schema}

final case class Event(id: String, action: String, timestamp: Instant) derives JsonCodec

object Event:

  given Schema[Event] = DeriveSchema.gen[Event]
