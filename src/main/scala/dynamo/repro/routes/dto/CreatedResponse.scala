package dynamo.repro.routes.dto

import zio.json.*

final case class CreatedResponse(item: String) derives JsonCodec
