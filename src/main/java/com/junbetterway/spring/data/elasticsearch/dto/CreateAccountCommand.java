package com.junbetterway.spring.data.elasticsearch.dto;

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
public class CreateAccountCommand {
	
	private String name;
    private Double balance;
	private String address;
	private String type;
	
}
