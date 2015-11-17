package br.com.logique.methodcache;

import junit.framework.Assert;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.profiler.Profiler;

import javax.inject.Inject;

/**
 * Created by Gustavo on 17/11/2015.
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(InterfaceProxyTestImpl.class)
public class CDICacheTest {

    @Inject
    private InterfaceProxyTestImpl realImplementation;

    @Test
    public void cdiInjection() {
        Assert.assertNotNull("CDI injection test", realImplementation);
    }

}
