FROM sbtscala/scala-sbt:eclipse-temurin-17.0.4_1.7.1_3.2.0 AS builder

WORKDIR /build
COPY ../ /build/
RUN mkdir /app
RUN sbt update Universal/packageZipTarball
RUN tar xzf server/target/universal/starter.tgz -C /app

FROM eclipse-temurin:17-jdk
ENV TZ="UTC"
RUN apt-get update && apt-get install -y \
    less iputils-ping dnsutils procps \
    && apt-get clean
COPY --from=builder /app /opt

RUN useradd -s /bin/bash starter
COPY --chmod=755 ./docker/startup.sh /
ENTRYPOINT ["./startup.sh"]