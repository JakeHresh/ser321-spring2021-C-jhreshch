Task 1

1.

The code is split up into 5 source code files, namely, Branch.java, MergeSort.java,
NetworkUtils.java, Node.java, and Sorter.java. 

The NetworkUtils.java file simply handles the sending and receiving of JSON objects
to simplify how packets are sent.

The MergeSort.java file acts as a sort of client that interacts with Branches and
Sorters to sort the values in an array from the Test method. The class contains
an init method, which takes the values of an array and stores them in a JSON array,
a peek method, which creates a JSON object with a peek request, and a remove method,
which creates a JSON object with a remove request. 

Node.java acts as a parent to the Sorter and Branch classes. This allows for 
different implementations of the responses to init, peek, and remove requests 
between the Sorters and Branches while also allowing both to share a run method,
which executes those responses. 

If a Sorter is asked to init, it will receive
the contents of the array from the JSON object and add those to its internal
priority queue, which will cause the elements to become sorted as they are inserted
in the queue. 

If a Branch is asked to init, it will receive the contents of the
array from the JSON object and split those array elements into two separate
arrays while also saving the entire collection as another array. It will then send
those array halves to the two nodes that are connected to that Branch. One big
disadvantage is that, at the most extreme Branches, there must be two Sorters
connected. An extreme Branch cannot have fewer than two Sorters because an error
will result otherwise.

If a Sorter is asked to peek, it will check to see if it has a value in its list
and will return that value if so. This peeked value will be the first element of 
the priority queue.

If a Branch is asked to peek, it will send two peek requests, with one request
going to one of the Branch's connected nodes and the other request going to the
other connected node. Assuming values are found, the two values of the responses 
received are compared with the smaller value being returned.

If a Sorter is asked to remove, it will check to see if it has a value in its list
and will remove that value if so while also returning the removed value.

If a Branch is asked to remove, it will send two peek requests, with one request
going to one of the Branch's connected nodes and the other request going to the
other connected node. Assuming values are found, the two values of the responses
received are compared. Once the smaller value is determined, a remove request is
sent to go back and remove the value.

The main advantage of this distributed algorithm is its potential in saving memory
for one system by distributing the data across several nodes.

However, the algorithm is limited by this distribution in performance. This is
especially evident in the implementation of the remove requests. Assuming there
are several layers of Branches to traverse before reaching the Sorters that
actually store the sorted data, considerable time would be spent sending two 
responses to child Branches to request a simple Peek. Additionally, this time would
be compounded by the layers of Branches where each Branch is required to have two
nodes connected to it, thereby compounding the number of peek requests needed to
reach the Sorter leaves. Once these leaves are reached and the data is passed back
up to the higher branches, additional remove requests must be made after the peek
comparisons. This means that the algorithm must constantly travel up and down
the layers of Branches before finally completing the requests. This is not performant,
especially with a large distribution of data.

2.

This experiment will test the different sorting times when using different array
numbers and sizes. Each experiment will involve the use of 1 MergeSort instance,
1 Branch instance, and 2 Sorter instances. Each array will be tested 10 times.
These times will then be averaged. 

The arrays to test will be:
|||
|---|---|
|ARRAY 1:| {5, 1, 6, 2, 3, 4, 10, 634, 34, 23, 653, 23, 2, 6}|
|ARRAY 2:| {1, 2, 3}|
|ARRAY 3:| {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}|
|ARRAY 4:| {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}|

The following are the resulting average times:

|||
|---|---|
||(Times are in milliseconds)|
|ARRAY 1 Average Time: |260|
|ARRAY 2 Average Time: |181.3|
|ARRAY 3 Average Time: |273.6|
|ARRAY 4 Average Time: |250.5|

These results indicate that the size of the array is the largest contributor to how
quickly the array will be sorted. The smaller the array, the faster the array will
be sorted. This time also accounts for how quickly the Sorters are initialized, so
it makes sense that the fewer array elements there are, the less time it will take
to feed the Sorters the data.

Additionally, the results indicate that array elements that are in complete reverse
order will take longer to sort. This is evidenced by ARRAY 3, a reversed array of 
10 elements taking longer to sort than ARRAY 1, a mixed array of 14 elements. This
might be the result of the internal implementation of the priority queue.

The second finding indicates that distribution might help performance to a certain
extent to reduce the time spent on the internal sorting of the priority queue. 
However, there should be a threshold where to much distribution leads to too much
overhead. In any case, the effect of distribution will be further understood in the
second experiment.

3.

This experiment will test the different sorting times when using different numbers
of nodes to distribute the data. The experiment will utilize the same arrays from
the first experiment. Once again, each setup with each array will be tested 10
times with these times being averaged.

Additionally there will be three different tree setups:
|||
|---|---|
|SETUP 1: |1 MergeSort instance, 0 Branch instances, 1 Sorter instance|
|SETUP 2: |1 MergeSort instance, 1 Branch instance, 2 Sorter instances|
|SETUP 3: |1 MergeSort instance, 3 Branch instances, 3 Sorter instances|

Worth noting is that this experiment was carried out slightly differently than the
previous experiment. Instead of manually executing the run commands 10 times for each
array as in the previous experiment, this experiment uses a loops, each that iterate
10 times. Each loop executes a different test command that runs a different array.
This way, I'll only need to restructure the tree manually. However, this does produce
results that vary slightly from the previous experiment. That said, something valuable
should still be produced.

(Times are in milliseconds)

||||||
|---|---|---|---|---|
||ARRAY 1|ARRAY 2|ARRAY 3|ARRAY 4|
|SETUP 1|70.6|15.2|23.9|37.3|
|SETUP 2|247.3|55.2|119.6|86.3|
|SETUP 3|332.9|135.3|212|122.7|

These results indicate that the more branches there are that separate the MergeSort
instance from the Sorters (also resulting in more Sorter instances), the more time
is spent. This indicates that the distribution does not make the sorting faster. 
It is possible that there is a threshold where the data distribution makes the sorting
better. However, my VM is not able to support more than this number of Branches.
In any case, the results indicate that it is unlikely the performance will be any
better than what was demonstrated when running the tests with a single Sorter.

4.

There is a lot of TCP traffic from the instructions sent from the MergeSort instance 
to the nodes it is contacting. Not surprisingly, the most traffic comes from the series
of remove instructions, which sends one element of a Sorter priority queue at a time. 
This would explain why this overhead would compound so much as more Branches are included.
One simple way to reduce the overhead would be to send all the elements of a Sorter priority
queue over the wire at once in a JSON array, much like the init method does already. There
would still be some overhead in prepping the array, but at least the additional overhead of
sending data over the wire won't be as present since more data would be sent at once.

Task 2

1.

Before running the AWS Sorters, I do expect to find changes in runtimes since there should
be additional latency in the communication between the local Branch and the remote server.

2.

As with the previous experiments, I decided to run 10 tests for each array. These tests
will use the setup that has a local Branch and two remote Sorters.

(Times are in milliseconds)

|||
|---|---|
|ARRAY 1 Average Time: |7904|
|ARRAY 2 Average Time: |2550.2|
|ARRAY 3 Average Time: |5886|
|ARRAY 4 Average Time: |5839.3|

These times are much longer than the times on the localhost. This is most likely the result
of the expected latency as packets are sent over the wire to a remote server. When examining 
the TCP traffic, there are occasionally Keep-alive packets, indicating that some packets are
not successfully sent over the wire and that the packets must again be sent. Issues like this
only further compound the amount of time spent sending packets over the wire through Branches.

Task 3

1.

Most time is lost when data is sent to remote Sorter instances like those on the AWS instance.
There is little that can be done to greatly improve the efficiency if data must be sent to
such Sorters. Structural changes like reducing the number of Sorters to 1 can reduce the
communication needed to support sorting, which can also reduce time spent. Additionally,
algorithmic changes like sending the entire sorted array over the wire as a JSON array
instead of sending single elements one-by-one over the wire can greatly reduce communication
time, thereby improving efficiency. However, as stated earlier, connection issues with the AWS
instance and other issues in communicating with remote distributed systems are difficult to 
address when attempting to improve efficiency.

2.

Given the above findings, it doesn't make much sense to run this algorithm as a distributed
system. Any benefit in saving memory resources by splitting up the data for distributed
systems is eclipsed by the performance hits that result in such splitting, especially when these
distributed systems reside on remote AWS instances. Therefore, this algorithm would fare better as
either a parallel or even a sequential algorithm.
