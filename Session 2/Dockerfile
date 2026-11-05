FROM tomcat:11.0-jdk17-temurin AS build
WORKDIR /build
COPY murach/ ./murach/
RUN mkdir -p classes && javac --release 17 -encoding UTF-8 \
    -cp /usr/local/tomcat/lib/servlet-api.jar -d classes \
    murach/business/User.java murach/email/EmailListServlet.java

FROM tomcat:11.0-jdk17-temurin
COPY --from=build /build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/
COPY WEB-INF/web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF/web.xml
COPY index.html thanks.jsp /usr/local/tomcat/webapps/ROOT/
COPY styles/ /usr/local/tomcat/webapps/ROOT/styles/
EXPOSE 8080
CMD ["catalina.sh", "run"]
