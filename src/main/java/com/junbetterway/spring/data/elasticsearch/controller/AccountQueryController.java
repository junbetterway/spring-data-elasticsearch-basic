package com.junbetterway.spring.data.elasticsearch.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.junbetterway.spring.data.elasticsearch.dto.ViewAccountQuery;
import com.junbetterway.spring.data.elasticsearch.model.Account;
import com.junbetterway.spring.data.elasticsearch.service.AccountQueryService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author junbetterway
 *
 */
@RequestMapping("api/account/query")
@RestController
@RequiredArgsConstructor
public class AccountQueryController {

	private final AccountQueryService queryService;
	
	@GetMapping("{id}")
	public ResponseEntity<ViewAccountQuery> readById(@PathVariable String id) {
		
		return queryService.findById(id)
			.map(account -> new ResponseEntity<>(ViewAccountQuery.from(account), HttpStatus.OK))
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
	}	
	
	@GetMapping("address/{searchKey}")
	public ResponseEntity<List<ViewAccountQuery>> searchByAddress(@PathVariable String searchKey) {
		
		List<ViewAccountQuery> accounts = queryService.findByAddressContaining(searchKey).stream()
				.map(account -> ViewAccountQuery.from(account))
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(accounts, HttpStatus.OK);
		
	}
	
	@GetMapping("address/restTemplate/{searchKey}")
	public ResponseEntity<SearchHits<Account>> searchByAddressUsingRestTemp(@PathVariable String searchKey) {
		
		SearchHits<Account> accounts = queryService.findByAddressContainingViaRestTemp(searchKey);
		
		return new ResponseEntity<>(accounts, HttpStatus.OK);
		
	}
	
	@GetMapping("filter/{searchKey}")
	public ResponseEntity<SearchHits<Account>> filter(@PathVariable String searchKey) {
		
		SearchHits<Account> accounts = queryService.multiFieldSearch(searchKey);
		
		return new ResponseEntity<>(accounts, HttpStatus.OK);
		
	}
	
}
