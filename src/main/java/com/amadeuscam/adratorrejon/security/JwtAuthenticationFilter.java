package com.amadeuscam.adratorrejon.security;

import com.amadeuscam.adratorrejon.services.UserDetailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    private final static Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            //obtenemos el token de la solicitud HTTP
            String token = obtenerJWTdeLaSolicitud(request);

            //validamos el token
            if (token != null && jwtTokenProvider.validateToken(token)) {
                //obtenemos el username del token
                String username = jwtTokenProvider.getUsernameFromJwt(token);

                //cargamos el usuario asociado al token
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //establecemos la seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            logger.error("fail en el metodo doFilterInternal");
        }
        filterChain.doFilter(request, response);
    }

    //Bearer token de acceso
    private String obtenerJWTdeLaSolicitud(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
