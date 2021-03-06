package com.junbetterway.spring.data.elasticsearch.service;

import com.junbetterway.spring.data.elasticsearch.dto.CreateAccountCommand;
import com.junbetterway.spring.data.elasticsearch.model.Account;

/**
 *
 * @author junbetterway
 *
 */
public interface AccountCommandService {

	Account create(CreateAccountCommand command);
	
	Account createViaRestTemplate(CreateAccountCommand command);
	
}
