package co.mobile.b.server.service;

import co.mobile.b.server.dto.response.UserResult;

public interface UserService {
    UserResult getUserByEmail(String email);
}
