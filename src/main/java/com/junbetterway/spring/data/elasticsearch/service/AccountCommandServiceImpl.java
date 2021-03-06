package com.junbetterway.spring.data.elasticsearch.service;

import org.springframework.stereotype.Service;

import com.junbetterway.spring.data.elasticsearch.dto.CreateAccountCommand;
import com.junbetterway.spring.data.elasticsearch.model.Account;
import com.junbetterway.spring.data.elasticsearch.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author junbetterway
 *
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AccountCommandServiceImpl implements AccountCommandService {

	private final AccountRepository repository;
	
	@Override
	public Account create(final CreateAccountCommand command) {

		log.info("[Repository] Processing {}", command);
		
		Account account = Account.builder()
				.name(command.getName())
				.balance(command.getBalance())
				.address(command.getAddress())
				.type(command.getType())
				.build();
		
		return repository.save(account);
		
	}

	@Override
	public Account createViaRestTemplate(final CreateAccountCommand command) {

		log.info("[Rest Template] Processing {}", command);
		
		Account account = Account.builder()
				.name(command.getName())
				.balance(command.getBalance())
				.address(command.getAddress())
				.type(command.getType())
				.build();
		
		return repository.saveViaRestTemp(account);
		
	}

}
