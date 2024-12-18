package com.puremadeleine.viewith.domain.image;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_image")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    Long id;

    String imageUrl;
    Long sourceId;

    @Enumerated(EnumType.STRING)
    SourceType sourceType;
}
