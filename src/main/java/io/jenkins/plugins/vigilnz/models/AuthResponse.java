package io.jenkins.plugins.vigilnz.models;

public class AuthResponse {

    @SuppressWarnings("lgtm[jenkins/plaintext-storage]")
    private final String accessToken;

    @SuppressWarnings("lgtm[jenkins/plaintext-storage]")
    private final String refreshToken;

    @SuppressWarnings("lgtm[jenkins/plaintext-storage]")
    private final long expiresIn;

    @SuppressWarnings("lgtm[jenkins/plaintext-storage]")
    private final String tokenType;

    public AuthResponse(String accessToken, String refreshToken, long expiresIn, String tokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }
}
