# mstemplate

This application was generated using JHipster 9.0.0, you can find documentation and help at https://www.jhipster.tech/documentation-archive/v9.0.0/2026/03/10/jhipster-release-9.0.0.html.

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

## How to use this template

1. Clone xm-ms-template to separate folder.
2. Start project in you local XM environment and check if it is working, run tests.
3. Remove `.git/` folder before project modification.
4. Add project to new git repository where it will evolve and live.
5. Find and replace `mstemplate` term everywhere in the projects.
6. Find end remove everything related to `ExampleEntityFirst` and `ExampleEntitySecond` (after checking how it is proposed to use)
7. Do you need to use database:
   - if YES - correct liquibase scripts in `resources/config/liquibase`
   - if NO - remove liquibase scripts, config `XmDatabaseConfiguration` and commons `xm-commons-migration-db`
8. Check all `com.icthh.xm.commons` libraries in `build.gradle` and remove redundant. try to keep minimum set of commons.

## Project Structure

`/src/*/java` structure follows default Java structure.
`/src/*/lep` structure follows LEP folder structure containing groovy files. Source folder is generated as symlink by XM development plugin.
`/src/main/docker` - Docker configurations for the application and services that the application depends on.

## XME features and use cases

Most of the XME features can be turned on using xm-commons dependencies.
**NOTE** In this template project all commons are included. You need to select only needed.

### Logger config

Logger is configured by adding `xm-commons-logging-web` dependency transitively from `xm-commons-ms-web`.

### Spring profiles supported by default:

- dev
- prod
- no-liquibase
- api-docs

### Multi tenant Database configuration

Database support multi tenancy by adding commons `xm-commons-migration-db`
Configuration files for Liquibase are in `resources/config/liquibase`.

**NOTE:** on the micoservice level you still need custom configuration for DB:
`com.icthh.xm.ms.mstemplate.config.XmDatabaseConfiguration`

Here you can decide if you need or do not need H2 and also provide base package for scan.

### Service discovery modes

1. Consul (default) - uses Consul as a service discovery mechanism.
2. External - allows to configure service IPs from external source. Can be configured using next section in `application.yml`:

```yaml
spring:
  cloud:
    discovery:
      client:
        simple:
          instances:
            config:
              - instanceId: config
                serviceId: config
                host: localhost
                port: 8084
    consul:
      enabled: false
```

### Docker build

[//]: # '# TODO it is planned to use Jib. Need to migrate our Dockerfile to gradle/docker.gradle'

### MS config

Microservice config connected using `xm-commons-config` usually transitively by some other commons.

### Security config

Security by default autoconfigured from `xm-commons-security` module.
You can extend `com.icthh.xm.commons.security.spring.config.SecurityConfiguration` and override behavior if need.

### Kafka connection

The connection is set by environment variables. Full sets for each `security.protocol`:

PLAINTEXT, no authentication and no encryption:

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:19092
SPRING_KAFKA_PROPERTIES_SECURITY_PROTOCOL=PLAINTEXT
```

SASL_PLAINTEXT, login and password, no encryption:

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:19093
SPRING_KAFKA_PROPERTIES_SECURITY_PROTOCOL=SASL_PLAINTEXT
SPRING_KAFKA_PROPERTIES_SASL_MECHANISM=SCRAM-SHA-512
SPRING_KAFKA_PROPERTIES_SASL_JAAS_CONFIG=org.apache.kafka.common.security.scram.ScramLoginModule required username="mstemplate" password="secret";
```

SSL, encryption and a client certificate (the key store is needed when the broker requires client authentication):

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:19094
SPRING_KAFKA_PROPERTIES_SECURITY_PROTOCOL=SSL
SPRING_KAFKA_SSL_TRUST_STORE_LOCATION=file:/certs/ca.p12
SPRING_KAFKA_SSL_TRUST_STORE_PASSWORD=changeit
SPRING_KAFKA_SSL_TRUST_STORE_TYPE=PKCS12
SPRING_KAFKA_SSL_KEY_STORE_LOCATION=file:/certs/mstemplate.p12
SPRING_KAFKA_SSL_KEY_STORE_PASSWORD=changeit
SPRING_KAFKA_SSL_KEY_STORE_TYPE=PKCS12
SPRING_KAFKA_SSL_KEY_PASSWORD=changeit
SPRING_KAFKA_SSL_PROTOCOL=TLSv1.3
```

SASL_SSL, login and password over encryption:

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:19095
SPRING_KAFKA_PROPERTIES_SECURITY_PROTOCOL=SASL_SSL
SPRING_KAFKA_PROPERTIES_SASL_MECHANISM=SCRAM-SHA-512
SPRING_KAFKA_PROPERTIES_SASL_JAAS_CONFIG=org.apache.kafka.common.security.scram.ScramLoginModule required username="mstemplate" password="secret";
SPRING_KAFKA_SSL_TRUST_STORE_LOCATION=file:/certs/ca.p12
SPRING_KAFKA_SSL_TRUST_STORE_PASSWORD=changeit
SPRING_KAFKA_SSL_TRUST_STORE_TYPE=PKCS12
SPRING_KAFKA_SSL_KEY_STORE_LOCATION=file:/certs/mstemplate.p12
SPRING_KAFKA_SSL_KEY_STORE_PASSWORD=changeit
SPRING_KAFKA_SSL_KEY_STORE_TYPE=PKCS12
SPRING_KAFKA_SSL_KEY_PASSWORD=changeit
SPRING_KAFKA_SSL_PROTOCOL=TLSv1.3
```

PEM instead of the store files: `SPRING_KAFKA_SSL_KEY_STORE_TYPE=PEM` with `SPRING_KAFKA_SSL_KEY_STORE_CERTIFICATE_CHAIN`
and `SPRING_KAFKA_SSL_KEY_STORE_KEY`, `SPRING_KAFKA_SSL_TRUST_STORE_TYPE=PEM` with `SPRING_KAFKA_SSL_TRUST_STORE_CERTIFICATES`,
the values are the PEM text; leave the store passwords out.

`SASL_MECHANISM` must match the broker: `SCRAM-SHA-512` or `SCRAM-SHA-256` with `ScramLoginModule`,
`PLAIN` with `org.apache.kafka.common.security.plain.PlainLoginModule`. The `;` at the end of `SASL_JAAS_CONFIG` is required.
`OAUTHBEARER` and `GSSAPI` take the `sasl.oauthbearer.*`, `sasl.login.*` and `sasl.kerberos.*` variables listed in `application.yml`.

SASL_PLAINTEXT with OAUTHBEARER, a token of the client credentials from the token endpoint; Kafka 4 opens only the URLs
listed in `org.apache.kafka.sasl.oauthbearer.allowed.urls`:

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:19096
SPRING_KAFKA_PROPERTIES_SECURITY_PROTOCOL=SASL_PLAINTEXT
SPRING_KAFKA_PROPERTIES_SASL_MECHANISM=OAUTHBEARER
SPRING_KAFKA_PROPERTIES_SASL_JAAS_CONFIG=org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required clientId="mstemplate" clientSecret="secret";
SPRING_KAFKA_PROPERTIES_SASL_LOGIN_CALLBACK_HANDLER_CLASS=org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginCallbackHandler
SPRING_KAFKA_PROPERTIES_SASL_OAUTHBEARER_TOKEN_ENDPOINT_URL=https://idp/oauth/token
JAVA_TOOL_OPTIONS=-Dorg.apache.kafka.sasl.oauthbearer.allowed.urls=https://idp/oauth/token
```

With `SPRING_KAFKA_PROPERTIES_SASL_OAUTHBEARER_JWKS_ENDPOINT_URL=https://idp/oauth/jwks` the client also checks the token
by these keys, then `allowed.urls` takes both URLs separated by a comma.

The SSL stores can be given as one `spring.ssl.bundle` instead of `SPRING_KAFKA_SSL_*_STORE_*`:

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:19094
SPRING_KAFKA_PROPERTIES_SECURITY_PROTOCOL=SSL
SPRING_SSL_BUNDLE_JKS_KAFKA_TRUSTSTORE_LOCATION=file:/certs/ca.p12
SPRING_SSL_BUNDLE_JKS_KAFKA_TRUSTSTORE_PASSWORD=changeit
SPRING_SSL_BUNDLE_JKS_KAFKA_TRUSTSTORE_TYPE=PKCS12
SPRING_SSL_BUNDLE_JKS_KAFKA_KEYSTORE_LOCATION=file:/certs/mstemplate.p12
SPRING_SSL_BUNDLE_JKS_KAFKA_KEYSTORE_PASSWORD=changeit
SPRING_SSL_BUNDLE_JKS_KAFKA_KEYSTORE_TYPE=PKCS12
SPRING_KAFKA_SSL_BUNDLE=kafka
```

#### Kafka broker with a password

A broker (KRaft) with one listener per mode, `server.properties`:

```properties
listeners=CONTROLLER://:29099,PLAINTEXT://:19092,SASLP://:19093,MTLS://:19094,SASLS://:19095
advertised.listeners=PLAINTEXT://kafka:19092,SASLP://kafka:19093,MTLS://kafka:19094,SASLS://kafka:19095
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,SASLP:SASL_PLAINTEXT,MTLS:SSL,SASLS:SASL_SSL
inter.broker.listener.name=PLAINTEXT

# SASL_PLAINTEXT and SASL_SSL: login and password
sasl.enabled.mechanisms=SCRAM-SHA-512
listener.name.saslp.scram-sha-512.sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required;
listener.name.sasls.scram-sha-512.sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required;

# SSL and SASL_SSL: the broker certificate must contain the host name of advertised.listeners
ssl.keystore.location=/certs/broker.p12
ssl.keystore.password=changeit
ssl.keystore.type=PKCS12
# CA of the client certificates, for SSL with client authentication
ssl.truststore.location=/certs/truststore.p12
ssl.truststore.password=changeit
ssl.truststore.type=PKCS12
listener.name.mtls.ssl.client.auth=required
```

The SASL user and its password:

```
# when the storage is formatted
kafka-storage.sh format -t <cluster id> -c server.properties --add-scram 'SCRAM-SHA-512=[name=mstemplate,password=secret]'
# or on a running broker
kafka-configs.sh --bootstrap-server kafka:19092 --alter --entity-type users --entity-name mstemplate \
    --add-config 'SCRAM-SHA-512=[password=secret]'
```

### Redis connection

Redis cache of `xm-commons-cache-redis`, for caches with `strategy: REDIS` in `cache.yml`.
Redis server with a password: `redis-server --requirepass secret`.

Environment variables of the microservice:

```
APPLICATION_TENANT_MEMORY_CACHE_ENABLED=true
APPLICATION_TENANT_CACHE_REDIS_ENABLED=true
APPLICATION_TENANT_CACHE_REDIS_HOST=redis
APPLICATION_TENANT_CACHE_REDIS_PORT=6379
APPLICATION_TENANT_CACHE_REDIS_PASSWORD=secret
APPLICATION_TENANT_CACHE_REDIS_DATABASE=0
```

Redis with an ACL user and TLS, `redis-server --tls-port 6379 --port 0 --tls-cert-file /certs/redis.crt --tls-key-file /certs/redis.key --tls-ca-cert-file /certs/ca.crt --tls-auth-clients no`
and `ACL SETUSER mstemplate on >secret ~* &* +@all`:

```
APPLICATION_TENANT_CACHE_REDIS_USERNAME=mstemplate
APPLICATION_TENANT_CACHE_REDIS_PASSWORD=secret
APPLICATION_TENANT_CACHE_REDIS_SSL=true
```

The CA of the Redis certificate must be trusted by the JVM: `JAVA_TOOL_OPTIONS=-Djavax.net.ssl.trustStore=/certs/ca.p12 -Djavax.net.ssl.trustStorePassword=changeit`.

### Privileges

There are conventions for privilege keys:

- `<ENTITY_NAME>.GET_LIST` - get list of entities
- `<ENTITY_NAME>.GET_LIST.ITEM` - get single entity by ID
- `<ENTITY_NAME>.CREATE` - create entity
- `<ENTITY_NAME>.UPDATE` - update entity
- `<ENTITY_NAME>.PARTIAL_UPDATE` - partial update entity
- `<ENTITY_NAME>.DELETE` - delete entity

NOTE: for filtering lists you need to setup `com.icthh.xm.commons.permission.annotation.FindWithPermission` annotation on Service layer.
It supports criteria based filters and SpEL defined in permission.

### Client binding

HTTP client binding connected using `xm-commons-client-feign` which contains autoconfiguration for
feign clients

To configure HTTP client automated authorization use next application configuration:

```yaml
spring:
  security:
    oauth2:
      client:
        provider:
          uaa:
            token-uri: http://localhost:9999/oauth/token
        registration:
          uaa:
            authorization-grant-type: client_credentials
            client-id: internal
            client-secret: internal
```

Where `token-uri` is a URI to UAA service, `client-id` and `client-secret` are credentials to receive
client token.

After configuration properties are set, use `@AuthorizedFeignClient` annotation on your client feign
interfaces to provide automatic authorization and simple declarative request configuration

### Service & DTO conventions

By design Services should never expose DB Entities outside. All communication with controllers should be using DTOs.
[Mapstruct](https://mapstruct.org/) is advised to use for mapping Entities to DTOs and vice versa.

Typical Service & DTO pattern:

```java
@Override
public ExampleEntityFirstDto save(ExampleEntityFirstDto exampleEntityFirstDto) {
  ExampleEntityFirst exampleEntityFirst = exampleEntityFirstMapper.toEntity(exampleEntityFirstDto);
  exampleEntityFirst = exampleEntityFirstRepository.save(exampleEntityFirst);
  return exampleEntityFirstMapper.toDto(exampleEntityFirst);
}

```

### CI/CD

[//]: # (TODO: define travice/gitlab file)

### Actuator

In `prod` the management endpoints are on port `9080`, `/management/health` is open.
The rest of `/management/**` needs the role of `application.management.access.role` (`SYSTEM-ADMIN` by default),
`TOKEN` below is a token of a user with this role.
`prod,migration` keeps everything on the main port `8081` as before.

#### Healthcheck

[//]: # (TODO: need to find helathckeck for Kafka)

#### Metrics

Metrics are collected in Prometheus format by Micrometer. In `prod` the export is off and Prometheus is closed;
`MANAGEMENT_PROMETHEUS_METRICS_EXPORT_ENABLED=true` turns the export on, `APPLICATION_PROMETHEUS_PUBLIC=true` opens it:

```
curl -H "Authorization: Bearer $TOKEN" http://localhost:9080/management/prometheus
curl http://localhost:9080/management/prometheus
```

#### Log management

`/management/logs` stays on the main port and needs the privilege `LOGS.GET` to read and `LOGS.UPDATE` to change a level,
with `APPLICATION_MANAGEMENT_LOGS_SYSTEM_ADMIN_ONLY=true` only the role of `application.management.access.role`.
Here `TOKEN` is a token of a role with these privileges in `permissions.yml`:

```
curl -H "Authorization: Bearer $TOKEN" http://localhost:8081/management/logs
curl -X PUT -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
    -d '{"name":"com.icthh.xm.ms.mstemplate","level":"DEBUG"}' http://localhost:8081/management/logs
```

#### Introspections

```
curl -H "Authorization: Bearer $TOKEN" http://localhost:9080/management/threaddump
```

### Error handling

Commons `xm-commons-i18n` contains `com.icthh.xm.commons.i18n.error.web.ExceptionTranslator` class which is responsible for exception translation.

## Development

To start your application in the dev profile, run:

```
./gradlew
```

### Start microservice without ms-config

Some microservices can work without ms-config instance.
The only reason they may need config is for getting public key for token verification and tenant list json to resolve tenant.

To start your microservice without config you can use `configMode=FILE` which still points to real config repository
but does not require running ms-config instance.

There is example configuration in application.yaml:

```yaml
xm-config:
  enabled: true
  configMode: FILE
  configDirPath: /path/to/xm-config-repo
```

These settings activate:

- com.icthh.xm.commons.config.client.repository.FileCommonConfigRepository - implementation of file based repo
- com.icthh.xm.commons.security.oauth2.FileVerificationKeyClient - reads certificate from `${configDirPath}/config/public.cer`

**NOTE:**

1. Config files will be updated as you change content in the local repo thanks to `com.icthh.xm.commons.config.client.repository.file.FileUpdateWatcher`
2. WARNING: you need to be aware that file will have **raw unprocessed** content (so environment variables will not be rosolved)

### Doing API-First development using openapi-generator

[OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:

```bash
./gradlew openApiGenerate
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker-compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.

## Building for production

### Manage application timezone

Timezone can be defined as environment variable `TZ` of docker container.
Usually it is configured in compose file as following:

```yaml
version: '3.8'
services:
  mstemplate-app:
    image: mstemplate
    environment:
      - XMX=512M
      - TZ=UTC
```

### Packaging as jar

To build the final jar and optimize the mstemplate application for production, run:

```
./gradlew -Pprod clean bootJar
```

To ensure everything worked, run:

```
java -jar build/libs/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./gradlew -Pprod -Pwar clean bootWar
```

## Testing

To launch your application's tests, run:

```
./gradlew test integrationTest jacocoTestReport
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the gradle plugin.

Then, run a Sonar analysis:

```
./gradlew -Pprod clean check jacocoTestReport sonarqube
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./gradlew bootJar -Pprod jibDockerBuild
```

Then run:

```
docker compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[JHipster Homepage and latest documentation]: https://www.jhipster.tech
[JHipster 8.6.0 archive]: https://www.jhipster.tech/documentation-archive/v8.6.0
[Doing microservices with JHipster]: https://www.jhipster.tech/documentation-archive/v8.6.0/microservices-architecture/
[Using JHipster in development]: https://www.jhipster.tech/documentation-archive/v8.6.0/development/
[Service Discovery and Configuration with Consul]: https://www.jhipster.tech/documentation-archive/v8.6.0/microservices-architecture/#consul
[Using Docker and Docker-Compose]: https://www.jhipster.tech/documentation-archive/v8.6.0/docker-compose
[Using JHipster in production]: https://www.jhipster.tech/documentation-archive/v8.6.0/production/
[Running tests page]: https://www.jhipster.tech/documentation-archive/v8.6.0/running-tests/
[Code quality page]: https://www.jhipster.tech/documentation-archive/v8.6.0/code-quality/
[Setting up Continuous Integration]: https://www.jhipster.tech/documentation-archive/v8.6.0/setting-up-ci/
[Node.js]: https://nodejs.org/
[NPM]: https://www.npmjs.com/
[openapi-generator]: https://openapi-generator.tech
[swagger-editor]: https://editor.swagger.io
[doing api-first development]: https://www.jhipster.tech/doing-api-first-development/

## Migrate existing ms-template based microservices to new Lep Engine

1 - Add configuration

```java
@Configuration
public class LepConfiguration extends GroovyLepEngineConfiguration {

  public LepConfiguration(@Value("${spring.application.name}") String appName) {
    super(appName);
  }
}

```

2 - Make migration by
[migration guide](https://github.com/xm-online/xm-commons/blob/master/xm-commons-lep/MIGRATION_GUIDE.MD)
