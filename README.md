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

    

5. In each application directory (api-gw, service-a, service-b) run the spring-boot:run command


    cd ./api-gw
    ./mvnw spring-boot:run
    
    cd ../service-a
    ./mvnw spring-boot:run
    
    cd ../service-b
    ./mvnw spring-boot:run



**APPLICATION USAGE**

Below you can see the details on how the testings can be performed.
1. Recommended: Use Postman application (https://www.postman.com/) to import the request collection that was developed as part of the project. The Postman collection can be found in the following location in the project (kadomos/Kadomos.postman_collection.json). After making sure all servers are run import the Postman collection and sent requests to the api's.
2. You can also try using the below url samples to execute api's on your convenient browser.
   

    Parameters: 
    accountName - ACCOUNT_A || ACCOUNT_B
    amount - 56.7
    
    Balance using Passthrough - http://localhost:8080/savings/balance?accountName=ACCOUNT_A
    Increase balance using Passthrough - http://localhost:8080/savings/increase?accountName=ACCOUNT_A&amount=17.6
    Decrease balance using Passthrough - http://localhost:8080/savings/decrease?accountName=ACCOUNT_A&amount=17.6
    
    
    Parameters:
    accountName - ACCOUNT_A || ACCOUNT_B
    description - Description for the transaction
    amount - 56.7
    type = DEBIT || CREDIT
    
    Balance using redirect - http://localhost:8080/gateway/balance?accountName=ACCOUNT_A
    Transaction using redirect - http://localhost:8080/gateway/transaction?accountName=ACCOUNT_A&description=Description&amount=26.6&type=CREDIT




