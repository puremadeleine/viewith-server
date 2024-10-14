package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.dto.help.HelpInfoResDto;
import com.puremadeleine.viewith.dto.help.HelpListResDto;
import com.puremadeleine.viewith.provider.HelpProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class HelpServiceTest {

    HelpProvider helpProvider = mock(HelpProvider.class);
    HelpService helpService = new HelpService(helpProvider);

    @DisplayName("get help info successfully")
    @Test
    void get_help_info_successfully_test() {
        // given
        HelpEntity help = HelpEntity.builder()
                .id(1L)
                .title("제목")
                .content("<p>내용</p>")
                .build();


        when(helpProvider.getHelp(anyLong())).thenReturn(help);

        // when
        HelpInfoResDto result = helpService.getHelpInfo(1L);

        // then
        assertThat(result.getTitle()).isEqualTo("제목");
        assertThat(result.getContent()).isEqualTo("<p>내용</p>");
    }


    @DisplayName("get help no summary list successfully")
    @Test
    void get_help_no_summary_list_successfully_test() {
        // given
        HelpEntity help1 = HelpEntity.builder().id(1L).title("도움말1").content("<p>내용<p>").build();
        HelpEntity help2 = HelpEntity.builder().id(2L).title("도움말2").content("<p>내용<p>").build();

        Page<HelpEntity> helpPage = new PageImpl<>(List.of(help1, help2));

        when(helpProvider.getHelpList(anyInt(), anyInt())).thenReturn(helpPage);

        // when
        HelpListResDto result = helpService.getHelpList(1, 3, false);

        // then
        assertThat(result.getSize()).isEqualTo(2);
        assertThat(result.getHasNext()).isEqualTo(false);
        assertThat(result.getList().getFirst().getSummary()).isNull();
        assertThat(result.getList().getFirst().getContent()).isEqualTo("내용");
    }

    @DisplayName("get help summary list successfully")
    @Test
    void get_help_summary_list_successfully_test() {
        // given
        String content = "내용".repeat(100);
        HelpEntity help1 = HelpEntity.builder().id(1L).title("도움말1").content("<p>" + content + "<p>").build();
        HelpEntity help2 = HelpEntity.builder().id(2L).title("도움말2").content("<p>내용<p>").build();
        Page<HelpEntity> helpPage = new PageImpl<>(List.of(help1, help2));

        when(helpProvider.getHelpList(anyInt(), anyInt())).thenReturn(helpPage);

        // when
        HelpListResDto result = helpService.getHelpList(1, 3, true);

        // then
        assertThat(result.getSize()).isEqualTo(2);
        assertThat(result.getHasNext()).isEqualTo(false);
        assertThat(result.getList().getFirst().getContent()).isNull();
        assertThat(result.getList().getFirst().getSummary().length()).isEqualTo(30);
        assertThat(result.getList().get(1).getSummary().length()).isEqualTo("내용".length());
    }

}
