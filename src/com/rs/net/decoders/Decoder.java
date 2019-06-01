package com.rs.net.decoders;

import com.rs.io.InputStream;
import com.rs.net.Session;

public abstract class Decoder {

    public Session session;

    public Decoder(Session session) {
    	this.session = session;
    }

    public abstract void decode(Session session, InputStream stream);

}
