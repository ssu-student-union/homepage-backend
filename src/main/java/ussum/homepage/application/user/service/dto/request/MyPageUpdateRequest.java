package ussum.homepage.application.user.service.dto.request;

import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;

public record MyPageUpdateRequest(
        boolean union,
        String name,
        String password
) {
    public static boolean validate(MyPageUpdateRequest request) {
        if (!request.union()){
            return false;
        }
        return true;
    }
}
