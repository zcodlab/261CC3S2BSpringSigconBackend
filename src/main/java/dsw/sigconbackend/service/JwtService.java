package dsw.sigconbackend.service;

import dsw.sigconbackend.model.Modulo;
import dsw.sigconbackend.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private  String secretKey;

    //@Value("3600000")     //1 hora=60 minutosx 60 segundo x 1000 milisegundos
    @Value("300000")     //5 minutos x 60 segundo x 1000 milisegundos
    private long jwtExpiration;

    @Value("86400000") // 1 day (1 * 24 * 60 * 60 * 1000)
    private long refreshExpiration;

    public String generateToken(Usuario usuario, List<Modulo> modules){
        Map<String,Object> claims = new HashMap<>();
        claims.put("personaId", usuario.getPersona().getIdPersona());
        claims.put("email",usuario.getEmail());
        claims.put("names",usuario.getPersona().getNombres());
        claims.put("role",usuario.getRol());
        claims.put("modules",modules);

        return Jwts.builder()
                .claims(claims)
                .subject(usuario.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public SecretKey getSigningKey(){
        byte[] keyBytes=Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(Usuario usuario) {
        return buildToken(new HashMap<>(), usuario, null, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            Usuario usuario,
            List<Modulo> modules,
            long expiration
    ) {
        Map<String, Object> claims = new HashMap<>(extraClaims);
        if (usuario.getPersona() != null) {
            claims.put("personaId", usuario.getPersona().getIdPersona());
            claims.put("names", usuario.getPersona().getNombres());
        }
        claims.put("email", usuario.getEmail());
        if (usuario.getRol() != null) {
            claims.put("role", usuario.getRol());
        }
        if (modules != null) {
            claims.put("modules", modules);
        }

        return Jwts.builder()
                .claims(claims)
                .subject(usuario.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return (username.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
