/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.web;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.web.rest.MotorCycleRidePlannerRestImpl;

/**
 * see
 * http://ashismo.github.io/java-web%20service/2015/05/13/RESTful-webservice-with-Basic-HTTP-authentication
 *
 * @author gallenc
 */
public class RestAuthenticationFilter implements javax.servlet.Filter {
    private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationFilter.class);

    public static final String AUTHENTICATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filter) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);

            // You can implement dependancy injection here
            AuthenticationService authenticationService = new AuthenticationService();

            boolean authenticationStatus = authenticationService.authenticate(authCredentials);

            if (authenticationStatus) {
                LOG.debug("authorised returning authorised reponse");
                filter.doFilter(request, response);
            } else {
                LOG.debug("unauthorised returning unauthorised reponse");
                if (response instanceof HttpServletResponse) {
                    LOG.debug("marking response unauthorised");
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
