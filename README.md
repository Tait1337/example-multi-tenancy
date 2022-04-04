# example-multi-tenancy
[![build status](https://github.com/Tait1337/example-multi-tenancy/workflows/build/badge.svg)](https://github.com/Tait1337/example-multi-tenancy/actions)
[![quality gate status](https://sonarcloud.io/api/project_badges/measure?project=Tait1337_example-multi-tenancy&metric=alert_status)](https://sonarcloud.io/dashboard?id=Tait1337_example-multi-tenancy)
[![license](https://img.shields.io/github/license/Tait1337/example-multi-tenancy)](LICENSE)

Spring Boot based Multitenancy Webapp.
This example shows how to use Spring Boot to create a web application that
- separate tenants into different database schemas that can use flyway migration scripts
- uses OAuth to grant user access to their tenants

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Install Java JDK 17 or higher.
```
https://openjdk.java.net/install/index.html
```

### Installing

Clone the Repository.
```
git clone https://github.com/tait1337/example-multi-tenancy.git
```

Start the Database.
```
docker run -d -p 5432:5432 --name example-multi-tenancy-postgres -e POSTGRES_PASSWORD=secret postgres:alpine
```

Start and configure the OAuth Server (takes some minutes).
```
docker run -d -p 8080:8080 --name example-multi-tenancy-keycloak -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=secret -e KEYCLOAK_IMPORT=/tmp/example-realm-tenant1.json,/tmp/example-realm-tenant2.json -v $PWD/realms:/tmp jboss/keycloak
Open http://localhost:8080/auth/admin/ and login with admin/secret
Create a new user on both tenants:
username: user1, email: user1@example.com, firstname: John, lastname: Doe, password: secret
username: user1, email: user1@example.de, firstname: Jane, lastname: Doe, password: secret
```

Run the Web Application.
```
mvn spring-boot:run
```

### Configuration

Within [application.properties](src/main/resources/application.properties) you can modify settings.

## Running the tests

```
When you start the application flyway micration scripts will run for every tenant.
You will find two schemas in the db: example-multi-tenancy_tenant1 and example-multi-tenancy_tenant2.
Add some data:
INSERT INTO "example-multi-tenancy_tenant1".data(id, value) VALUES ('6dce8ea3-7634-4594-a89e-1301ff9cfe38', 'tenant 1 data');
INSERT INTO "example-multi-tenancy_tenant2".data(id, value) VALUES ('e19b6faa-687d-4a4f-8b98-53ff699c42b7', 'tenant 2 data');

Open http://localhost:8080/auth/realms/tenant1/account and login with user1/secret
Copy the bearer token
Make a request to http://localhost:8081/protected/tenant using the bearer token
Make a request to http://localhost:8081/protected/data using the bearer token

Open http://localhost:8080/auth/realms/tenant2/account and login with user1/secret
Copy the bearer token
Make a request to http://localhost:8081/protected/tenant using the bearer token
Make a request to http://localhost:8081/protected/data using the bearer token
```

## Deployment

The most basic option to run the Application is by building the Dockerimage.

```
docker build -t example-multi-tenancy:1.0.0-SNAPSHOT .
docker run -p 8080:8080 -d example-multi-tenancy:1.0.0-SNAPSHOT
```

## Contributing

I encourage all the developers out there to contribute to the repository and help me to update or expand it.

To contribute just create an issue together with the pull request that contains your features or fixes.

## Versioning

We use [GitHub](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/tait1337/example-multi-tenancy/tags).

## Authors

* **Oliver Tribess** - *Initial work* - [tait1337](https://github.com/tait1337)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* [Adoptium](https://adoptium.net/) Team for providing a free JDK
* [Spring](https://spring.io/) Team for providing the Service creation platform to build stand-alone web apps
* [Flyway](https://flywaydb.org/) Team for providing a tool for database versioning
* [Postgresql](https://www.postgresql.org/) Team for providing an open source licensed relational database
