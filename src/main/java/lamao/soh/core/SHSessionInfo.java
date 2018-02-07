/**
 * SHSession.java 26.11.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Session information like last selected user.
 * @author lamao
 */
@XStreamAlias("session")
public class SHSessionInfo {
    @XStreamAlias("last-selected-profile")
    private String lastSelectedUsername;

    public String getLastSelectedUsername() {
        return lastSelectedUsername;
    }

    public void setLastSelectedUsername(String lastSelectedUsername) {
        this.lastSelectedUsername = lastSelectedUsername;
    }

}
