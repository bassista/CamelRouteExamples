package org.philcluff.route;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.philcluff.handler.ExampleHandler;

import java.io.IOException;

public class MultipleExceptionHandlingRouteBuilder extends RouteBuilder {

    @EndpointInject(ref="endpoint-in") protected Endpoint in;
    @EndpointInject(ref="endpoint-out") protected Endpoint out;
    @EndpointInject(ref="endpoint-io-error") protected Endpoint ioError;
    @EndpointInject(ref="endpoint-other-error") protected Endpoint error;

    private ExampleHandler handler;

    public MultipleExceptionHandlingRouteBuilder(ExampleHandler handler) {
        this.handler = handler;
    }

    public void configure() {

        // Most specific Exception Handler catches first.
        onException(IOException.class)
                .handled(true)
                .to(ioError);

        // All Exceptions which aren't IOException, or a direct subclass thereof,
        // fall through to the Exception handler below.
        onException(Throwable.class)
                .handled(true)
                .to(error);

        from(in)
            .bean(handler)
            .to(out);
    }

}
