Coding Challenge New Relic (Java 1.8).

The Main script is in /src/main/java/com/newrelic/codingchallenge/Main.java.

The entire solution uses java.io.* java.net.* java.util.* java.Nio.* No Dependencies. 

Deleted "Numbers.log" file while the socket is started if the file exists.

By using  ExecutorService fixed thread pool size is limited.

Created LogWriterTask to write to Numbers.Log
Created SummaryTask to write Summary for 10 seconds.

A Server class that handles and reads the input streams for each of the clients. responding to inputs dynamically as they come in.

A BitSet is used to identify duplicates. 

As per requirement, only Unique numbers are logged to the Numbers.Log file.

Used BlockingQueue -
A Queue that additionally supports operations that wait for the queue to become non-empty when retrieving an element, and wait for space to become available in the queue when storing an element.

Numeric Literals are used to improve the readability of code.

Executor service shuts down if the input contains "terminate".

AtomicBoolean is used to control the while loop.

Wrote unit cases to cover all edge cases.
