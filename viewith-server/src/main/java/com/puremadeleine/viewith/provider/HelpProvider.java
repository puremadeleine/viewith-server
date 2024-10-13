package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.HelpRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_HELP;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpProvider {

    final HelpRepository helpRepository;

    public HelpEntity getHelp(Long helpId) {
        return helpRepository.findById(helpId)
                .orElseThrow(() -> new ViewithException(NO_HELP, "The help with ID " + helpId + " was not found."));
    }

    public Page<HelpEntity> getHelpList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HelpEntity> result = helpRepository.findAll(pageable);
        return result;
    }
}
