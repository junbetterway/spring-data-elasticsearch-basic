package com.junbetterway.spring.data.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.junbetterway.spring.data.elasticsearch.model.Account;

/**
 *
 * @author junbetterway
 *
 */
public interface AccountRepository extends ElasticsearchRepository<Account, String>, AccountCustomRepository {

	List<Account> findByAddressContaining(String address);
	  
}
