[![Build Status](https://travis-ci.com/mtumilowicz/java-iterator-fail-types.svg?branch=master)](https://travis-ci.com/mtumilowicz/java-iterator-fail-types)

# java-iterator-fail-types
_Reference_: https://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601

* **Fail-fast**

    Collections maintain an internal counter called `modCount`. 
    Each time an item is added or removed from the Collection, 
    counter gets incremented.
    
    When iterating, on each `next()` call, the current value of 
    `modCount` gets compared with the initial value. If there’s 
    a mismatch, it throws `ConcurrentModificationException` which 
    aborts the entire operation.  
    However, this check is done without synchronization, so there is a risk of
    seeing a stale value of the modification count and therefore that the iterator does
    not realize a modification has been made. This was a deliberate design tradeoff
    to reduce the performance impact of the concurrent modification detection code

    _Remark_: ConcurrentModificationException can arise in 
    single-threaded code as well; this happens when objects 
    are removed from the collection directly.

    _Remark_: If during iteration over a Collection, an item is 
    removed using Iterator‘s `remove()` method, that’s entirely 
    safe and doesn’t throw an exception.

    _Example_: Default iterators on Collections from `java.util` 
  package such as **ArrayList**, **HashMap**, etc.

* **Fail-safe**

    Those iterators create a clone of the actual Collection and 
    iterate over it. If any modification happens after the iterator 
    is created, the copy still remains untouched. Hence, these 
    Iterators continue looping over the Collection even if it’s 
    modified.

    Disadvantage is the overhead of creating a copy of the Collection, 
    both regarding time and memory.

    _Example_: **CopyOnWriteArrayList**

* **Weakly consistent**

    Reflects some but not necessarily all of the changes that 
    have been made to their backing collection since they were 
    created. For example, if elements in the collection have 
    been modified or removed before the iterator reaches them, 
    it definitely will reflect these changes, but no such guarantee 
    is made for insertions.

    _Remark_: The default iterator for the `ConcurrentHashMap` is 
  weakly consistent. This means that this Iterator can tolerate 
  concurrent modification, traverses elements as they existed 
  when Iterator was constructed and may (but isn’t guaranteed to) 
  reflect modifications to the Collection after the construction 
  of the Iterator.

    _Example_: Default iterators on Collections from 
  `java.util.concurrent` package such as 
  **ConcurrentHashMap**, **ConcurrentSkipListSet** etc.