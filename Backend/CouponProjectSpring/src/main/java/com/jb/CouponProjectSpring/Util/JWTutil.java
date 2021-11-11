package com.jb.CouponProjectSpring.Util;

import com.jb.CouponProjectSpring.Beans.UserDetails;
import com.jb.CouponProjectSpring.Enums.ClientType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTutil {
    /**
     * Signature algorithm field - type of encryption
     */
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    /**
     * Encoded secret key field - our private key
     */
    private String encodedSecretKey = "TheYorkCountyMaineTercentenaryhalfdollarisafiftycentpiecemintedin1936asacommemorativecointocommemoratethe300thanniversaryofthefoundingofYorkCountythesouthernmostcountyinMaineandthefirsttobeorganizedTheobverseshowsBrownsGarrisonafortaroundwhichYorkCountyde";
    //private String encodedSecretKey = "u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H";
    /**
     * Decoded secret key field - creates our private key
     */
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), signatureAlgorithm);

    /**
     * Generate token
     * this method generates our key
     * @param userDetails- the user's details
     * @return Token in String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("password", userDetails.password);
        claims.put("clientType", userDetails.getClientType());
        String myToken = createToken(claims, userDetails.getEmail());
        System.out.println("New token was created : " + myToken );
        return myToken;
    }

    /**
     * Create token
     * this method creates our token
     * @param claims - contains the fields which we are basing the token on
     * @param subject - ?????????????
     * @return Token in String
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Instant now = Instant.now();
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    /**
     * Extract all claims
     * this method extracts all the claims in json
     * @param token - the user's token
     * @return Claims
     * @throws ExpiredJwtException throws error if something went wrong
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
        System.out.println(jwtParser.parseClaimsJws(token).getSignature());
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /**
     * Extract email
     * this method extracts the user's email
     * @param token- the user's token
     * @return String - the user's email
     */
    public String extractEmail(String token) {
        // System.out.println("email:"+extractAllClaims(token).getSubject());
        return extractAllClaims(token).getSubject();
    }

    public String extractPassword (String token){
        // System.out.println("password:"+extractAllClaims(token).getId());
        return extractAllClaims(token).getId();
    }



    /**
     * Extract expiration date
     * this method extracts the expiration date of the token
     * @param token -the user's token
     * @return the token's expiration date
     */
    public Date extractExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Is token expired
     * this method checks if the token is expired
     * @param token - the user's token
     * @return boolean- if it's expired
     */
    private boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException | SignatureException err) {
            return true;
        }
    }

    /**
     * Validate token
     * this method checks the validation of the user's details with the token
     * @param token - the user's token
     * @return boolean - if the token is valid
     */
    public boolean validateToken(String token) {
        //final String username = extractEmail(token);
        return !isTokenExpired(token) ; //validate signutare
    }

    //tester
/*
    public static void main(String[] args) {
        //create our instance of user that the token will be created for him.
        UserDetails admin = new UserDetails("admin@admin.com","12345", ClientType.administrator);
        //use our new shiny JWTutil.
        JWTutil myUtil = new JWTutil();
        //generate a new token
        String myToken = myUtil.generateToken(admin);
        //print the token on screen to show to our mother.
        System.out.println("my new shiny token:\n"+myToken);
        //get our clamis :)
        System.out.println("our token body is:\n"+myUtil.extractAllClaims(myToken));
        System.out.println("and the winner is:\n"+myUtil.extractEmail(myToken));
    }

 */


}
