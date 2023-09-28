<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
***
***
***
*** To avoid retyping too much info. Do a search and replace for the following:
*** ssuraci, spring-boot-playground, twitter_handle, email, project_title, project_description
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">

  <h3 align="center">Spring Boot Playground</h3>

  <p align="center">
    A simple project starter for Spring Boot based applications
    <br />
    <a href="https://github.com/ssuraci/spring-boot-playground"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/ssuraci/spring-boot-playground/issues">Report Bug</a>
    <a href="https://github.com/ssuraci/spring-boot-playground/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#experimental-kubernetes-support-minikube">Experimental Kubernetes support</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

A simple project starter for Spring Boot 3 based CRUD applications. Provides:
* abstract classes with basic endpoints for CRUD operations
* out-of-the-box filtering, pagination, sorting for select operations
* validation for insert / update
* integration with many support libraries
* testcontainers support for integration testing
* demo application

### Built With

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data](https://spring.io/projects/spring-data)
* [Hibernate ORM](https://hibernate.org/orm/)
* [Hibernate Validator](http://hibernate.org/validator/)
* [Spring Data JPA Entity Graph](https://github.com/Cosium/spring-data-jpa-entity-graph)
* [SpringDoc](https://springdoc.org/)
* [FlywayDB](https://flywaydb.org/)
* [MapStruct](https://mapstruct.org/)
* [TestContainers](https://www.testcontainers.org/)
* [Mockito](https://site.mockito.org/)
* [H2](https://www.h2database.com/)
* [Project Lombok](https://projectlombok.org/)




<!-- GETTING STARTED -->
## Getting Started

This is a simple starter project for spring based CRUD applications that integrates several useful libraries.

### Prerequisites

* Java 17 or greater
* Maven 3
* Docker 20.10

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/ssuraci/spring-boot-playground.git
   ```
2. Build library and application
   ```sh
   mvn install
   ```



<!-- USAGE EXAMPLES -->
## Usage

There are 3 maven profiles:
* `dev-pgsql` (default, pgsql local development)
    * jdbc url: `jdbc:postgresql://localhost:54321/spring_boot_demo_playground_db`
    * user: `postgres`
    * password: `postgres`
* `dev-mssql` (mssql local development)
    * jdbc url: `jdbc:sqlserver://localhost:14330;databaseName=master`
    * user: `sa`
    * password: `Pass@word`
* `docker` (multi db profile for building docker image)

For local development, use one either `dev-pgsql` or `dev-mssql` profile. Start the corresponding database with docker compose:

   ```
   cd spring-boot-playground-demo/devops/docker-compose`
   ```

   Use: 
   - `docker-compose up spring_boot_playground_pgsql_db` to start postgres
   - `docker-compose up spring_boot_playground_mssql_db` to start SQL Server

## Run dockerized application with docker-compose

1. Build application docker image

```
mvn clean package
cd spring-boot-playground-demo
mvn -Pdocker spring-boot:build-image
```

2. Start application and database with docker-compose.

   ```
   cd spring-boot-playground-demo/devops/docker-compose`
   ```

   Use: 
   - `docker-compose --profile=pgsql up` to start app with postgres
   - `docker-compose --profile=mssql up` to start app with SQL Server

   Stop with `docker-compose down`
<!--
3. Browse springdoc documentation `http://localhost:8080/swagger-ui.html`
-->
3. Use `Postman` collection in `src/test/resources/postman/spring boot demo.postman_collection.json` to call REST endpoints
<!--
## Deploy to Kubernetes (minikube)

1. Minikube prerequisites

Enable `ingress` controller in minikube:

```
minikube addons enable ingress
```

It is also useful to enable dashboard to have an overview of cluster status:

```
minikube enable dashboard
```

2. Build application docker image

```
eval $(minikube docker-env)
mvn package -Pdocker jib:dockerBuild -Dmaven.test.skip=true
```

3. You can deploy application to k8s either with `kubectl` or with `helm` (version 3):

4. Apply Kubernetes resources with `kubectl`:

```
cd spring-boot-playground-demo/devops/k8s/kubectl
kubectl apply -f namespace-dev.yaml
kubectl apply -f ingress.yaml  --namespace=playground-dev
kubectl apply -f configmap-dev.yaml  --namespace=playground-dev
kubectl apply -f postgres.yaml  --namespace=playground-dev
kubectl apply -f spring-boot-playground-demo.yaml  --namespace=playground-dev
```

5. Or, alternatevily, install application with `helm` (if not already installed, install `helm` as described [here](https://helm.sh/docs/intro/install/)
):

```
cd spring-boot-playground-demo/devops/k8s/helm/spring-boot-playground-demo
helm dependency update .
helm install playground-demo  . --namespace playground-dev --create-namespace
```

To uninstall:
```
helm delete playground-demo --namespace playground-dev
```


6. Get minikube ip
```
minikube ip
```

7. Edit hosts file

Add the following line in hosts file adding minikube ip mapped to `playground-demo.local`. For example if minikube ip is `192.168.49.2`, add:

```
192.168.49.2 playground-demo.local
```


8. Check web endpoint with IP from previous command output
```
wget http://playground-demo.local/api/demo/school
```
-->

<!-- ROADMAP -->
## Roadmap

* code clenaup
* security support
* CI/CD pipelines
* Skaffold support
* ...

See the [open issues](https://github.com/ssuraci/spring-boot-playground/issues) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Project Link: [https://github.com/ssuraci/spring-boot-playground](https://github.com/ssuraci/spring-boot-playground)




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/ssuraci/spring-boot-playground.svg?style=for-the-badge
[contributors-url]: https://github.com/ssuraci/spring-boot-playground/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/ssuraci/spring-boot-playground.svg?style=for-the-badge
[forks-url]: https://github.com/ssuraci/spring-boot-playground/network/members
[stars-shield]: https://img.shields.io/github/stars/ssuraci/spring-boot-playground.svg?style=for-the-badge
[stars-url]: https://github.com/ssuraci/spring-boot-playground/stargazers
[issues-shield]: https://img.shields.io/github/issues/ssuraci/spring-boot-playground.svg?style=for-the-badge
[issues-url]: https://github.com/ssuraci/spring-boot-playground/issues
[license-shield]: https://img.shields.io/github/license/ssuraci/spring-boot-playground.svg?style=for-the-badge
[license-url]: https://github.com/ssuraci/spring-boot-playground/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/sebastianosuraci/
