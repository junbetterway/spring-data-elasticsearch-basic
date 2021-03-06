package com.junbetterway.spring.data.elasticsearch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junbetterway.spring.data.elasticsearch.dto.CreateAccountCommand;
import com.junbetterway.spring.data.elasticsearch.model.Account;
import com.junbetterway.spring.data.elasticsearch.service.AccountCommandService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author junbetterway
 *
 */
@RequestMapping("api/account/command")
@RestController
@RequiredArgsConstructor
public class AccountCommandController {

	private final AccountCommandService commandService;
	
	@PostMapping
	public ResponseEntity<String> createViaRepository(@RequestBody CreateAccountCommand request) {
		Account account = commandService.create(request);
		return new ResponseEntity<>(account.getId(), HttpStatus.CREATED);
	}

	@PostMapping("restTemplate")
	public ResponseEntity<String> createViaRestTemplate(@RequestBody CreateAccountCommand request) {
		Account account = commandService.createViaRestTemplate(request);
		return new ResponseEntity<>(account.getId(), HttpStatus.CREATED);
	}
	
}
