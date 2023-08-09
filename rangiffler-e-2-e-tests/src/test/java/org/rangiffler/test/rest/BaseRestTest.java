package org.rangiffler.test.rest;

import org.rangiffler.jupiter.annotation.meta.RestTest;

@RestTest
public class BaseRestTest {

    protected static final String ID_REGEXP = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

}
