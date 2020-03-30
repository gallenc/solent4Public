
# web socket auction

This project provides the core of a working auction that uses websocket messages.
The model has been extended to include extra auction objects such as lot, auction, bid and message.
However the data is all mocked through mockdao's which impliment minimal in memory functionality.

The AuctionService allows parties to register for an auction and received back an authkey which they must use when they are bidding.
Auctions go through the states planning scheduled running and finished..
When an auction is running users can no longer register for the auction or add new lots.

The auction service has a scheduler which iterates through the auction schedule starting the auction for each lot until all the lots in an auction have finished.
Messages are broadcast to all registered users every time an auction or a lot changes state or there is a new highest bid.

A websocket service is provided which allows users to listen for the start of bidding for a lot and and receive notifications each time the price changes.
Users can bid for lots but they must include their auction registered authkey in the message in order for their bid to be accepted


### Web Socket Events
Two simple web socket examples are also provided as described below.

This simple project is built from an example here

https://examples.javacodegeeks.com/enterprise-java/tomcat/apache-tomcat-websocket-tutorial/

It uses Tomcat 7's internal JSR-356 Java WebSocket 1.1 implementation

https://tomcat.apache.org/tomcat-7.0-doc/web-socket-howto.html   
