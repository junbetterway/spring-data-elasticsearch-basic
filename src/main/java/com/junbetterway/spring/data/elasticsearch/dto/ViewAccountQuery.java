package com.junbetterway.spring.data.elasticsearch.dto;

import com.junbetterway.spring.data.elasticsearch.model.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author junbetterway
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewAccountQuery {
	
	private String id;
	private String name;
    private Double balance;
	private String address;
	private String type;
	
	public static ViewAccountQuery from(final Account account) {
		
		return ViewAccountQuery.builder()
				.id(account.getId())
				.name(account.getName())
				.balance(account.getBalance())
				.address(account.getAddress())
				.type(account.getType())
				.build();
	}
	
}
