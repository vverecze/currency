package com.vverecze.service;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Viki
 */
@Singleton
@Startup
public class ScheduledTaskRunner {
    private ParserInterface parser;
    
    public ScheduledTaskRunner() {}
    
    @Inject
    public ScheduledTaskRunner(ParserInterface parser) {
        this.parser = parser;
    }
    
    @PostConstruct
    public void init() {
        parser.processCurrencyXml();
    }

    public ParserInterface getParser() {
        return parser;
    }

    public void setParser(ParserInterface parser) {
        this.parser = parser;
    }
}
