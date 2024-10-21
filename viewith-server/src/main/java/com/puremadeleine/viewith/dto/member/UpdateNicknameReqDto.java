package com.puremadeleine.viewith.dto.member;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateNicknameReqDto {
    String nickname;
}
