# jdevmin-web

Instructions on how to run:

Eventually this project will be hosted on my personal website which can be found at 'www.jdevmin.tech'.

Until then, 

First you'll have to create the database.

$ create database jdevminweb


then edit the application.properties file to suit your database.

spring.datasource.username={your username}
spring.datasource.password={your password}

and the 'META-INF/persistence.xml' file also needs to be updated

property name="hibernate.connection.user" value="{yourUsername}"
property name="hibernate.connection.password" value="{yourPassword}"

then these commands will run the application:

$ mvn clean install
$ mvn spring-boot:run

you can access the login page by going to http://localhost:8080/

create an account by clicking [Create A New User] button from the landing page.

You can access other accounts by going to /friends/find

Add a friend by finding their account from the /friends/find endpoint.

They will receive a friend request in their inbox.

That user can now visit their alert inbox at /user/inbox/alerts.

The alert will show up on their homepage and they can follow the link provided to see their alert inbox.

Clicking accept on the alert from the alert inbox will connect the users profiles.

Users can send eachother messages by accessing eachothers profiles at '/user/profile/user?user=${user}'

User profiles have a link to send a message to their message inbox.

The functionality of accessing the inbox isn't there yet.

'/groups' endpoint brings up a list of all groups.

Eventually there will be functions to join groups and make posts in them.

There will be group moderators and they will be able to ban members from groups, and have other related administrative powers.