#grofers-lucky-draw-game

## Requirements
#### - Java8
#### - MySql Server

###1. Clone the project from github using the following command
#####git clone https://github.com/akarsh2609/grofers-contest.git
###2. you can import the project in IntelliJ, Eclipse, NetBeans as a maven project
###3. Import src/main/java/resources/data.sql into MySQL database

###4. Update database credential and other configuration into application.properties available in src/main/java/resources

###5. to run the application add configuration as Application and provide the path of Application class in main class and select JRE as java8

###6. Once Sprint Boot Application will be started successfully then we can call following Endpoints by using POSTMAN

##### set content type as in header as `application/json`

##### set request body as raw with JSON payload

### The Restful API endpoints are:
#### Endpoint | HTTP Method | CRUD Method | Result

###### `gamingservice/loginSignup` | POST | Create | Create a single user
######`gamingservice/createTicket`| POST | Create | Create a new Ticket for a user
######`gamingservice/takePart` | POST | Create | Map the user ticket to a contest
######`gamingservice/upcomingContest` | GET | Show | Show the upcoming contests for a week
######`gamingservice/lastWeekWinners` | GET | Show | Show all the contest winners in last 1 week
######`gamingservice/findWinner?contest=[CONTEST_NAME]` | GET | Show | Finds the winner of a single contest

### cURL calls

####1. To Create a user :
##### This Creates a user and returns its userId, user can register with a phone number only once
```
   http://localhost:8080/gamingservice/loginSignup

Payload
{
"name": "akarsh singhal",
"mobileNo": 8077216889
}

OUTPUT
{
    "id": "2c62f0fa-1c89-4344-af7d-5d7d0e32fe2e",
    "name": "akarsh singhal",
    "mobileNo": 8077216889
}
```
####2. To create a ticket:
#####this API creates a ticket for a user, a single user can buy multiple tickets
```
    http://localhost:8080/gamingservice/createTicket
    
Payload
{
    "id": "2c62f0fa-1c89-4344-af7d-5d7d0e32fe2e"
}

Output
{
    "message": "Ticket has been successfully created with Id = 0389353b-47a3-43ea-a1ea-2c3fc175f637",
    "error": false
}
```

####3. To take Part in a Contest
##### This API helps to link a ticket to a particular contest, a user can register to a contest only once
```
    http://localhost:8080/gamingservice/takePart

Payload
{
    "id": "96389173-d3e8-47a5-b318-cfbe3fd81600",
    "contestName": "Contest-15/May",
    "ticketId": "9e9683c4-fcce-4ae3-bd7a-ddca47eaeb79"
}

Output
{
    "message": "Successfully taken part in the Contest-15/May",
    "error": false
}
```
####4. to view the upcoming contests for a week
##### This API helps in viewing the upcoming contests Ending date and their prize
```
    http://localhost:8080/gamingservice/upcomingContest
    
Output
{
    "2021-05-15T10:21:00.000+0000": "laptop",
    "2021-05-20T18:30:00.000+0000": "Iphone",
    "2021-05-19T18:30:00.000+0000": "AC"
}

```

####5. To view the winners of the last week contests
##### This API gets the past week contest and their winners
```
    http://localhost:8080/gamingservice/lastWeekWinners

Output
{
    "Contest-15/May": "akarsh singhal",
    "Contest-16/May": "harshit singhal"
}
```

####6. To find the winner of a particular contest
##### This API finds the winner of a contest passed as query param
```
    http://localhost:8080/gamingservice/findWinner?contest=Contest-15/May

Output
{
    "message": "akarsh singhal",
    "error": false
}
```

##Database
###There are three tables for this project:
#####1. User table which store all the user data like name, userId, and phone number.
#####2. Tickets table which store the userId, ContestName, TicketId.
#####3. Contest table which store the contest name, start-time, end-time, winner and prize.
