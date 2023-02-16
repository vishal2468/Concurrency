## Agenda
* Threads
* Process
* Using threads
* Daemon threads
* Life cycle and threads states
* Sleeping, joining and interrupting threads

* Race conditions
* Synchronization
* Monitors and structured locking
* The volatile keyword
* Thread local

* Advanced concurrency APIs
* Unstructured locks
* Executor service
* Thread pool types
* Callables and Future
* Semaphores
* Fork Join framework

## Thread
* A thread is a single sequential flow of control within a program.

## Thread
* Single sequential flow of control
* Sequence of programmed instructions that can be managed independently
* Allows the program to split into simultaneously running tasks

## Process
* Binary instructions loaded into memory
* Gets access to resources like memory
* (its own stack, heap, registers)
* Resource is protected from other processes
* Does its thing

## Processes are independent
* Can be started and stopped without affecting others
* Cannot talk to each other (unless mechanisms are coded into them)

## Thread vs Process 
* Default execution mode of a process is concurrent

## Processor
* Has multiple cores

## Requirement
* A process needs some instructions to run in parallel (not sequential)

## Solution
* Threads 

## Thread
* Unit of execution within a process
* Does the work of a process
* Usually has a shared objective
* Has shared resources like memory, heap storage

##  Process can be single threaded...
* All instructions executed sequentially
## ... or multithreaded
* Process can "spawn" threads that could run in parallel

## A Java application
* A single process (JVM)
* Consists of various threads
* Application thread - responsible for running the main method
* Other threads for runtime concerns - like garbage collection

## Need more threads?
Created using a language API

#  The Thread class

## Java Concurrency primitives

## Classical Concurrency APIs 

## Runnable
* Something that can be run
* Has a run method

## Steps
* Identify the code you want run in a separate thread
* Put it into a Runnable
* Create a new Thread from that Runnable
* Start the thread

```java
public class MyRunnable implements Runnable {
    public void run() {
        System.out.println("I am running!");
    }
};

Runnable r = new MyRunnable();
Thread t = new Thread(r);
t.start();
```

## How this works
* JVM calls the underlying OS threading APls

## When does a thread end?
* When the run method returns
* An exception is thrown

## Syntax Alternatives
* inline class
```java
Runnable r = new Runnable() {
    public void run() {
        System.out .printin("Running");
    }
}
```
## Syntax Alternatives
* lambdas
```java
Runnable r = () -> System. out. printin( "Running");
```
 

## Syntax Alternatives
```java
new Thread(() -> System. out .printin("Running")) .start();
```

```java
class MyThread extends Thread {
    public void run() {
        System.out.printin("Running");
    }
}
new MyThread() .start() ;
```
 
## Summary
* Runnable is a functional interface
* Lambdas can be used instead of Runnable instances
* Thread can be subclassed to create a Runnable
* Recommend creating a new Runnable instead of subclassing Thread

```java
while (true){
    Scanner sc = new Scanner (System. in);
    System.out.println("In I can tell you the nth prime number. Enter n: ");
    int n = sc.nextInt();
    if (n == 0) break;
    Runnable r = new Runnable(){
        @Override
        public void run() {
        int number = PrimeNumberUtil. calculatePrime(n);
        System.out.println("\n Result:");
        System. out. println("In Value of " + n + "th prime: " + number);
        }
    };
    Thread t = new Thread(r);
    t.start();
}
```

## Deamon threads

## When does the application end?
* Usual answer:
* When the main method thread ends

## When does the application end?
* When you spawn threads:
* When the last thread ends

## Two types of threads
* User threads
* Daemon threads

## Examples of user threads
* The main method
* Your new Thread (runnable).start() 

## When does a Java application end?
When all the user threads have exited

What if you don't want the application exit to wait for a thread? 
Daemon threads

Daemon Thread
```java
Thread t = new Thread(r);
t.setDaemon(true);
t.start();
```

## High level thread states
* Just created
* Running
* Blocked
* Terminated

## Actual Thread states
* New
* Runnable
* Blocked
* Waiting
* Timed Waiting
* Terminated

## Sleep
* Pausing or waiting for a thread

```java
public static void sleep(long millis) throws InterruptedException
```

* below is status reporter , which should be a deamon thead.

```java
Runnable statusReporter = () -> {
try {
        while (true) {
            Thread. sleep (5000);
            printThreads(threads);
        }
    } catch (InterruptedException e) {
        System. out .printin("Status report thread interrupted. Ending status updates");
    }
};
```

## What if we need to wait?
* Use case: Print a message after the last thread ends

## Wait for a thread
* vs sleep (fixed time)

## Join
* "Joins" a certain thread with the currently running thread

```java
myThread. start();
// ...
myThread. join();
// this executes after mvThread has ended
```

## wait till all the threads are terminated
```java
private static void waitForThreads(List<Thread> threads) throws InterruptedException {
    for (Thread thread : threads) {
        thread. join();
    }
}
```

## Barrier synchronization

## Stopping a thread
• Interrupt the thread
• Have it voluntarily clean up and shut down

## interrupt ()

```java
Thread t = new Thread(r);
t. start();
// ...
t.interrupt();
```

* Built in blocking operations handle interrupts

```java
try {
    Thread. sleep (5000);
} 
catch (InterruptedException e) {
    System. out .printin("Interrupted!"); 4
}
```

```java
while (true) {
// Run task
// ...
    if (Thread. interrupted()) {
        throw new InterruptedException();
    }
}
```

```java
reporterThread. interrupt();
```

## Interrupts
* "Soft" interrupt
* Very different from hardware interrupts
* Depends on what the implementation does

## Concurrency vs parellelism


## Scheduler
* Operating system process
* Responsible for scheduling a thread to an available processor core

## Scheduler
* Unschedules a running thread temporarily as needed
* Tries to be fair
* Honors priorities
* Non deterministic

## Two threads running on two cores
* Parellelism

## 10 threads running on one core
* Concurrency

## Concurrency
* Can be done with multicore CPUs
* Can be done with single core CPUs

## Parallelism
* By definition, needs multiple cores

## Need for Concurrency

* Threads don't share data?
No problem

* Threads share constant (unchanging) data
No problem

* Threads read and write to the same data
Problem

## Two Race Condition Patterns

* Check-then-act
* Read-modify-write

# Solving race conditions

## The solution
* Make sure only one thread can "pick up" a data element

## Lock and key model

## Coordinating access

## Synchronization
* Make sure two threads don't simultaneously access a critical data element
* Data , not Code
* JVM Feature

```java
public void increment() {
    isolateThis {
        this.counter++;
    }
}
```
## Analogy
* Only one guest can use any hotel room at a time - Wrong
* Given a hotel room, only one guest can use that room at a time - Correct

## Writing Synchronization
* Programmer marks a data element as a lock
* Programmer marks a piece of code to be accessible by the lock holder

## How synchronization works
* JVM creates a virtual "lock" from the data element
* Thread tries to "acquire" a lock
* If it acquires it, it can execute the synchronized code
* Thread finishes executing block and "releases" lock
* All other threads that need to execute the block have to wait

## Semi-declarative
* You declare to specify the hotel room and the key

## Synchronized block
```java
// Some code
synchronized (objectRef) {
// Code to be executed one thread at a time
}
```

1. Thread tries to get access to the monitor lock
2. If thread gets it, it executes the block
3. Releases the monitor lock when exiting the block
4. Any other thread needs to wait (can't get monitor lock)

## Critical section

## Monitors
* Obiect based isolation

```java
public void methodA() {
    synchronized (obj1) {
    // synchronized code here
    }
}
public void methodB() {
    synchronized (obj1) {
    // some other synchronized code here
    }
}
```
## Synchronized method
```java
public synchronized increment() {
}
```
## Synchronized methods
* Lock is associated with the object whose method is being called
* Lock is implicitly applied on "this".

## What synchronization achieves
• Mutual exclusion
• Visibility

## Mutual exclusion
Mutex

## Visibility
• Value is read from memory before block execution
• Value is written to memory after execution completes

## Structured lock
1. Block structure using synchronized
2. Acquiring and releasing locks are implicit
3. Example: Exception causing control to exit: lock auto-released

## Structured lock
1. Block structure using synchronized
2. Acquiring and releasing locks are implicit
3. Example: Exception causing control to exit: lock auto-released

## Thread safe

## Have we solved concurrency?
No

## Problem
* Performance

## Mutex locks
Careful application needed

## Mutex locks
• Choose the right object for the lock
• synchronize the bare minimum code necessary

## Problem 1: Choosing the wrong lock
Remember: It has to be the same lock!
```java
public void methodA() {
    synchronized (obj1) {
        // ...
        methodB() ;
    }
}
public void method() {
    synchronized (obj2){
        // ...
    }
}
```

## Problem 2: Extreme synchronization
Non-concurrent (serial) code

## Bigger problem
Liveness

# Liveness - deadlocks, livelocks and starvation

## Liveness
• State of general activity and motion
• Requires a system to make progress
• Not "stuck"
• Something good will eventually occur

## Program execution
• Starts
• Executes
• Completes successfully or errors out
• Hangs

## What can cause liveness issues?
The infinite loop

## What can cause liveness issues?
The Infinite loop

## Liveness issues with concurrency
• Deadlock
• Livelock
• Starvation

## Deadlock
• Multiple threads are waiting for other threads
• The dependency is circular

## Deadlock example
The "No, you hang up first" problem
Dependency is circular

```java
synchronized(objRef1) {
    synchronized (objRef2) {
        //
    }
}
synchronized (objRef2) {
    synchronized (objRef1) {
        //
    }
}
```

## Other variants
Circular invocation of synchronized methods
s(a)->s(b), s(b)->s(c), s(c)->s(a)

## Other variants
Two threads invoking join on each other

## Result
Potentially threads waiting forever

## Livelock
A "smarter" deadlock

## A naive solution to deadlock
• Try to get lock 1
• Try to get lock 2
• If lock 2 not acquired in x ms, release lock 1
• Try again after sometime

## Livelock example
Two people in each other's way in a corridor

## Livelock
• Potential deadlock
• Steps taken to mitigate deadlock causes perpetual "corrective" action
• Not completely "dead".
• ..but all activity is just to get the lock

## Stalemate in chess

## Starvation
Thread is ready to run but is never given a chance

## Starvation example
Low priority thread not scheduled by the executor

## Indefinite postponement

## Liveness issues with concurrency
• Deadlock
• Livelock
• Starvation

## How do you avoid them?
• No Java / JVM feature to avoid these
• Careful use of locks
• Example: Avoid using more than one lock

# The volatile keyword

## The visibility problem

## The synchronized keyword
Fixes the problem at the boundaries

## That may not always be ideal
• Something else affects when the synchronized block ends
• Synchronous access might not be a problem
• You want to just fix visibility

```java
public class Foo {
private volatile int value;
}
```

Behavior - given order
• Thread 1 writes variable (to memory)
• Thread 2 reads variable (to memory)

## Applies to other variables visible in a thread(but only those that are before)
```java
public class Foo {
private volatile int value;
private boolean hasValueUpdated;
hasValueUpdated = true;
value = 20;
}
```

## Understanding thread local

## Access a variable in multiple places in a thread

```java
Runnable r = () -> processUserData(userId);
//...
public void processUserData(int userId) {
    doSomeStuff();
    dootherStuff(); 
    moreStuff();
}
```
* Need to access the user ID in multiple places in the thread

## Options
## Need to access the user ID in multiple places in the thread
1. Pass around the variable everywheie
2. Use a class member variable

## Another option
* Use a thread local variable

## Thread local
1. Scope is per-thread
2. "Global" in the context of thread instance
3. Each thread just sees its own thread local variable

## Declaring ThreadLocal
```java
ThreadLocal<Integer> threadLocalUserId = new ThreadLocal<>();
```
## Accessing ThreadLocal
```java
threadLocalUserId.set(1234);
Integer userId = threadLocalUserId.get();
```

## ThreadLocal
1. Generic class
2. Almost like a wrapper
3. Each thread sets and gets a different value

# Unstructured locks

## synchronized keyword
1. Structured locks
2. Acquire and release handled for you
3. Nesting possible
```java
synchronized (obj1) {
// Access obj1
    synchronized (obj2) {
    // Access obj1 and obj2
    }
}
```
## Sometimes, nesting might not work!
```java
for (int i = 0; i < arrsize - 2; i++) {
process (arr[il, arrli + 1]);
}
```
```java
for (int i = 0; i < arrsize - 2; i++) {
    synchronized(arr[i]) {
        synchronized(arr[i + 1]) {
            process (arr[i],arr[i + 1]);
        }
    }
}
```
## Hand over hand locking
```java
private Lock l = new ReentrantLock()
```

```java
public void run() {
    l.lock();
    this.increment();
    System.out.printIn(Thread.currentThread().getName() + " increments: " + this .getValue());
    this.decrement();
    System.out.println(Thread. currentThread().getName() + " decrements: " + this.getValue());
    l.unlock();
}
```

```java
lock()
lockInterruptibly()
newCondition()
tryLock()
tryLock(long time,TimeUnit unit)
unlock()
```

## The Executor service
