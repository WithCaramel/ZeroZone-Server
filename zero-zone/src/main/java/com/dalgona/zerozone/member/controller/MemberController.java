package com.dalgona.zerozone.member.controller;

import com.dalgona.zerozone.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{email}")
    public Boolean checkEmailDuplicate(@PathVariable String email){
        return memberService.checkEmailDuplicate(email);
    }



}
