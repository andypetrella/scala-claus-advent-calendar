# Scala Claus' Advent Calendar

## What the heck?
### Goal and context
This application is actually a talk, that was initially given at [BruJUG in Belgium/Brussels](http://wiki.brussels-jug.be/doku.php?id=events4:2014_01_session1).

It has been given for a team at Virdata (Technicolor) which is putting efforts in building their Big Data/IoT platform in Scala.

Another session is for the JDL in Mons (still in Belgium), [the 15th of May 2014](http://jeudisdulibre.be/2014/04/30/mons-le-15-mai-echo-java-sed-sjavscal-le-langage-de-programmation-scala/).

This talk aims to be a gentle, pragmatic and didactic approach of the programming language [Scala](http://www.scala-lang.org/).

### Synopsys
The line up is composed of 25 features of the language.

They all share the same property, that is, they can be used in our everyday work. 

They are presented in such a way that the next feature enables a cleaner code -- most of the time, more readable.

And, of course, the level increases slightly in order to take the time to catch the concepts intuitivelly. 

### Features
1. JVM and learning curve
2. First code look-a-like
3. Removing noise
4. Cleaning
5. String interpolation
6. Enhance the JDK
7. Inference
8. Function
9. Method
10. Easy immutability
11. Default Parameter
12. Object
13. Class hierarchy
14. Operator
15. List.map
16. List.flatMap
17. Improving readability, take 1, the List
18. $1M mistake
19. Option.map
20. Option.flatMap
21. Improving readability, take 2, the Option
22. Structural meaning
23. Asynchronous tasks
24. Readable and sane asynchronous tasks
25. &not; Explicit

## How to use it
### Requirements
This talk is a [Play! 2](http://www.playframework.com/) application that uses a customized version of the framework.

This version can be found [on github](https://github.com/andypetrella/playframework).

To install it locally, you'll simply need to:
```
$ git clone https://github.com/andypetrella/playframework
$ cd playframework/framework
$ ./build
```

This will prepare the whole thingy, and put you in a [SBT](http://www.scala-sbt.org/) console, and will let you do the very final step:
```
[info] Set current project to Root (in build file:<...>/playframework/framework/)
> publish-local
```

This might take sometime since it will compile and create the framework itself and finally publishing it locally. Publishing locally in SBT means that the build packages will be put in `~/.ivy2/local/com.typesafe.play`.

**NOTE**: From now on, we'll assume that the `play` executable in the playframework directory will be in the `PATH` under the alias `cumstom-play`.

### Run
This talk being a Play! 2 application, the very first thing to be able to use it is to laynch the play console in its directory.

So after having cloned the repository, we can move into it, and enter the console.
```
$ git clone git@github.com:andypetrella/scala-claus-advent-calendar.git
$ cd scala-claus-advent-calendar
$ custom-play
```

This will prepare some stuffs (mainly downloading some libs) and enter a Play console (being a customize SBT one).

To launch it, you'll simply have to run it
```
$ ~run
```

Using the continuous execution (hence the `~`) will increase the responsivity of the talk (since changes will be reloaded in the background automatically).

Now, we can head to a Chrome (preferred browser for the talk) and go to [http://localhost:9000](http://localhost:9000).

### The talk
#### Present it, the hard way
go to

start button

the day1 code fails, update the code

save it

If it fails, update again

If it has comilation error, look at the error message, update again

Until the slides is shown

Switch to the result, code, slide

Next slide, repeat until final page

#### God Mode, the soft way
only the slides are coming, by picking the cheat.Day* actions.


#### Shortcuts recap
CTRL+S

left; right;

CTRL+ALT+C

CTRL+ALT+F

c

t

r



## Behind the scenes
### Concepts

### Failing action
#### Action composition

#### Parse the code
##### Fetch lines
##### Fetch comments
##### Fetch folds

#### Save the code

### Compilation errors
#### The problem
#### The solution

### Cheating
index page with special param

fetch the code within the cheat package
