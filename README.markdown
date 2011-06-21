Source code for 'Mahout in Action' book
==========

# Installation #

To work with source code you need to have [Apache Maven](http://maven.apache.org/)
installed (if you use Linux, it's better to install it from repository).

Use of IDE with Maven support (Eclipse, Netbeans, IntelliJ IDEA) is encouraged.

After you'll get source code of examples, you need to compile it using `mvn package`
command, staying in the `MiA` directory.  This command will fetch all necessary
dependencies, compile, and package everything you'll need for work.

For some examples you will need Apache Mahout distribution.  You can grab latest release
version from [site](http://mahout.apache.org).  Grab both binary and src distributions,
and unpack them in some place.

Examples from chapter 16 also require installation of
[Apache ZooKeeper](http://zookeeper.apache.org/).  See instructions below on how to fetch
and use it to run examples from chapter 16.

# Work with examples #

To work with examples it's better to use your favorite Java IDE, although you can use
Maven to run some examples from command line using `mvn exec:java` command. For example,
to run `IREvaluatorIntro` example from chapter 2, you can use following command:

    mvn exec:java -Dexec.mainClass="mia.recommender.ch02.IREvaluatorIntro" -Dexec.args="src"

