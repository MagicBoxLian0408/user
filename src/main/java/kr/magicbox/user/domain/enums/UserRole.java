package kr.magicbox.user.domain.enums;

import kr.magicbox.user.domain.exception.InvalidUserRoleException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

public enum UserRole {
    USER,
    CREATOR,
    ADMIN;

    private static final Map<String, UserRole> NAME_MAP = Arrays.stream(values()).collect(Collectors.toMap(UserRole::name, role -> role));

    public static UserRole of(String name) {
        UserRole role = NAME_MAP.get(name);
        if (role == null) {
            throw new InvalidUserRoleException("Invalid user role: " + name);
        }
        return role;
    }
}