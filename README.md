This is a Java client library to develop CloudBees Cloud Resources. For details on Cloud Resource, see the [Cloud Resource specification] (https://docs.google.com/document/d/1SjM36eXu4sy5HeTMzKouy1AWcBlJkBIPx2vmnflFacE/edit?usp=sharing)

This project currently has two modules:

* *core* defines [Jackson](http://jackson.codehaus.org/) based serialization of common cloud resource types and mechanism to define and annotate custom cloud resource types as Java interfaces.
* *jaxrs-jpa* defines additional code useful for building cloud resources on top of JAX-RS and JPA.

Using the code
==============
To use this library, add the following dependency to your Maven POM:

    <dependency>
      <groupId>com.cloudbees.cloud_resource</groupId>
      <artifactId>cloud-resource-commons</artifactId>
      <version>...</version>
    </dependency>

For using JAX-RS/JPA module, use the artifact ID `cloud-resource-commons-jaxrs-jpa`.
