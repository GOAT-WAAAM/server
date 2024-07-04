package com.goat.server.notification.domain.type;


import lombok.Getter;

@Getter
public enum PushType {

    CUSTOM_REVIEW("님, 🔔복습할 시간이에요!", "지금 복습하면 잊어버리지 않아요"),
    AUTO_REVIEW_ONE_HOUR("님 방금 공부한 내용, 다시 볼까요?👀", "학습 직후 복습이 가장 중요해요"),
    AUTO_REVIEW_ONE_DAY("님 어제 공부한 내용 기억하세요?", "지금 복습하지 않으면 70% 이상 잊어버려요"),
    AUTO_REVIEW_ONE_WEEK("님, 이거 잊어버리셨나요?", "지금 복습하면 더 오래 기억할 수 있어요"),
    AUTO_REVIEW_ONE_MONTH("님, 거의 다 왔어요!🚩", "마지막으로 복습하면 장기기억에 저장돼요");

    private final String title;
    private final String body;

    PushType(String title, String body) {
        this.title = title;
        this.body = body;
    }

}
