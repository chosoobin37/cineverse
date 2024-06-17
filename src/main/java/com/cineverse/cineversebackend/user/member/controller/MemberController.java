package com.cineverse.cineversebackend.user.member.controller;

import com.cineverse.cineversebackend.user.member.dto.MemberDTO;
import com.cineverse.cineversebackend.user.member.entity.Member;
import com.cineverse.cineversebackend.user.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;
    private ModelMapper modelMapper;

    @Autowired
    public MemberController(MemberService memberService, ModelMapper modelMapper) {
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    /* 설명. 회원 생성 */
    @PostMapping("/regist")
    private ResponseEntity<Member> registMember(@RequestBody MemberDTO memberDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Member member = modelMapper.map(memberDTO, Member.class);
        memberService.registMember(member);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 설명. 로그인 */
    @PostMapping("/login")
    public ResponseEntity<String> sessionLogin(@RequestBody MemberDTO memberDTO, HttpSession session) {
        Member member = memberService.sessionLogin(memberDTO.getUserId());

        if (member != null && member.getUserPassword().equals(memberDTO.getUserPassword())) {
            session.setAttribute("member", member);
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    @PostMapping("/logout")
    private ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    /* 설명. 회원 탈퇴 */
    @PatchMapping("/delete/{memberId}")
    public ResponseEntity<Member> deleteMember(@PathVariable int memberId) {
        return ResponseEntity.ok(memberService.deleteMember(memberId));
    }

    /* 설명. 회원 수정 */
    @PatchMapping("/modify/{memberId}")
    public ResponseEntity<Member> modifyMember(@RequestBody MemberDTO memberDTO ,@PathVariable int memberId) {
        return ResponseEntity.ok(memberService.modifyMember(memberId, memberDTO));
    }

    /* 설명. 회원 단일 조회 */
    @GetMapping("/{memberId}")
    public MemberDTO findMemberById(@PathVariable int memberId) {
        MemberDTO member = memberService.findMemberById(memberId);

        return member;
    }

    /* 설명. 회원 아이디 찾기 */
    @PostMapping("/find_id")
    public ResponseEntity<String> findUserId(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.findUserId(memberDTO);
        if (member == null) {
            return ResponseEntity.ok("존재하지 않는 이메일입니다.");
        }

        return ResponseEntity.ok(member.getUserId());
    }

}
