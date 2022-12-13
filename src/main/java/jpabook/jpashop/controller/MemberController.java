package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jpabook.jpashop.domain.Adress;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/new")
    public String createForm(Model model) {
        // new MemberForm() 빈 객체 넘겨주는 목적은 validation check 사용될 수 있음
        model.addAttribute("memberForm", new MemberForm());
        return "member/createMemberForm";
    }

    @PostMapping("/member/new")
    public String createMember(@Valid MemberForm form) {
        Member member = new Member();
        member.setUsername(form.getName());
        member.setAdress(new Adress(form.getCity(), form.getStreet(), form.getZipcode()));
        memberService.join(member);
        return "redirect:/";    // home 으로 이동
    }
}
