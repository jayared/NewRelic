Coding Challenge New Relic (Java 1.8).


<B>Gradle</B>
The build framework provided here uses gradle to build  project and manage dependencies. The gradlew command used here will automatically download gradle for you so you shouldn't need to install anything other than java.

The entire solution uses java.io.* java.net.* java.util.* java.Nio.* No Dependencies. 

<B>Project Layout</B>
All source code should be located in the src/main/java folder.
The Main script is in /src/main/java/com/newrelic/codingchallenge/Main.java.



 <B>Assumptions or Solution</B>

Deleted "numbers.log" file while the socket is started if the file exists.

By using  ExecutorService fixed thread pool size is limited to 5.

Executor service shuts down if the input contains "terminate".

Created LogWriterTask to write to numbers.log

Any data that does not conform to a valid line of input is discarded and the client connection terminated immediately and without comment.

Created SummaryTask to write Summary for every 10 seconds in the console.


A Server class that handles and reads the input streams for each of the clients. responding to inputs dynamically as they come in.

A BitSet is used to identify duplicates. 

As per requirement, only Unique numbers are logged to the numbers.log file.

Numeric Literals are used to improve the readability of code.

AtomicBoolean is used to control the while loop.

Used LinkedBlockingQueue -
The LinkedBlockingQueue is an optionally-bounded blocking queue based on linked nodes. It means that the LinkedBlockingQueue can be bounded, if its capacity is given, 
else the LinkedBlockingQueue will be unbounded. The capacity can be given as a parameter to the constructor of LinkedBlockingQueue. 
This queue orders elements FIFO (first-in-first-out). 
It means that the head of this queue is the oldest element of the elements present in this queue. 
The tail of this queue is the newest element of the elements of this queue.



Wrote unit cases to cover all edge cases.
