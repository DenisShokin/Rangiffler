package org.rangiffler.jupiter.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.rangiffler.api.context.CookieContext;
import org.rangiffler.api.context.SessionContext;

public class ClearCookiesAndSessionExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        CookieContext.getInstance().release();
        SessionContext.getInstance().release();
    }

}
