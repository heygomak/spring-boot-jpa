package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jpabook.jpashop.domain.Address;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        // new MemberForm() 빈 객체 넘겨주는 목적은 validation check 사용될 수 있음
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result) {
        // 파라미터로 엔터티를 직접 넘기지 않는 이유는
        // 엔터티는 핵심 비즈니스 로직에만 순수하게 의존해야하며 화면에 의존되면 안된다.

        if(result.hasErrors()) {
            // thymeleaf 태그에서 validation 해당 에러메시지 출럭 가능
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setAddress(new Address(form.getCity(), form.getStreet(), form.getZipcode()));
        memberService.join(member);
        return "redirect:/";    // home 으로 이동
    }

    @GetMapping("/members")
    public String list (Model model) {
        // 실무에서는 엔터티를 그대로 넘기지 말고 DTO를 만들어서
        // 화면에 꼭 필요한 정보들만 넘길것을 권장 (API 개발시에는 엔터티 리턴 절대 안됨)
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("members",  memberList);
        return "members/memberList";
    }
}
