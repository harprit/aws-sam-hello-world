# Hello World AWS SAM with Java

This is a Hello World example that can also be used for template as you begin with AWS SAM on Java.

* Shows simplest possible integration of an AWS Lambda function with API Gateway.
* For a URL of the type /hello/{variable}, the Lambda function returns a response 'hello world' where variable = 'world'.

### Requirements

AWS SAM Specific -
* AWS CLI already configured with Administrator permission
* [Docker installed](https://www.docker.com/community-edition)
* Easiest way is to follow [AWS SAM Quick Start]((https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-quick-start.html) to make sure everything has been setup properly

Java Specific -
* [Java SE Development Kit 8 installed](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/install.html)

### Installing dependencies

Use `maven` to install the dependencies and package your application into a JAR file:

```bash
mvn package
```
### Local development

**Invoking function locally through local API Gateway**

```bash
sam local start-api
```

If the previous command ran successfully you should now be able to hit the following local endpoint to invoke your function `http://localhost:3000/hello`

**SAM CLI** is used to emulate both Lambda and API Gateway locally and uses our `template.yaml` to understand how to bootstrap this environment (runtime, where the source code is, etc.) - The following excerpt is what the CLI will read in order to initialize an API and its routes:

```yaml
...
Events:
    HelloWorld:
        Type: Api # More info about API Event Source: https://github.com/awslabs/serverless-application-model/blob/master/versions/2016-10-31.md#api
        Properties:
            Path: /hello
            Method: get
```

## Packaging and deployment

AWS Lambda Java runtime accepts either a zip file or a standalone JAR file - We use the latter in this example. SAM will use `CodeUri` property to know where to look up for both application and dependencies:

```yaml
...
    HelloWorldFunction:
        Type: AWS::Serverless::Function
        Properties:
            CodeUri: target/HelloWorld-1.0.jar
            Handler: helloworld.App::handleRequest
```

Firstly, we need a `S3 bucket` where we can upload our Lambda functions packaged as ZIP before we deploy anything - If you don't have a S3 bucket to store code artifacts then this is a good time to create one:

```bash
aws s3 mb s3://BUCKET_NAME
```

Next, run the following command to package our Lambda function to S3:

```bash
sam package \
    --template-file template.yaml \
    --output-template-file packaged.yaml \
    --s3-bucket REPLACE_THIS_WITH_YOUR_S3_BUCKET_NAME
```

Next, the following command will create a Cloudformation Stack and deploy your SAM resources.

```bash
sam deploy \
    --template-file packaged.yaml \
    --stack-name sam-app \
    --capabilities CAPABILITY_IAM
```