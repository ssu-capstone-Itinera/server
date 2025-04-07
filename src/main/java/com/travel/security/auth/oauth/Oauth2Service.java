package com.travel.security.auth.oauth;

import com.travel.security.auth.dto.UserInfo;

public interface Oauth2Service {
    UserInfo getUserInfo(String socialAccessToken);
}
