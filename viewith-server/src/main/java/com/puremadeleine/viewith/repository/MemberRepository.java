package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.dto.member.OAuthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByIdAndDeleteYn(Long id, boolean deleteYn);

    Optional<MemberEntity> findByOauthTypeAndOauthUserId(OAuthType oauthType, Long oauthUserId);
}