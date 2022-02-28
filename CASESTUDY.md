**Scaling API gateway**

Horizontal scaling of an application let's increase the Thoughoutput, Latency and Availability of the system, which ensures the critical to importance busness needs are met. In the typical case, the Api Gateway let's users manage the account which holds critically important financial securityies. 


As we observed the case study details and implementation it is obvious the current Api Gateway is based on single server/node machinery as we can see below. 
1. 
           Api Gateway
            /        \
    Account A   |   Account B


This structure is fine, until the business meets a new requirments to enhance the api accessability, particularly a typical business requirment would be to consider 1 million active user transactions per minute, in this case the single node structure would fail because of the over load.

For this reason most commonly Load Balancer is used to distribute the work of the system. In some othercasesarchitectures include several layers of load balancers, and the number of layers can be increased to support desired level of Latency and Throughoutput, a sample of such architecture can be seen below.

2. 
                                       
                                                                         Load Balancer
                                                          
                                     /                                          |                                     \
                                                           
                          Load Balancer                                    Load Balancer                          Load Balancer  ......
                          
                         /             \       ...                        /              \     ...               /              \
                          
              Api Gateway                   Api Gateway                 Api Gateway
              
               /        \                   /        \                 /           \
               
       Account A   |   Account B      Account A   |   Account B     Account A   |   Account B                    ......
    
    
On the 1. Structure of architecture we can see a single instance of Api Gatway redirecting requests to Account A and Account B which limits the precessing power to the machinery underlying these 3 servers, On the other hand the 2. structure is more robust and employes many number of nodes serving the requests which would necessarily improve the latency and throughput. The top load balancer first accepts the request and basedon some load statistics dispatches to the second layer of load balancers which in turn dispatch to the proepr Api Gatway in hand based on some otherstatistics or conditions. This ensures the requests are equally distributed through the distributed system hence balancing the load.
    
But this raises other concerns, are we all set, and the answer is kind of what did you expect, not really, if we assume the Account A nodes are servers which function on the same data, then we face concurrency and racingconditions. If multiple requests try executing a Debit transaction simultainusly in the below order, this may cause the balance calculate arithmetic errors.

        Request 1: Get Balance - 75 euro
        Request 2: Get Balance - 75 euro
        Request 3: Get Balance - 75 euro
        Request 1: Debit transaction- 45 euro
        Request 2: Debit transaction- 45 euro
        Request 3: Debit transaction- 45 euro
        
In the above example we can assum 3 requests are performed simultainusly and all request threads reach the point of Get Balance which returns 75 euro, and then they all start making Debit transaction which executes successfully leading wrong result.


To avoid this scenarious the data modification of the account nodes must be done in a synchronious way to avoid racing condition where 2 requests try to mdofiy the account balance simultainusly. 

Distrubuted locks are an example of such tools to synchronise the requests within nodes which in the above example would make sure the Request 1 executs the transaction, before the Request 2 and 3 execute, this would ensure the balance on the account contains enough funds and will have accurate results. 
        
        
Latency - is the time it takes for a data packet to reach to destination

Throughtpiut - is the ammount of data can pass through a network or connection within a single time frame (second or minute)



      





**Monitor uptime so you can sleep at night**
