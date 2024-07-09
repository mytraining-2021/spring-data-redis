package com.example.redis.service;

import com.example.redis.entity.Product;
import com.example.redis.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void clearCacheOnStartup() {
        cacheManager.getCache("products").clear();
        logger.debug("Cache cleared on application startup");
    }

    @CacheEvict(value = "products", allEntries = true)
    public void addProducts(List<Product> products) {
        productRepository.saveAll(products);
        logger.debug("Products saved to the database and cache invalidated");
    }

    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        log.info("Fetching products from the DB Call");
        List<Product> products = productRepository.findAll();
        log.info("Products fetched from the database");
        return products;
    }

    @Scheduled(fixedRate = 900000) // 15 minutes in milliseconds
    public void refreshCache() {
        logger.debug("Refreshing cache by fetching data from the database");
        List<Product> products = productRepository.findAll();
        cacheManager.getCache("products").put("products", products);
        logger.debug("Cache refreshed with data from the database");
    }
}
