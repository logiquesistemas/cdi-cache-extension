package br.com.logique.methodcache;

/**
 * Locate Service for supplier cache.
 *
 * Created by Gustavo Leitão on 19/11/2015.
 */
public class SupplierLocateService {

    /**
     * Get supplier cache to be used.
     * @return supplier cache
     */
    public static SupplierCache getSupplierCache(){
        return new SupplierCacheImpl();
    }

}
