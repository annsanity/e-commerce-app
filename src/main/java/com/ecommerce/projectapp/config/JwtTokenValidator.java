package com.ecommerce.projectapp.config;

import javax.crypto.SecretKey;

public class JwtTokenValidator extends OncePerRequestFilter {

    protected void doFilterInternal(HttpServlet request, HttpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if(jwt != null){
            jwt = jwt.substring(7);

            try {
                SecretKey keys = SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parseBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(jwt)
                                .getBody();
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                System.out.println("authorities -------- "+authorities);

                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
                SecurityContextHolder.getContext().setAuthentication(authentication);
				
            } catch (Exception e) {
                throw new BadCredentialsException("invalid token...");
            }
        }
         filterChain.doFilter(request, response);
    }

}
