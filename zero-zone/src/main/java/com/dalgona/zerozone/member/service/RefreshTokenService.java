package com.dalgona.zerozone.member.service;


import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.member.domain.RefreshToken;
import com.dalgona.zerozone.member.domain.RefreshTokenRepository;
import com.dalgona.zerozone.security.JwtProvider;
import com.dalgona.zerozone.security.exception.AuthErrorCode;
import com.dalgona.zerozone.security.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public String updateRefreshToken(Member member) throws AuthException{
        String refreshTokenValue = createRefreshTokenValue();
        String refreshToken = jwtProvider.createRefreshToken(refreshTokenValue);
        saveOrUpdateRefreshTokenValue(member, refreshTokenValue);
        return refreshToken;
    }

    private String createRefreshTokenValue() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void saveOrUpdateRefreshTokenValue(Member foundMember, String refreshTokenValue) {
        if(checkRefreshTokenExists(foundMember.getId()))
            updateRefreshTokenIfRefreshTokenValueExists(foundMember, refreshTokenValue);
        else
            saveRefreshTokenWithTokenValue(refreshTokenValue, foundMember.getId());
    }

    private boolean checkRefreshTokenExists(Long memberPk) {
        return refreshTokenRepository.existsByMemberPk(memberPk);
    }

    private void updateRefreshTokenIfRefreshTokenValueExists(Member foundMember, String refreshTokenValue) {
        if(refreshTokenRepository.existsByMemberPk(foundMember.getId())) {
            RefreshToken foundRefreshToken = findRefreshTokenByMemberOrElseThrows(foundMember);
            updateRefreshTokenWithNewRefreshTokenValue(foundRefreshToken, refreshTokenValue);
        }
    }

    public RefreshToken findRefreshTokenByMemberOrElseThrows(Member requestedMember) {
        return refreshTokenRepository.findByMemberPk(requestedMember.getId())
                .orElseThrow(() -> new AuthException(AuthErrorCode.UNSAVED_REFRESH_TOKEN));
    }

    private void updateRefreshTokenWithNewRefreshTokenValue(RefreshToken refreshToken, String refreshTokenValue) {
        RefreshToken updateRefreshToken = refreshToken.updateRefreshTokenValue(refreshTokenValue);
        refreshTokenRepository.save(updateRefreshToken);
    }

    private void saveRefreshTokenWithTokenValue(String refreshTokenValue, Long key) {
        RefreshToken refreshToken = RefreshToken.builder()
                .key(key)
                .refreshTokenValue(refreshTokenValue)
                .build();
        refreshTokenRepository.save(refreshToken);
    }
}

