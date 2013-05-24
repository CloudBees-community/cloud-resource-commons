This is a Java client library to develop CloudBees Cloud Resource Provider.

Using the code
==============
To use this library, add the following dependency to your Maven POM:

    <dependency>
      <groupId>com.cloudbees</groupId>
      <artifactId>cloudbees-api-client</artifactId>
      <version>0.1-SNAPSHOT</version>
    </dependency>




TODO
====
Split this into multiple modules with different assumptions.
For example, the types packag has almost no external dependency and usable everywhere,
but the model package currently has some JPA annotations.