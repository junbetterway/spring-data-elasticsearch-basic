package com.junbetterway.spring.data.elasticsearch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import com.junbetterway.spring.data.elasticsearch.model.Account;
import com.junbetterway.spring.data.elasticsearch.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author junbetterway
 *
 */
@Service
@RequiredArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {

	private final AccountRepository repository;
	
	@Override
	public Optional<Account> findById(final String id) {
		return repository.findById(id);
	}

	@Override
	public List<Account> findByAddressContaining(final String address) {
		return repository.findByAddressContaining(address);
	}

	@Override
	public SearchHits<Account> findByAddressContainingViaRestTemp(final String address) {
		return repository.findByAddressContainingViaRestTemp(address);
	}

	@Override
	public SearchHits<Account> multiFieldSearch(final String query) {
		return repository.multiFieldSearch(query);
	}

}
