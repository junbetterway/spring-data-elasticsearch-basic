package com.junbetterway.spring.data.elasticsearch.repository;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import com.junbetterway.spring.data.elasticsearch.constant.AccountConstant;
import com.junbetterway.spring.data.elasticsearch.model.Account;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author junbetterway
 *
 */
@RequiredArgsConstructor
public class AccountCustomRepositoryImpl implements AccountCustomRepository {
	
	private final ElasticsearchOperations operations;

	@Override
	public Account saveViaRestTemp(final Account account) {
		
	    IndexQuery indexQuery = new IndexQueryBuilder()
	    	    .withObject(account)
	    	    .build();
	    
	    String documentId = operations.index(indexQuery, IndexCoordinates.of(AccountConstant.INDEX_NAME));
	    	    
	    account.setId(documentId);
	    
		return account;
		
	}
	
	@Override
	public SearchHits<Account> findByAddressContainingViaRestTemp(final String address) {
		
		QueryBuilder queryBuilder = QueryBuilders
				.wildcardQuery("address", "*"+address+"*");
		
		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder)
				.build();
		
		return operations.search(searchQuery, 
				Account.class, 
				IndexCoordinates.of(AccountConstant.INDEX_NAME));
		
	}

	@Override
	public SearchHits<Account> multiFieldSearch(final String query) {
		
		QueryBuilder queryBuilder = QueryBuilders
				.multiMatchQuery(query, "name", "address", "type")
				.fuzziness(Fuzziness.AUTO);
		
		Query searchQuery = new NativeSearchQueryBuilder()
				.withFilter(queryBuilder)
				.build();
		
		return operations.search(searchQuery, 
				Account.class, 
				IndexCoordinates.of(AccountConstant.INDEX_NAME));
		
	}

}
