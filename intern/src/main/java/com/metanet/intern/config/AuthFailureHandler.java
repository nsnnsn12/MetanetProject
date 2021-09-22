package com.metanet.intern.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler{
	private final String defaultFailureUrl = "/account/loginFail";
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("로그인 실패 핸들러:"+exception.getMessage());
		request.setAttribute("errMessage", exception.getMessage());
 
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
}
