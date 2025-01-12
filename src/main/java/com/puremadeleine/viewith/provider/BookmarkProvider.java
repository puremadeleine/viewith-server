package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.repository.BookmarkRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookmarkProvider {
    BookmarkRepository bookmarkRepository;

    public long countByMemberId(long memberId) {
        return bookmarkRepository.countByMemberId(memberId);
    }

    public boolean isBookmarked(Long seatId, Long memberId) {
        return bookmarkRepository.existsBySeatIdAndMemberId(seatId, memberId);
    }
}
