bankersAlgJava
==============

Implementation of the Banker's Algorithm for CS4323 at Oklahoma State University with Dr. Michel Toulouse

Solution is provided in Java

BankerThread.java - A Runnable object used to issue requests for resources to the banker

Banker.java - Manages requests and releases from running BankerThread instances

BankersAlg.java - Initializes, starts, and stops the algorithm

Instructions
==============
To compile and run:

1. Navigate to bankersAlgJava/src
2. Enter ```javac *.java```
3. Enter ```java BankersAlg n d r1 r2 ... rm```

  Argument Descriptions
  ---------------------
  n - The number of threads competing for resources

  d - The duration, in seconds, that the algorithm will run for

  rx - The available resources of type x. Any number of resource types that can be stored as a Java int may be used.

==============

Example Output:

Output from 
```
java BankersAlg 4 10 1 1 1 1 1 1 1 1 1
```
can be found in bankersOut.txt

==============

Find more from Matt at matthewahorton.com
