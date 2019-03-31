package com.waylau.spring.boot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 启用 Web Spring Security
 */
@EnableWebSecurity
/**
 * 启用方法安全设置
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String KEY = "liuenci.cn";
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private PasswordEncoder passwordEncoder;

	/**
	 * 加密
	 * @return
	 */
	@Bean  
    public PasswordEncoder passwordEncoder() {  
        return new BCryptPasswordEncoder();
    }  
	
	@Bean  
    public AuthenticationProvider authenticationProvider() {  
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		// 设置密码加密方式
		authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;  
    }  
 
	/**
	 * 自定义配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// css js fonts index 不需要权限就可以访问
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
				// 内存数据库 H2 也可以无需权限访问
				.antMatchers("/h2-console/**").permitAll()
				// admin 路径下的资源需要 ADMIN 权限
				.antMatchers("/admins/**").hasRole("ADMIN")
				.and()
				// 基于 Form 表单登录验证
				.formLogin()
				// 自定义登陆页面
				.loginPage("/login").failureUrl("/login-error")
				// 启用 remember me
				.and().rememberMe().key(KEY)
				// 处理异常，拒绝访问就重定向到 403 页面
				.and().exceptionHandling().accessDeniedPage("/403");
		// 禁用 H2 控制台的 CSRF 防护
		http.csrf().ignoringAntMatchers("/h2-console/**");
		// 允许来自同一来源的 H2 控制台的请求
		http.headers().frameOptions().sameOrigin();
	}
	
	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
}
