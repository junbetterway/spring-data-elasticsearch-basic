package com.junbetterway.spring.data.elasticsearch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.core.SearchHits;

import com.junbetterway.spring.data.elasticsearch.model.Account;

/**
 *
 * @author junbetterway
 *
 */
public interface AccountQueryService {

	Optional<Account> findById(String id);
	
	List<Account> findByAddressContaining(String address);
	
	SearchHits<Account> findByAddressContainingViaRestTemp(String address);

	SearchHits<Account> multiFieldSearch(String query);
	
}
