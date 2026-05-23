package kr.magicbox.user.domain.vo;

public record DeviceId(Long value) {

    public DeviceId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("디바이스 ID는 양수여야 합니다.");
        }
    }

    public static DeviceId of(Long value) {
        return new DeviceId(value);
    }
}