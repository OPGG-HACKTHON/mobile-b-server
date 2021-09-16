package co.mobile.b.server.controller;

import co.mobile.b.server.config.security.JwtTokenProvider;
import co.mobile.b.server.dto.request.LoginParam;
import co.mobile.b.server.dto.response.LoginResult;
import co.mobile.b.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object login(@RequestBody @Validated LoginParam loginParam) throws Exception{

        /* 유저 정보를 UsernamePasswordAuthenticationToken 변환 */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginParam.getEmail(), loginParam.getPassword());
        /* token으로 AuthenticationManager를 통해 UserDetailsService가 처리 하도록 함 */
        Authentication authentication = authenticationManager.authenticate(token);
        /* 실제 SecurityContextHolder에 authentication 정보 등록 */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResult(jwtTokenProvider.createToken(authentication), userService.getUserByEmail(loginParam.getEmail()));
    }

}
