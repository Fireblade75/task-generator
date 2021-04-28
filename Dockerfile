FROM open-liberty:full-java11-openj9

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

COPY --chown=1001:0 task-generator-ws/src/main/liberty/config/ /config/
COPY --chown=1001:0 task-generator-ws/target/*.war /config/apps/
COPY --chown=1001:0 task-generator-ws/target/taskgenerator/WEB-INF/lib/mysql-connector-java-8.0.23.jar /config/jdbc

RUN configure.sh