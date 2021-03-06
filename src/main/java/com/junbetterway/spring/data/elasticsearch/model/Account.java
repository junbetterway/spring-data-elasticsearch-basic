package com.junbetterway.spring.data.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.junbetterway.spring.data.elasticsearch.constant.AccountConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author junbetterway
 *
 */
@Document(indexName = AccountConstant.INDEX_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

	@Id
	private String id;
	
    @Field(type = FieldType.Text, name = "name")
	private String name;
	
	@Field(type = FieldType.Double, name = "balance")
    private Double balance;

	@Field(type = FieldType.Text, name = "address")
	private String address;
	
	@Field(type = FieldType.Keyword, name = "type")
	private String type;
	
}
