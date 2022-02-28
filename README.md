# kadomos
A case study with Kadomos team to implement Kadomos services allowing user to manage two savings accounts. The work in the case study includes development of tools for the users to manage savings account A and B through a rest api Gateway. 

The work includes three server applications which operate independently through exposed api's over the HTTP. The api's were implemented considering the communication between user and saving account applications are performed through a Gatway. The Gateway operates as a medium communication between user and saving accounts as a Passthrough.

On the other hand, there is also a Redirect version of the implementation which deos redirect the user to a proper account server based on the user requests.

**APPLICATION INSTALLATION**
1. Install the postgres database server and run the SQL script provided as part with the application repo (kadomos/postgres/kadomos). This will create a database that would be used by the saving account applications.
2. Configure the application properties of the Service A and B by providing the Postgress server credentials (service-a/src/main/resources/application.properties and service-b/src/main/resources/application.properties) 
3. Install and configure Maven (https://maven.apache.org/install.html)
4. In each application directory (api-gw, service-a, service-b) run the mvn clean package command or run the script in the project directory (kadomos/mvn-clean-package-all.sh)
    

    cd ./api-gw

    ./mvnw clean package
    
    cd ../service-a

    ./mvnw clean package
    
    cd ../service-b

    ./mvnw clean package

    

5. In each application directory (api-gw, service-a, service-b) run the spring-boot:run command on a separate terminal


    cd ./api-gw

    ./mvnw spring-boot:run
    
    cd ../service-a

    ./mvnw spring-boot:run
    
    cd ../service-b

    ./mvnw spring-boot:run



**APPLICATION USAGE**

**Authentication**

As part of the case study a authentication is implemented to enhance the security of the services, which can be enabled and disabled through the properties configuration (kadomos/api-gw/src/main/resources/application.properties) by setting the kadomos.authentication.enabled to true (default enabled)

The authentication is based on the session id generation per valis username and password supliment, the authentication api does generate a unique identifier representing the session and sends back to the client through the API


**POST** request

Parameters:

username: root
password: kadomos

http://localhost:8080/authentication/authenticate


This will return a json response including the authenticated sessionId

        {
            "success": true,
            "error": "",
            "sessionId": "8cf7f8c4-bfae-46e2-b22c-cf56a142681c"
        }


if you are using the Postman, it will automatically add the session Id to the request urls no need to manually add it


Authentication is run with a **POST** request


Otherwise if the api is run manually using a browser then the session id would need to be added to the get requesturl like you can see in below examples just replace the {{sessionId}} with the api returned sessionID
 


Below you can see the details on how the testings can be performed.
1. Recommended: Use Postman application (https://www.postman.com/) to import the request collection that was developed as part of the project. The Postman collection can be found in the following location in the project (kadomos/Kadomos.postman_collection.json). After making sure all servers are run import the Postman collection and sent requests to the api's.
2. You can also try using the below url samples to execute api's on your convenient browser.
   

    Check the services are running use the following:

    Welcome API GateWay - http://localhost:8080

    Welcome Account A - http://localhost:8081

    Welcome Account B - http://localhost:8082


    Parameters: 

    accountName - ACCOUNT_A || ACCOUNT_B

    amount - 56.7

    
    Balance using Passthrough 
    
    http://localhost:8080/savings/balance?accountName=ACCOUNT_A&sessionId={{sessionId}} 
    
    http://localhost:8080/savings/balance?accountName=ACCOUNT_B&sessionId={{sessionId}}

    Increase balance using Passthrough 
    
    http://localhost:8080/savings/increase?accountName=ACCOUNT_A&amount=17.6&sessionId={{sessionId}}
    
    http://localhost:8080/savings/increase?accountName=ACCOUNT_B&amount=17.6&sessionId={{sessionId}}

    Decrease balance using Passthrough 
    
    http://localhost:8080/savings/decrease?accountName=ACCOUNT_A&amount=17.6&sessionId={{sessionId}}
    
    http://localhost:8080/savings/decrease?accountName=ACCOUNT_B&amount=17.6&sessionId={{sessionId}}

    
    
    Parameters:

    accountName - ACCOUNT_A || ACCOUNT_B

    description - Description for the transaction

    amount - 56.7

    type = DEBIT || CREDIT

    
    Balance using redirect
    
    http://localhost:8080/gateway/balance?accountName=ACCOUNT_A
    
    http://localhost:8080/gateway/balance?accountName=ACCOUNT_B

    Transaction using redirect
    
    http://localhost:8080/gateway/transaction?accountName=ACCOUNT_A&description=Description&amount=26.6&type=CREDIT

    http://localhost:8080/gateway/transaction?accountName=ACCOUNT_B&description=Description&amount=26.6&type=CREDIT





