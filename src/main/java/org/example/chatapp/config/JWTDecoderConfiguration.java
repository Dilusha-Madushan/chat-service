package org.example.chatapp.config;

import com.fasterxml.jackson.databind.util.Converter;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.util.Collections;
import java.util.Map;

@Configuration
public class JWTDecoderConfiguration {
    private static final String ASGARDEO_JWT_TOKEN_TYPE = "at+jwt";
    private static final Logger log = LoggerFactory.getLogger(JWTDecoderConfiguration.class);

    @Bean
    public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
        NimbusJwtDecoder jwtDecoder =  NimbusJwtDecoder.withJwkSetUri(properties.getJwt()
                        .getJwkSetUri())
                .jwtProcessorCustomizer(jwtCustomizer -> {
                    jwtCustomizer.setJWSTypeVerifier(new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType(ASGARDEO_JWT_TOKEN_TYPE)));
                })
                .build();

        MappedJwtClaimSetConverter converter = MappedJwtClaimSetConverter
                .withDefaults(Collections.singletonMap("sub", this::lookupUserIdBySub));
        jwtDecoder.setClaimSetConverter(converter);
        return jwtDecoder;
    }

    private String lookupUserIdBySub(Object sub) {
        log.info(sub.toString());
        return sub.toString();
    }


}
