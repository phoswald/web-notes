package phoswald.webnotes.framework;

import javax.ws.rs.core.CacheControl;

public class BaseService {

    protected static final String ATTRIBUTE_USER = "userid";

    protected static CacheControl getNoCache() {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        return cc;
    }
}
