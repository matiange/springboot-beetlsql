package com.matiange.shiro;

import org.apache.shiro.session.Session;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class SecurityRedisSessionDAO extends RedisSessionDAO {
	private static final Logger logger = LoggerFactory.getLogger(SecurityRedisSessionDAO.class);

	@Override
	protected Serializable generateSessionId(Session session) {
		Serializable sessionId;
		if(session.getId()!= null) {
			sessionId = session.getId();
		}else {
			sessionId = getSessionIdGenerator().generateId(session);
		}
		logger.info("Shiro generated session id = " + sessionId);
		return sessionId;
	}

}
