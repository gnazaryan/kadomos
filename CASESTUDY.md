**Scaling API gateway**

Horizontal scaling of an application let's increase the Thoughoutput, Latency and Availability of the system, which ensures the critical to importance busness needs are met, in a typical case, the Api Gateway let's users manage the account which holds critically important financial securityies. 


According to case study details and implementation the current Api Gateway has a single server/node machinery structor as can be seen below. 

1. 

                      Api Gateway
                      
                      /          \
                      
              Account A         Account B


This structure is fine, until the business meets a new requirments to enhance the api accessability, particularly a typical business requirment could be to consider 1 million active user transactions per minute, in this case the single node structure would fail because of the over load.

For this reason most commonly a Load Balancer is used to distribute the work of the system. In some other cases, architectures include several layers of load balancers, and the number of layers can be increased to support desired level of Latency and Throughoutput. A sample of such architecture can be seen below.

2. 
                                       
                                                                         Load Balancer
                                                          
                                     /                                          |                                     \
                                                           
                          Load Balancer                                    Load Balancer                          Load Balancer  ......
                          
                         /             \       ...                        /              \     ...               /              \
                          
              Api Gateway                   Api Gateway                 Api Gateway
              
               /        \                   /        \                 /           \
               
       Account A   |   Account B      Account A   |   Account B     Account A   |   Account B                    ......
    
    
On the 1. Structure of architecture we can see a single instance of Api Gatway redirecting requests to Account A and Account B which limits the precessing power to the machinery underlying these 3 servers, On the other hand the 2. structure is more robust and employes many number of nodes serving the requests which would necessarily improve the latency and throughput. The top load balancer first accepts the request and basedon some load statistics dispatches to the second layer of load balancers which in turn dispatch to the target Api Gatway in hand based on some other statistical data or conditions. This ensures the requests are equally distributed through the distributed system, hence balancing the load.
    
But this raises other concerns and questions, are we all set?, and the answer is kind of what did you expect, not really, if we assume multiple Account A nodes are servers function on the same data, then it could potentially face concurrency and racing conditions. If multiple requests try executing a Debit transaction simultainusly on the same account like in the below order, this may cause arithmetic errors in the balance calculation.

        Request 1: Get Balance - 75 euro
        Request 2: Get Balance - 75 euro
        Request 3: Get Balance - 75 euro
        Request 1: Debit transaction- 45 euro
        Request 2: Debit transaction- 45 euro
        Request 3: Debit transaction- 45 euro
        
In the above example we can assum 3 requests are performed simultainusly and all request threads reach the point of Get Balance which returns 75 euro, and then they all start making Debit transaction which would execute successfully leading wrong result.


To avoid this scenarious the data modification of the account nodes must be done in a synchronious way to avoid racing condition where 2 requests try to mdofiy the account balance simultainusly. 

Distrubuted locks are an example of such tools to synchronise the requests within nodes which in the above example will make sure the Request 1 executs the transaction, before the Request 2 and 3 execute, ensuring the balance on the account contains enough funds and producing acurate results.
        
        
Latency - is the time it takes for a data packet to reach to destination

Throughtpiut - is the ammount of data can pass through a network or connection within a single time frame (second or minute)



      








**Monitoring uptime**

Availability is one of the other important and critical factors of a distributed system, it is the time a system remains functional to perform its required task in a specific period of time. Most of the critical services employe tools to monitor the availability of the system. These monitoring tools collect mmetrics detailing the health of the system in a given point of time which actively passis throuigh analytics to provide robust alerting systems ensuring the resource is available, in case the mmonitoring observes system metrics not being helthy or missing it generates alerts and alarms the users through communication means.

One of such basic tools is a Heartbeat System. A Heartbeat System is a peace of active process nested in the node being monitored which peridocally (in a predefined interval, every minute or 5 minutes) runs a self health status check inside a system to ensure services are running and sends the status information to the monitoring tool. 

The monitoring tool itself does periodically check the status of the node and in case the status update is missing or the update is late it starts generating alerts and alarms the subscribed support to troublshoot the problem.


