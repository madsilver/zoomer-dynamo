package com.silver.zoomer.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.silver")
public class DynamoDBConfig {
    @Value("${aws.region}")
    private String region;
    @Value("${aws.dynamodb.url}")
    private String url;
    @Value("${aws.dynamodb.access-key}")
    private String accessKey;
    @Value("${aws.dynamodb.secret-key}")
    private String secretKey;

    @Bean(name = "amazonDynamoDB")
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(this.getCredentialsProvider())
                .withEndpointConfiguration(this.getEndpointConfiguration(this.url))
                .build();
    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration(final String url) {
        return new AwsClientBuilder.EndpointConfiguration(url, this.region);
    }

    private AWSStaticCredentialsProvider getCredentialsProvider() {
        return new AWSStaticCredentialsProvider(this.getBasicAWSCredentials());
    }

    private BasicAWSCredentials getBasicAWSCredentials() {
        return new BasicAWSCredentials(this.accessKey, this.secretKey);
    }
}
