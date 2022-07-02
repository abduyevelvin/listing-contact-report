package com.listing.contact.report.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheManagerConfig {
    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("contacts");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(500)
                .weakKeys()
                .recordStats());
        return cacheManager;
    }

    @Bean
    public CacheManager alternateCacheManager() {
        return new ConcurrentMapCacheManager("listings");
    }

    @Bean
    public CacheManager listingAvgCacheManager() {
        return new ConcurrentMapCacheManager("listingsAVG");
    }

    @Bean
    public CacheManager listingTopCacheManager() {
        return new ConcurrentMapCacheManager("listingsTop");
    }

    @Bean
    public CacheManager listingMakeCacheManager() {
        return new ConcurrentMapCacheManager("listingsMake");
    }

    @Bean
    public CacheManager listingTopThirtyCacheManager() {
        return new ConcurrentMapCacheManager("listingsTopThirty");
    }

    @CacheEvict(allEntries = true, cacheNames = "contacts")
    @Scheduled(fixedDelayString = "${contactsCacheFixedDelay}", initialDelayString = "${contactsInitialDelay}")
    public void reportContactCacheEvict() {
        System.out.println("Flush Contact Cache " + new Date());
    }

    @CacheEvict(allEntries = true, cacheNames = "listings", cacheManager = "alternateCacheManager")
    @Scheduled(fixedDelayString = "${listingsCacheFixedDelay}", initialDelayString = "${listingsInitialDelay}")
    public void reportListingCacheEvict() {
        System.out.println("Flush Listing Cache " + new Date());
    }

    @CacheEvict(allEntries = true, cacheNames = "listingsAVG", cacheManager = "listingAvgCacheManager")
    @Scheduled(fixedDelayString = "${listingsAVGCacheFixedDelay}", initialDelayString = "${listingsAVGInitialDelay}")
    public void reportListingAVGCacheEvict() {
        System.out.println("Flush Listing AVG Cache " + new Date());
    }

    @CacheEvict(allEntries = true, cacheNames = "listingsTop", cacheManager = "listingTopCacheManager")
    @Scheduled(fixedDelayString = "${listingsTopCacheFixedDelay}", initialDelayString = "${listingsTopInitialDelay}")
    public void reportListingTopCacheEvict() {
        System.out.println("Flush Listing Top Cache " + new Date());
    }

    @CacheEvict(allEntries = true, cacheNames = "listingsMake", cacheManager = "listingMakeCacheManager")
    @Scheduled(fixedDelayString = "${listingsMakeCacheFixedDelay}", initialDelayString = "${listingsMakeInitialDelay}")
    public void reportListingMakeCacheEvict() {
        System.out.println("Flush Listing Top Cache " + new Date());
    }

    @CacheEvict(allEntries = true, cacheNames = "listingsTopThirty", cacheManager = "listingTopThirtyCacheManager")
    @Scheduled(fixedDelayString = "${listingsTopThirtyCacheFixedDelay}", initialDelayString = "${listingsTopThirtyInitialDelay}")
    public void reportListingTopThirtyCacheEvict() {
        System.out.println("Flush Listing Top Thirty Cache " + new Date());
    }
}
