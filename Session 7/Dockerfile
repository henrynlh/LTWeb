FROM tomcat:11.0-jdk17-temurin AS build
WORKDIR /build
COPY murach/ ./murach/
RUN mkdir -p classes && javac --release 17 -encoding UTF-8 \
    -cp /usr/local/tomcat/lib/servlet-api.jar -d classes \
    murach/business/Product.java murach/business/LineItem.java \
    murach/business/Cart.java murach/data/ProductDB.java \
    murach/cart/CartServlet.java

FROM tomcat:11.0-jdk17-temurin
COPY --from=build /build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/
COPY WEB-INF/web.xml WEB-INF/cart.jsp WEB-INF/checkout.jsp /usr/local/tomcat/webapps/ROOT/WEB-INF/
COPY WEB-INF/lib/ /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/
COPY index.html /usr/local/tomcat/webapps/ROOT/
COPY styles/ /usr/local/tomcat/webapps/ROOT/styles/
EXPOSE 8080
CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-8080}\\\"/\" /usr/local/tomcat/conf/server.xml && exec catalina.sh run"]
