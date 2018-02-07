/**
 * SHSessionService.java 26.11.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

import lamao.soh.core.SHSessionInfo;

/**
 * Service which deals width session information
 * @author lamao
 */
public class SHSessionService {
    private static final String SESSION_DEFAULT_FILE = "session.xml";

    private static Logger LOGGER = Logger.getLogger(SHSessionService.class.getCanonicalName());

    private SHSessionInfo sessionInfo;

    private String sessionFileName = SESSION_DEFAULT_FILE;

    public SHSessionService() {}

    public SHSessionService(
                    String sessionFileName) {
        this.sessionFileName = sessionFileName;
    }

    /**
     * Get session info
     * @return session info object
     */
    public SHSessionInfo getSessionInfo() {
        if (sessionInfo == null) {
            XStream xstream = new XStream();
            xstream.processAnnotations(SHSessionInfo.class);
            try {
                sessionInfo = (SHSessionInfo) xstream
                                .fromXML(new FileInputStream(new File(sessionFileName)));
            } catch (FileNotFoundException e) {
                LOGGER.warning("No session found. New session will be created");
                sessionInfo = new SHSessionInfo();
            }
        }

        return sessionInfo;
    }

    /**
     * Save session info to file
     * @return true if session was saved successfully
     */
    public boolean saveSessionInfo() {
        XStream xstream = new XStream();
        xstream.processAnnotations(SHSessionInfo.class);
        try {
            xstream.toXML(getSessionInfo(), new FileOutputStream(new File(sessionFileName)));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return false;
        }
        return true;
    }
}
