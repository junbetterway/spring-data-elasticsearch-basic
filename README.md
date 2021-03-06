# Spring Data Elasticsearch - Basic
__[Elasticsearch](https://www.elastic.co/what-is/elasticsearch)__ is a distributed, free and **open search and analytics engine** for all types of data, including textual, numerical, geospatial, structured, and unstructured. Known for its simple REST APIs, distributed nature, speed, and scalability, Elasticsearch is the central component of the Elastic Stack, a set of free and open tools for data ingestion, enrichment, storage, analysis, and visualization.

__[Spring Data Elasticsearch](https://docs.spring.io/spring-data/elasticsearch/docs/4.1.5/reference/html/#reference)__ provides a simple interface to perform these operations on Elasticsearch as an alternative to using the REST APIs directly. 

The main goal of this __[repository](https://github.com/junbetterway/spring-data-elasticsearch-basic)__ is to demonstrate the basic usage of __Spring Data Elasticsearch__ in your Spring Boot project. 

## Getting started
The best way to be introduced to Elasticsearch concepts is by drawing an analogy with your RDBMS knowledge which is best summarized by below table:

| RDBMS (e.g., MySQL) | Elasticsearch | 
| ------------------- |:-------------:| 
| Table               | Index         | 
| Row                 | Document      |  
| Column              | Field         |    

Now having the above comparisons, this is how we will define our Index class - **Account**:

```
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
```

Raw data flows into Elasticsearch from a variety of sources, including logs, system metrics, and web applications. Data ingestion is the process by which this raw data is parsed, normalized, and enriched before it is indexed in Elasticsearch. Before indexing, the data is going thru normalization and enrichment (or different analyzers) depending on your declared field types.

Based from above example, we store our `address` field as __FieldType.Text__. Given this if our entry is "Barangay Longos Malabon" - the default analyzer will break up the string at the space characters and produce lowercase tokens: “barangay“, “longos”, and “malabon“. Now one can use any combination of these terms to match an **Account**.

Once indexed in Elasticsearch, users can run complex queries against their data and use aggregations to retrieve complex summaries of their data.

## Spring Data Elasticsearch Operations vs Repositories
We have two ways of accessing Elasticsearch with Spring Data:
* **Repositories**: We define methods in an interface, and Elasticsearch queries are generated from method names at runtime.
* **ElasticsearchRestTemplate**: We create queries with method chaining and native queries to have more control over creating Elasticsearch queries in relatively complex scenarios. The repository approach may not be suitable when we need granular control over how we create our queries or when the team already has expertise with Elasticsearch syntax.

For this tutorial, we will combine both and expose APIs to each for testing purposes. In addition, we created **AccountCustomRepository** and use it as the interface using the **ElasticsearchRestTemplate** then extending it by our normal Spring Data **AccountRepository** to provide abstraction on its invoking services.

## Run the needed dependencies: Elasticsearch instance

1. Make sure to install **[Docker](https://docs.docker.com/get-docker/)** on your machine
2. Go to the root directory of the project where __docker-compose.yml__ is located.
3. Run the docker compose by

```
docker-compose up
```

*__Note:__ We are using single-node discovery type here since our environment is for testing only.*

Now once done, you can test if your Elasticsearch container is up and running by invoking below URL in your browser:

```
http://localhost:9200
```

The resulting output should be somehow like this:

```
{
  "name": "c4c1874ffd75",
  "cluster_name": "docker-cluster",
  "cluster_uuid": "tER7UbBaRjyUN0XrTuisBw",
  "version": {
    "number": "7.9.3",
    "build_flavor": "default",
    "build_type": "docker",
    "build_hash": "c4138e51121ef06a6404866cddc601906fe5c868",
    "build_date": "2020-10-16T10:36:16.141335Z",
    "build_snapshot": false,
    "lucene_version": "8.6.2",
    "minimum_wire_compatibility_version": "6.8.0",
    "minimum_index_compatibility_version": "6.0.0-beta1"
  },
  "tagline": "You Know, for Search"
}
```

## Run the Spring Boot Application Using Spring Tool Suite (STS)
1. Download STS version 3.4.* (or better) from the [Spring website](https://spring.io/tools). STS is a free Eclipse bundle with many features useful for Spring developers.
2. Right-click on the project or the main application class then select "Run As" > "Spring Boot App"

## Testing time
We are kind of using CQRS here on lightweight approach by simply separating two packages (two models) for our Account class. 

1. Create API (save) - see **AccountCommandController**

* http://localhost:8080/api/account/command
* http://localhost:8080/api/account/command/restTemplate

The request body format for both contracts are just the same - sample:

```
{
    "name": "Jun King Minon",
    "balance": 12000,
    "address": "Barangay Longos Malabon",
    "type": "Savings"
}
```

The only difference is on the **AccountCommandServiceImpl** - one calling the default **repository.save(account)** while the other is our custom **repository.saveViaRestTemp(account)**. Create more for testing our search.

2. Search API (read/view) - see **AccountQueryController**

There are several query API endpoints here but let's focus on the multi-field search API endpoint:

```
 http://localhost:8080/api/account/query/filter/{searchKey}
```

This is using below custom repository I created. As can be seen below, it is very easy to perform matching on different fields. Not to mention, we can use several elasticsearch properties such as fuzziness which allows some typo during the search but still possible to match it with a search by specifying a fuzziness parameter, which allows inexact matching.

```
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
```

Go ahead try it!

Lastly, I find these two articles: __[this](https://reflectoring.io/spring-boot-elasticsearch/)__ and __[this](https://medium.com/groww-engineering/simple-search-service-using-springboot-and-elasticsearch-2-e8e856c7bc8f)__ really useful and most of my understanding (and above explanation) with regards to Spring Data Elasticsearch are best explained by these links. Kudos to the authors!

## Powered By
Contact me at [junbetterway](mailto:jkpminon12@yahoo.com)

Happy coding!!!
