package dynamo.repro.dynamo

import dynamo.repro.model.Event
import zio.*
import zio.dynamodb.*

trait EventsDynamoStore:

  def put(event: Event): Task[Unit]

  def get(id: String): Task[Either[DynamoDBError, Event]]

end EventsDynamoStore

object EventsDynamoStore:

  val live: ZLayer[DynamoDBExecutor, Throwable, EventsDynamoStore] = ZLayer(
    for
      executor <- ZIO.service[DynamoDBExecutor]
      tableName = "events"
      _        <- executor
                    .execute(
                      DynamoDBQuery.createTable(tableName, KeySchema("id"), BillingMode.PayPerRequest)(
                        AttributeDefinition.attrDefnString("id")
                      )
                    )
                    .ignoreLogged
    yield EventsDynamoStoreLive(executor, tableName)
  )

end EventsDynamoStore
