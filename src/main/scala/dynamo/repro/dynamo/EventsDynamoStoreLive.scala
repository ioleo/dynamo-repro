package dynamo.repro.dynamo

import dynamo.repro.model.Event
import zio.*
import zio.dynamodb.{DynamoDBError, DynamoDBExecutor, DynamoDBQuery, PrimaryKey}

final case class EventsDynamoStoreLive(executor: DynamoDBExecutor, TABLE: String) extends EventsDynamoStore:

  def put(event: Event): Task[Unit] =
    executor.execute(DynamoDBQuery.put[Event](TABLE, event)).unit

  def get(id: String): Task[Either[DynamoDBError, Event]] =
    executor.execute(DynamoDBQuery.get[Event](TABLE, PrimaryKey("id" -> id)))

end EventsDynamoStoreLive
