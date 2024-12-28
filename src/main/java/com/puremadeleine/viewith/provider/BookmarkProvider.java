package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.bookmark.BookmarkEntity;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.BookmarkRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookmarkProvider {
    BookmarkRepository bookmarkRepository;

    public long countByMemberId(long memberId) {
        return bookmarkRepository.countByMemberId(memberId);
    }

    public void save(BookmarkEntity bookmarkEntity) {
        bookmarkRepository.save(bookmarkEntity);
    }

    public BookmarkEntity getBookmark(Long memberId, long seatId) {
        return findBookmark(memberId, seatId).orElseThrow(() -> new ViewithException(ViewithErrorCode.NO_BOOKMARK));
    }

    public Optional<BookmarkEntity> findBookmark(Long memberId, long seatId) {
        return bookmarkRepository.findByMember_IdAndSeat_Id(memberId, seatId);
    }

    public void deleteBookmark(BookmarkEntity bookmarkEntity) {
        bookmarkRepository.delete(bookmarkEntity);
    }
}
