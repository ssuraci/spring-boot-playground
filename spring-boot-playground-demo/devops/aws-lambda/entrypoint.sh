#!/usr/bin/env sh

HANDLER="$1"

if [ -z "${AWS_LAMBDA_RUNTIME_API}" ]; then
    exec /usr/bin/aws-lambda-rie /opt/java/openjdk/bin/java -cp "$JAR_DIR/spring-boot-playground-demo-0.1.1.jar" "com.amazonaws.services.lambda.runtime.api.client.AWSLambda" "$HANDLER"
else
    exec /opt/java/openjdk/bin/java -cp "$JAR_DIR/spring-boot-playground-demo-0.1.1.jar" "com.amazonaws.services.lambda.runtime.api.client.AWSLambda" "$HANDLER"
fi