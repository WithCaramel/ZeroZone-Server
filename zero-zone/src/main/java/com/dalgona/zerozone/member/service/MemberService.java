package com.dalgona.zerozone.member.service;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.member.domain.MemberRepository;
import com.dalgona.zerozone.member.domain.RefreshToken;
import com.dalgona.zerozone.member.dto.MemberSaveRequestDto;
import com.dalgona.zerozone.security.JwtProvider;
import com.dalgona.zerozone.security.dto.MemberInfoResponseDto;
import com.dalgona.zerozone.security.dto.TokenDto;
import com.dalgona.zerozone.security.dto.TokenRequestDto;
import com.dalgona.zerozone.security.exception.AuthErrorCode;
import com.dalgona.zerozone.security.exception.AuthException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;
    private final EmailAuthService emailAuthService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public Boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void register(MemberSaveRequestDto memberSaveRequestDto) throws BadRequestException {
        String email = memberSaveRequestDto.getEmail();
        checkEmailDuplicateThenThrow(email);
        emailAuthService.checkEmailAuthed(email);
        Member member = memberSaveRequestDto.toEntity(passwordEncoder);
        memberRepository.save(member);
    }

    private void checkEmailDuplicateThenThrow(String email) throws BadRequestException {
        if(checkEmailDuplicate(email))
            throw new BadRequestException(BadRequestErrorCode.DUPLICATED, "해당 이메일로 이미 회원가입했습니다.");
    }

    @Transactional
    public TokenDto login(String email, String password) {
        Member member = findMemberOrElseThrow(email);
        checkPassword(password, member.getPassword());
        return createTokenDtoAndUpdateRefreshTokenValue(member);
    }

    private TokenDto createTokenDtoAndUpdateRefreshTokenValue(Member signUpSuccessMember) throws AuthException {
        String accessToken = jwtProvider.createAccessToken(signUpSuccessMember.getId(), signUpSuccessMember.getRoles());
        String refreshToken = refreshTokenService.updateRefreshToken(signUpSuccessMember);
        return new TokenDto(accessToken, refreshToken);
    }

    private void checkPassword(String requestPassword, String encodedOriginPassword) {
        boolean isNotCorrectPassword = !(passwordEncoder.matches(requestPassword, encodedOriginPassword));
        if(isNotCorrectPassword) throw new BadRequestException(BadRequestErrorCode.NOT_MATCHES, "비밀번호가 일치하지 않습니다.");
    }

    private Member findMemberOrElseThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) throws AuthException {
        String requestedAccessToken = tokenRequestDto.getAccessToken();
        String requestedRefreshToken = tokenRequestDto.getRefreshToken();

        Member requestedMember = findMemberOrElseThrow(getRequestedUserIdFromAccessToken(requestedAccessToken));
        RefreshToken foundRefreshToken = refreshTokenService.findRefreshTokenByMemberOrElseThrows(requestedMember);
        checkRequestedRefreshTokenMatchesToFoundRefreshToken(requestedRefreshToken, foundRefreshToken);

        String accessToken = jwtProvider.createAccessToken(requestedMember.getId(), requestedMember.getRoles());
        String refreshToken = refreshTokenService.updateRefreshToken(requestedMember);

        return new TokenDto(accessToken, refreshToken);
    }

    private Member findMemberOrElseThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));
    }


    private Long getRequestedUserIdFromAccessToken(String requestedAccessToken) {
        try {
            return Long.parseLong(jwtProvider.getAuthentication(requestedAccessToken).getName());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
    }

    private void checkRequestedRefreshTokenMatchesToFoundRefreshToken(String requestedRefreshToken, RefreshToken foundRefreshToken) {
        boolean isNotValid = true;
        try {
            isNotValid = !foundRefreshToken.getRefreshTokenValue().equals(jwtProvider.getRefreshTokenValue(requestedRefreshToken));
        } catch (ExpiredJwtException e){
            log.error(e.getMessage());
            throw new AuthException(AuthErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AuthException(AuthErrorCode.WRONG_TOKEN);
        }
        if (isNotValid)
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

    @Transactional
    public MemberInfoResponseDto getInfo(Member member) {
        return MemberInfoResponseDto.of(member);
    }

    @Transactional
    public void updateName(Member member, String newName) {
        memberRepository.save(member.updateName(newName));
    }

    @Transactional
    public void updatePassword(String email, String newPassword) {
        Member member = findMemberOrElseThrow(email);
        emailAuthService.checkEmailAuthed(member.getEmail());
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        memberRepository.save(member.updatePassword(encodedNewPassword));
    }
}
