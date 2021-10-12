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

A simple project starter for Spring Boot based CRUD applications. Provides:
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
* [Liquibase](https://www.liquibase.org/)
* [MapStruct](https://mapstruct.org/)
* [TestContainers](https://www.testcontainers.org/)
* [TestContainers Spring Boot](https://github.com/Playtika/testcontainers-spring-boot)
* [Project Lombok](https://projectlombok.org/)




<!-- GETTING STARTED -->
## Getting Started

This is a simple starter project for spring based CRUD applications that integrates several useful libraries.

### Prerequisites

* Java 1.8 or greater
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

There are 2 maven profiles:
* `postgres` (default)
    * jdbc url: `jdbc:postgresql://localhost:54320/spring_boot_demo_db`
    * user: `postgres`
    * password: `postgres`
* `SQL Server`
    * jdbc url: `jdbc:sqlserver://localhost:14330;databaseName=master`
    * user: `sa`
    * password: `Pass@word`

1. Start a database configured for chosen maven profile.

   You can use docker-compose images in `spring-boot-playground-demo/src/main/resources/db/docker/`.

   Change to the desired DB directory (e.g. `db/docker/pgsql`) and start with `docker-compose up -d`

   Stop with `docker-compose down`

3. Run demo application 
```
cd spring-boot-playground/spring-boot-playground-demo/target
java -jar spring-boot-playground-demo-0.1.1.jar
```
3. Browse springdoc documentation `http://localhost:8080/swagger-ui.html`
4. Use `Postman` collection in `src/test/resources/postman/spring boot demo.postman_collection.json` to call REST endpoints

## Experimental Kubernetes support (minikube)

1. Build Docker image with

```
eval $(minikube docker-env)
mvn -Pdev-pgsql-k8s clean package docker:build -Dmaven.test.skip=true
```

2. Apply Kubernetes resources

```
cd spring-boot-playground-demo/src/main/resources/k8s/
kubectl apply -f setup/namespace.yaml
kubectl apply -f postgres/postgres.yaml  --namespace=spring-boot-demo-dev
kubectl apply -f app/spring-boot-demo.yaml  --namespace=spring-boot-demo-dev
```

3. Start minikube tunnel (requires root)
```
minikube tunnel
```

4. Get service list

```
kubectl get svc
NAME                  TYPE           CLUSTER-IP       EXTERNAL-IP      PORT(S)           AGE
postgres              LoadBalancer   10.110.228.206   10.110.228.206   54321:30918/TCP   4h56m
spring-boot-demo-lb   LoadBalancer   10.102.131.119   10.102.131.119   8088:30291/TCP    18m
```

5. Check web endpoint with IP from previous command output
```
wget http://10.102.131.119:8088/api/demo/school
```


<!-- ROADMAP -->
## Roadmap

* code clenaup
* further integrations (eg: [fix jpa n+1 problem](Spring-Data-Jpa-ManyToOne-n-plus-1-problem-solution))
* support more DB
* security support
* Kubernetes integration
* CI/CD pipelines
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
