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
$ git checkout -b my-new-session
$ custom-play
```

This will prepare some stuffs (mainly downloading some libs) and enter a Play console (being a customize SBT one).

Note: the creation of a dedicated branch for the session is not mandatory but it's cleaner. That, because the application will create or **change** files in the repo that won't have to be pushed!

To launch it, you'll simply have to run it
```
$ ~run
```

Using the continuous execution (hence the `~`) will increase the responsivity of the talk (since changes will be reloaded in the background automatically).

Now, we can head to a Chrome (preferred browser for the talk) and go to [http://localhost:9000](http://localhost:9000).

### The talk
#### Present it, the hard way
##### Premisses
The very first page is decoupled from the rest of the talk. It simply shows the title of the talk and a button that will start the presentation application.

So click `start`.

##### Slide
###### First show
At this stage, the application starts and shows a red-ish page with only a code block in it.
This code is erroreneous and will have to be updated until it passes.

###### Changes
What has to be changed? As usual in *Scala*, the lines that have to be filled or reworked contains a `???` value in it.

For simplicity, these lines are reported as errors in the margin of the editor. So, hovering the red crosses symbols will pop a help message for the current line.

###### Save
When the code looks good, it's time to push it to the application, that is, *to save* it. This can be achieved by pressing `CTRL+S` (Apple® fans will translate easily :-D).

When the updated code is saved, there are several cases.
###### Compilation failure
The submitted code is not compiling.

Fortunately, Play! 2 is able to deal with this kind of failures and will return a error page. In order to not break the presentation flow, this error page will be opened in another window.

So can you eaily find what the problems are and update the code accordingly, before saving once again.

###### Runtime failure
The other case that might (will?) happen is that the saved code won't work, and result in a runtime error.

These errors are silently handled, thus saving the code and staying on the same page means change something.

**NOTE**: I know it's BAD, and thus, it might change (if you help me, or if I have a chance to find some time... a day...).

###### Cannot make it work!?
It's time to **cheat**. No fear, it may happen for a plenty of reason, like the stress, the time or whatsoever and not related to your Scala skills!

To load the cheat code, just press `CTRL+ALT+C`. (*mnemonic* is *C* for *C*he*A*t *C*ode).

###### Compiled and ran
Success!!

The result of the published code will be available in the background, but the code itself will be replaced by a slide explaining to the crowd what just happened.

Some shortcuts are available to show the code back, the result, and so on. Check the [Shortcuts recap](#shortcuts-recap) section for them.

###### Recursion
This process is true for all slides, so at this point, the best is to move to the next slide.

Press `right`.

Reaching the last slide, and `left` won't do anything...

###### Backward
You pressed `right` too fast, or a question pops out in the attendance related to the previous slide...

Press `left` to backtrack one slide.

#### God Mode, the soft way
You know, if you want to speed up the talk or repeat it in your bedroom or ... or... In these cases, it's worth switching to the **god mode**.

In god mode, only the slides are coming up, that means that only the result of the publishing of compilable and running codes (the application switches to the controllers and actions within the `cheat` package).

No more suspense, switching to this mode is pretty simple by adding a `cheat=true` query parameter to the URL. An example on the root URL is [http://localhost:9000/?cheat=true](http://localhost:9000/?cheat=true).

#### Shortcuts recap
* Saving the code: `CTRL+S`
* Next slide: `left`
* Previous slide: `right`
* Load cheating code for current slide: `CTRL+ALT+C`
* Show code in full screen: `CTRL+ALT+F`
* Show the successful *code*: `c`
* Show the execution *result*: `r`
* Show the resulting *text*: `t`. This will show the slide back if we had to show the code (using `c`) or the execution result (using `r`).

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
