package dynamo.repro.dynamo

import dynamo.repro.config.DynamoDbConfig
import zio.*
import zio.aws.core.config.{AwsConfig, ClientCustomization, CommonAwsConfig}
import software.amazon.awssdk.auth.credentials.{AwsBasicCredentials, StaticCredentialsProvider}
import software.amazon.awssdk.regions.Region

import java.net.URI

object DynamoDatabase:

  val commonAwsConfig: ZLayer[DynamoDbConfig, Nothing, CommonAwsConfig] = ZLayer.scoped(
    for config <- ZIO.service[DynamoDbConfig]
    yield CommonAwsConfig(
      region = Some(Region.of(config.region)),
      credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(config.username, config.password)),
      endpointOverride = Some(URI.create(config.url)),
      commonClientConfig = None
    )
  )
