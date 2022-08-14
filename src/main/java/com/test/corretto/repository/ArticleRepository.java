package com.test.corretto.repository;

import com.test.corretto.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

}
