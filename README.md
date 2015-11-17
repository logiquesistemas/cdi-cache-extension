# CDI Cache Extension
An extension CDI for cache the results of the methods

##How to use

Simply add the Cacheable annotation above the method and
inject the bean normally.

```java

import java.util.concurrent.TimeUnit;
import br.com.logique.methodcache.Cacheable;

public class MyBean{

    @Cacheable(lifeTime = 30, unit = TimeUnit.SECONDS)
    void doSomething(String arg) {
        //do something slow
    }

}

```
