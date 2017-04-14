package com.Davi.poems.net;

import org.mortbay.jetty.security.Constraint;
import org.mortbay.jetty.security.ConstraintMapping;
import org.mortbay.jetty.security.SecurityHandler;
import org.mortbay.jetty.servlet.Context;

// Most of the code in this class is copied from HBASE-10473

/**
 * Utility class to impose constraints on Jetty HTTP servers
 */

public class HTTPServerConstraintUtil {

    private HTTPServerConstraintUtil() {

    }

    /**
     * Impose constraints on the {@linkplain org.mortbay.jetty.servlet.Context}
     * passed in.
     * @param ctx - {@linkplain org.mortbay.jetty.servlet.Context} to impose
     *            constraints on.
     */
    public static void enforceConstraints(Context ctx) {
        Constraint c = new Constraint();
        c.setAuthenticate(false);

        ConstraintMapping cmt = new ConstraintMapping();
        cmt.setConstraint(c);
        cmt.setMethod("TRACE");
        cmt.setPathSpec("/*");

        ConstraintMapping cmo = new ConstraintMapping();
        cmo.setConstraint(c);
        cmo.setMethod("OPTIONS");
        cmo.setPathSpec("/*");

        SecurityHandler sh = new SecurityHandler();
        sh.setConstraintMappings(new ConstraintMapping[]{cmt, cmo});
        ctx.addHandler(sh);
    }
}