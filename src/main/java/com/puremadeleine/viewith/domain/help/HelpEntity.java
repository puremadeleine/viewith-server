package com.puremadeleine.viewith.domain.help;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_help")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "help_id")
    Long id;

    String title;

    String content;
}
