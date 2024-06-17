package com.cineverse.cineversebackend.user.member.service;

import com.cineverse.cineversebackend.user.member.dto.MemberDTO;
import com.cineverse.cineversebackend.user.member.entity.Member;
import com.cineverse.cineversebackend.user.member.repo.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(ModelMapper modelMapper, MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
    }

    /* 설명. 회원가입 */
    @Override
    @Transactional
    public void registMember(Member member) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = format.format(date);

        member.setJoinDate(newDate);
        member.setMemberStatus("Y");
        member.setGradeId(1);
        memberRepository.save(member);
    }

    /* 설명. 로그인 */
    @Override
    public Member sessionLogin(String userId) {
        return memberRepository.findByUserId(userId).orElse(null);
    }

    /* 설명. 회원가입 */
    @Override
    public Member deleteMember(int memberId) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = format.format(date);

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) {
            throw new EntityNotFoundException("존재하지 않는 회원입니다.");
        }

        Member member = optionalMember.get();

        member.setWithdrawalDate(newDate);

        return memberRepository.save(member);
    }

    /* 설명. 회원 수정 */
    @Override
    public Member modifyMember(int memberId, MemberDTO modifyMember) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(!optionalMember.isPresent()) {
            throw new EntityNotFoundException("존재하지 않는 회원입니다.");
        }

        Member member = optionalMember.get();

        if (modifyMember.getMemberEmail() != null) {
            member.setMemberEmail(modifyMember.getMemberEmail());
        }
        if (modifyMember.getMemberNumber() != null) {
            member.setMemberNumber(modifyMember.getMemberNumber());
        }
        if (modifyMember.getNickname() != null) {
            member.setNickname(modifyMember.getNickname());
        }
        if (modifyMember.getUserPassword() != null) {
            member.setUserPassword(modifyMember.getUserPassword());
        }

        return memberRepository.save(member);
    }

    /* 설명. 회원 단일조회 */
    @Override
    public MemberDTO findMemberById(int memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾지 못했습니다"));

        return modelMapper.map(member, MemberDTO.class);
    }

    @Override
    public void changePwd(String newPwd, String to) {
        Member member = memberRepository.findByMemberEmail(to);
        member.setUserPassword(newPwd);

        memberRepository.save(member);
    }

    @Override
    public Member findUserId(MemberDTO memberDTO) {
        Member member = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        return member;
    }
}
