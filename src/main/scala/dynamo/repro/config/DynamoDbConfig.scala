package dynamo.repro.config

final case class DynamoDbConfig(
  url: String,
  username: String,
  password: String,
  region: String
)
