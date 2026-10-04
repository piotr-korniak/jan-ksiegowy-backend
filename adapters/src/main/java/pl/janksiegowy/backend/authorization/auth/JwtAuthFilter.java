package pl.janksiegowy.backend.authorization.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.janksiegowy.backend.database.TenantContext;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter( HttpServletRequest request) {
        String path= request.getRequestURI();
        return path.startsWith("/v2/auth/login") || path.startsWith("/v2/auth/refresh");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        String authHeader= request.getHeader("Authorization");

        if( authHeader==null || !authHeader.startsWith( "Bearer ")) {
            filterChain.doFilter( request, response);
            return;
        }

        try {
            String token= authHeader.substring(7);
            Claims claims= jwtService.parseClaims(token);

            TenantContext.setCurrentTenant(
                    TenantContext.Context.create()
                            .tenant( claims.get("tenant", String.class))
                            .company( claims.get("schema", String.class))
            );

            UsernamePasswordAuthenticationToken authentication=
                    new UsernamePasswordAuthenticationToken(
                            claims.get("userId", UUID.class), null, List.of());
            SecurityContextHolder.getContext().setAuthentication( authentication);

            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
