package com.shop.repository;

import com.shop.entity.Board;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Item> , BoardRepositoryCustom{
}

