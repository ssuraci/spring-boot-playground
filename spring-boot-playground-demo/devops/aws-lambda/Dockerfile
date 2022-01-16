# We use a Java 12 image, but any image could serve as a base image.
FROM eclipse-temurin:11.0.13_8-jre-alpine

# Add the lambda-runtime-interface-emulator to enable local testing.
ADD https://github.com/aws/aws-lambda-runtime-interface-emulator/releases/latest/download/aws-lambda-rie /usr/bin/aws-lambda-rie
RUN chmod +x /usr/bin/aws-lambda-rie

# Add the entrypoint script.
ADD ./devops/aws-lambda/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]

# Add the JAR to a known path.
ENV JAR_DIR="/jar"
ADD ./target/spring-boot-playground-demo-0.1.1.jar $JAR_DIR/

# Set our
CMD ["it.sebastianosuraci.springboot.demo.StreamLambdaHandler::handleRequest"]

# mvn -Paws-lambda clean package -Dmaven.test.skip
#docker build . -f ./devops/aws-lambda/Dockerfile -t ssuraci/playground-lambda:latest
#curl -XPOST "http://localhost:8080/2015-03-31/functions/function/invocations" -d "$(cat ./devops/aws-lambda/test_payload.json)"
#docker run --network="host" -p 9000:8080 --env-file ./devops/aws-lambda/.env --rm --name lambdacontainers ssuraci/playground-lambda:latest
#aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 563630532359.dkr.ecr.eu-central-1.amazonaws.com
#docker tag ssuraci/playground-lambda:latest 563630532359.dkr.ecr.eu-central-1.amazonaws.com/ssuraci/playground-lambda:latest
#docker push 563630532359.dkr.ecr.eu-central-1.amazonaws.com/ssuraci/playground-lambda:latest

