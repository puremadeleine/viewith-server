package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.help.HelpInfoResDto;
import com.puremadeleine.viewith.service.HelpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/help")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpController {

    final HelpService helpService;

    @GetMapping("/{help_id}")
    public HelpInfoResDto getReview(@PathVariable("help_id") Long helpId) {
        HelpInfoResDto helpInfo = helpService.getHelpInfo(helpId);
        return helpInfo;
    }

}
