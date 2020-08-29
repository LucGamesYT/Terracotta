package org.terracotta.util;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;

import java.net.URI;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
@NoArgsConstructor
public class Utils {

    /**
     * Forges authentication data for sending {@link com.nukkitx.protocol.bedrock.packet.LoginPacket}
     *
     * @param keyPair   public and private keys
     * @param extraData the json object which should be forged
     *
     * @return a fresh forged authentication data
     */
    @SneakyThrows
    public static SignedJWT forgeAuthData(final KeyPair keyPair, final JSONObject extraData) {
        final String publicKeyEncoded = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        final URI x5u = URI.create(publicKeyEncoded);
        final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES384).x509CertURL(x5u).build();
        final long timestamp = System.currentTimeMillis();
        final Date nbf = new Date((timestamp - TimeUnit.SECONDS.toMillis(1)));
        final Date exp = new Date((timestamp + TimeUnit.DAYS.toMillis(1)));

        final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .notBeforeTime(nbf)
                .expirationTime(exp)
                .issueTime(exp)
                .issuer("self")
                .claim("certificateAuthority", true)
                .claim("extraData", extraData)
                .claim("identityPublicKey", publicKeyEncoded)
                .build();

        final SignedJWT jwt = new SignedJWT(header, claimsSet);

        EncryptionUtils.signJwt(jwt, (ECPrivateKey) keyPair.getPrivate());

        return jwt;
    }

    /**
     * Forges skin data for sending {@link com.nukkitx.protocol.bedrock.packet.LoginPacket}
     *
     * @param keyPair  public and private keys
     * @param skinData the json object which should be forged
     *
     * @return a fresh forged skin data
     */
    @SneakyThrows
    public static JWSObject forgeSkinData(final KeyPair keyPair, final JSONObject skinData) {
        final URI x5u = URI.create(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES384).x509CertURL(x5u).build();
        final JWSObject jws = new JWSObject(header, new Payload(skinData));

        EncryptionUtils.signJwt(jws, (ECPrivateKey) keyPair.getPrivate());

        return jws;
    }
}