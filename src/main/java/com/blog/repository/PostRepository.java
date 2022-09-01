package com.blog.repository;

import com.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
