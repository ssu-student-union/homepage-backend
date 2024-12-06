package ussum.homepage.global.error.code;

import ussum.homepage.global.error.dto.ReasonDto;

public interface BaseCode {
    public ReasonDto getReason();

    public ReasonDto getReasonHttpStatus();
}
