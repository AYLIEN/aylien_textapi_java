About
=====

This is the Java client library for AYLIEN's APIs. If you haven't already done so, you will need to [sign up](https://developer.aylien.com/signup).

Installation
============

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.aylien.textapi</groupId>
  <artifactId>client</artifactId>
  <version>0.7.0</version>
</dependency>
```

### sbt users

Add this to your `libraryDependencies`:

    "com.aylien.textapi" % "client" % "0.7.0"

See the [Developers Guide](https://developer.aylien.com/docs) for additional documentation.

Example
=======

```java
import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.*;
import com.aylien.textapi.responses.*;
import java.net.URL;

class Example {
  public static void main(String[] args) throws Exception {
    TextAPIClient client = new TextAPIClient("YourApplicationId", "YourApplicationKey");
    ConceptsParams.Builder builder = ConceptsParams.newBuilder();
    builder.setUrl(new URL("http://www.bbc.com/news/science-environment-30097648"));
    Concepts concepts = client.concepts(builder.build());
    System.out.println(concepts.getText());
    for (Concept c: concepts.getConcepts()) {
      System.out.println(c.getUri());
      for (SurfaceForm sf: c.getSurfaceForms()) {
        System.out.println(sf.getString());
      }
    }
  }
}
```
