package com.junbetterway.spring.data.elasticsearch.repository;

import org.springframework.data.elasticsearch.core.SearchHits;

import com.junbetterway.spring.data.elasticsearch.model.Account;

/**
 *
 * @author junbetterway
 *
 */
public interface AccountCustomRepository {
	
	Account saveViaRestTemp(Account account);
	
	SearchHits<Account> findByAddressContainingViaRestTemp(String address);
	
	SearchHits<Account> multiFieldSearch(String query);
	
}
