
# web socket auction

This project provides the core of a working auction that uses websocket messages.
The model has been extended to include extra auction objects such as lot, auction, bid and message.
However the data is all mocked through mockdao's which impliment minimal in memory functionality.

The AuctionService allows parties to register for an auction and received back and auth key which they can use when they are bidding.
Auctions go through three states Eastleigh planning scheduled running and finished..





## Web Socket Events

This simple project is built from an example here

https://examples.javacodegeeks.com/enterprise-java/tomcat/apache-tomcat-websocket-tutorial/

It uses Tomcat 7's internal JSR-356 Java WebSocket 1.1 implementation

https://tomcat.apache.org/tomcat-7.0-doc/web-socket-howto.html   
