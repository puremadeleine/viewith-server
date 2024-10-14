package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.help.HelpInfoResDto;
import com.puremadeleine.viewith.dto.help.HelpListResDto;
import com.puremadeleine.viewith.service.HelpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public HelpListResDto getReview(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "is_summary", required = false, defaultValue = "false") Boolean isSummary) {
        return helpService.getHelpList(page, size, isSummary);
    }

}
