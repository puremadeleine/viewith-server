package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.repository.HelpRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HelpProviderTest {

    HelpRepository helpRepository = mock(HelpRepository.class);

    HelpProvider helpProvider = new HelpProvider(helpRepository);

    @DisplayName("return the helpEntity when the help is successfully returned")
    @Test
    void return_helpEntity_successfully_test() {
        // given
        HelpEntity help = HelpEntity.builder()
                .id(1L)
                .title("제목")
                .content("<p>내용</p>")
                .build();

        when(helpRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(help));

        // when
        HelpEntity result = helpProvider.getHelp(1L);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @DisplayName("return the page helpEntity when the help list is successfully returned")
    @Test
    void return_page_helpEntity_successfully_test() {
        // given
        int page = 1;
        int size = 2;
        HelpEntity help1 = HelpEntity.builder().id(1L).title("도움말1").content("내용").build();
        HelpEntity help2 = HelpEntity.builder().id(2L).title("도움말2").content("내용").build();
        Page<HelpEntity> helpPage = new PageImpl<>(List.of(help1, help2));

        when(helpRepository.findAll(any(Pageable.class)))
                .thenReturn(helpPage);

        // when
        Page<HelpEntity> result = helpProvider.getHelpList(page, size);

        // then
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo("도움말1");
    }


}