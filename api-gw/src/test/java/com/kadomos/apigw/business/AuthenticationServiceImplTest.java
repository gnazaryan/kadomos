package com.kadomos.apigw.business;

import com.kadomos.apigw.util.Convenience;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationServiceImplTest {

    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImplTest.class);

	@Autowired
	private AuthenticationService authenticationService;

	@Test
	public void authenticateTest() throws Exception {
        String sessionId = authenticationService.authenticate("root", "kadomos");
        assertTrue(Convenience.hasValue(sessionId));
        Boolean authenticated = true;
        try {
            authenticationService.authenticate("root", "X");
        } catch (Exception ex) {
            authenticated = false;
        }
        assertFalse(authenticated);
	}

    @Test
    public void isAuthenticatedTest() throws Exception {
        Boolean isAuthenticated = authenticationService.isAuthenticated("sessionId");
        assertFalse(isAuthenticated);
        String sessionId = authenticationService.authenticate("root", "kadomos");
        isAuthenticated = authenticationService.isAuthenticated(sessionId);
        assertTrue(isAuthenticated);
    }
}
