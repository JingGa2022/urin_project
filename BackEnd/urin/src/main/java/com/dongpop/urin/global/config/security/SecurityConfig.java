package com.dongpop.urin.global.config.security;

import com.dongpop.urin.domain.member.repository.MemberRepository;
import com.dongpop.urin.oauth.exception.RestAuthenticationEntryPoint;
import com.dongpop.urin.oauth.filter.TokenAuthenticationFilter;
import com.dongpop.urin.oauth.handler.CustomLogoutHandler;
import com.dongpop.urin.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.dongpop.urin.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.dongpop.urin.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.dongpop.urin.oauth.service.CustomOAuth2AuthorizedClientService;
import com.dongpop.urin.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final CustomLogoutHandler customLogoutHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final MemberRepository memberRepository;
    private final CustomOAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final CorsProperties corsProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource(corsProperties))
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                    .logoutUrl("/api/v1/auth/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                    .invalidateHttpSession(false)
                    .addLogoutHandler(customLogoutHandler)
                .and()
                .authorizeRequests()
                    .antMatchers("/oauth2/**", "/swagger-ui/**", "/swagger-resources/**",
                            "/v2/**", "/api/v1/notification").permitAll()
                    .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .oauth2Login()
                    .authorizedClientService(oAuth2AuthorizedClientService)
                    .redirectionEndpoint()
                        .baseUri("/oauth2/code/*")
                    .and()
                        .authorizationEndpoint()
                            .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                    .and()
                        .userInfoEndpoint().userService(customOAuth2UserService(memberRepository, passwordEncoder()))
                    .and()
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService customOAuth2UserService(MemberRepository memberRepository,
                                                     PasswordEncoder passwordEncoder) {
        return new CustomOAuth2UserService(memberRepository, passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(CorsProperties corsProperties) {
        CorsConfiguration configuration = new CorsConfiguration();

        List<String> allowOrigins = corsProperties.getAllowOrigins();
        for (String allowOrigin : allowOrigins) {
            log.info("[Security Config] : Allow-Origin = {}", allowOrigin);
            configuration.addAllowedOrigin(allowOrigin);
        }
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
