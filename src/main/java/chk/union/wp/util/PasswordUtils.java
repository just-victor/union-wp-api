package chk.union.wp.util;

import chk.union.wp.common.exception.UnauthorizedException;
import chk.union.wp.entity.User;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public class PasswordUtils {

    public static String base64Decode(final String bytes) {
        return new String(Base64.getDecoder().decode(bytes.getBytes()));
    }

    public static String base64Encode(final String bytes) {
        return new String(Base64.getEncoder().encode(bytes.getBytes()));
    }

    public static void verifyUserPassword(final User user, final String passwordToVerify) {
        String salt = user.getPasswordSalt();

        String encodedPassword = base64Encode(salt + passwordToVerify);

        if (!Objects.equals(user.getPassword(), encodedPassword)) {
            throw new UnauthorizedException("Пароль неверный");
        }
    }

    public static void generateUserPassword(final User user, final String authorization) {
        String password = base64Decode(authorization);

        String salt = UUID.randomUUID().toString();

        String encodedPassword = base64Encode(salt + password);

        user.setPasswordSalt(salt);
        user.setPassword(encodedPassword);
    }
}
