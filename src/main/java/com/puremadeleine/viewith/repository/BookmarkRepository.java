package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.bookmark.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

}
