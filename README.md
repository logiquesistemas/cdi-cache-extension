# CDI Cache Extension
A simple extension CDI for cache method results using guava cache.

##How to use

Simply add the @TimedCache or @EternalCache annotation on method to be cached. 
It is necessary that the method has some return to be cached. CDI Cache Extension will have no effect on methods with void returns.

The @TimedCache will cache the result of the method with the specific parameters for the given time. After that time expires the next method call will
update the cache again. On the other hand , The @EternalCache will cache the result of the method forever. So , the next call to the method with the same parameters will return the result immediately.

```java
import java.util.concurrent.TimeUnit;
import br.com.logique.methodcache.annotations.TimedCache;
import br.com.logique.methodcache.annotations.EternalCache;

public class MyBean{

    @TimedCache(lifeTime = 30, unit = TimeUnit.SECONDS)
    int doSomething(String arg) {
        //do something slow
    }
    
    @EternalCache
    int calculate(int arg) {
        //calculate something slow
    }    
    
}
```

### CDI Projects

To use the bean in CDI projects simply inject the annotated bean normally. 

```java

public class CacheTest{

    @Inject
    private MyBean bean;
    
    void foo() {
        int result =  bean.doSomething("test");
    }
    
}

```
### Java SE Projects

It is also possible to use CDI Cache Extension in a non CDI project. However in this case is necessary to create the proxy instance manually:

```java

public class CacheTest{
    
    private MyBean bean = (MyBean) CacheEnhancer.newInstance(new MyBean());
    
    void foo() {
        int result =  bean.calculate(50);
    }
    
}
```

### Options 

If you need to limit the maximum number of entries in main cache you need change the value of property called "cdi-cache.max_size". 
For default the main cache is unlimited.

You can do this setting the property during startup of your application:

```java
    System.setProperty("cdi-cache.max_size",10000);
```

Or as a parameter in java exec command:
 
```java
   java -jar -Dcdi-cache.max_size=10000 MyApp.jar
``` 
    




  
 