package com.mobicomm.configuration;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
<<<<<<< HEAD
=======
import org.springframework.security.core.context.SecurityContext;
>>>>>>> 6c7159eb4e592d2e359281dd809e21b0494a4f73
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mobicomm.services.jwt.UserService;
import com.mobicomm.utils.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<< HEAD

=======
>>>>>>> 6c7159eb4e592d2e359281dd809e21b0494a4f73
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
<<<<<<< HEAD
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtUtil.extractUserName(jwt);
        
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
=======
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JWTUtil jwtUtil;
	private final UserService userService;
	
	 public JwtAuthenticationFilter(JWTUtil jwtUtil,UserService userService) {
         this.jwtUtil=jwtUtil;
	 	this.userService = userService;
     }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt= authHeader.substring(7);
		userEmail = jwtUtil.extractUserName(jwt);
		if(StringUtils.isNotEmpty(userEmail) 
				&& SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
			if(jwtUtil.isTokenValid(jwt, userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authToken);
				SecurityContextHolder.setContext(context);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
	

>>>>>>> 6c7159eb4e592d2e359281dd809e21b0494a4f73
}
