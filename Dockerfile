# FROM 824214588663.dkr.ecr.ap-south-1.amazonaws.com/build/amazoncorretto:8-alpine
FROM 824214588663.dkr.ecr.ap-south-1.amazonaws.com/build/amazoncorretto:11-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY target/magnum-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java","-jar","purchaseorder-0.0.1-SNAPSHOT.jar"]