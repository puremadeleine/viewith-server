package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.help.HelpInfoResDto;
import com.puremadeleine.viewith.dto.help.HelpListResDto;
import com.puremadeleine.viewith.service.HelpService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    public HelpInfoResDto getHelp(@PathVariable("help_id") Long helpId) {
        return helpService.getHelpInfo(helpId);
    }

    @GetMapping("/list")
    public HelpListResDto getHelpList(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(20) Integer size,
            @RequestParam(value = "is_summary", required = false, defaultValue = "false") Boolean isSummary) {
        return helpService.getHelpList(page, size, isSummary);
    }

}
