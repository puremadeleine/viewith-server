package com.puremadeleine.viewith.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NicknameConstants {
    public static String MAX = "15";
    public static String PATTERN = "^(?! )[a-zA-Z0-9가-힣\\s]{1," + MAX + "}(?<! )$";

    // 최대 4글자
    public static String[] PREFIXES = {
            "귀여운", "작은", "강한", "빠른", "똑똑한", "따뜻한", "멋진", "행복한", "유쾌한", "기쁜",
            "활기찬", "튼튼한", "재미난", "빛나는", "고운", "깜찍한", "슬기로운", "맑은", "활달한", "즐거운",
            "차분한", "평온한", "달콤한", "상냥한", "정다운", "믿음직한", "든든한", "우아한", "다정한", "조용한",
            "부드러운", "풍성한", "기특한", "깨끗한", "희망찬", "소중한", "독창적인", "평화로운", "착한", "순수한",
            "반짝이는", "활발한", "온화한", "따뜻한", "깔끔한", "겸손한", "사랑스런", "기운찬", "총명한", "밝은",
            "대담한", "강렬한", "희귀한", "독특한", "호기심", "자유로운", "섬세한", "정교한", "유능한", "신속한",
            "용맹한", "균형잡힌", "배려깊은", "희망적인", "순박한", "광대", "사려깊은", "순진한", "귀티나는", "눈치빠른",
            "조화로운", "흥미로운", "겹겹이 쌓인", "날렵한", "활기있는", "엄청난", "따스한", "창의적인", "풍부한", "강렬한",
            "친근한", "활달한", "설레는", "기발한", "쾌활한", "포근한", "개성있는", "유쾌한", "단정한", "독창적인"
    };
    public static String MIDDLE = " ";

    // 최대 5글자
    public static String[] ANIMALS = {
            "다람쥐", "토끼", "강아지", "고양이", "호랑이", "사자", "판다", "여우", "곰", "늑대",
            "부엉이", "독수리", "돌고래", "물개", "펭귄", "앵무새", "코끼리", "기린", "하마", "치타",
            "너구리", "고슴도치", "까마귀", "원숭이", "이구아나", "오리", "공작", "거북", "갈매기", "수달",
            "병아리", "닭", "돼지", "양", "말", "소", "코알라", "두더지", "사슴", "벌새",
            "참새", "고래", "물총새", "족제비", "산부엉이", "들쥐", "홍학", "불독", "늑대개", "참새",
            "공작새", "염소", "수탉", "암탉", "토끼개", "길고양이", "사막여우", "여우원숭이", "붉은팬더", "물소",
            "개구리", "작은박쥐", "도마뱀", "악어", "낙타", "쥐", "바다표범", "산양", "바다사자", "갈색곰",
            "흑표범", "은빛여우", "비둘기", "들고양이", "들개", "바둑강아지", "날다람쥐", "작은펭귄", "회색늑대", "백조",
            "백호", "산코끼리", "청설모", "겨울토끼", "긴팔원숭이", "빨간여우", "바다거북", "회색곰", "하늘다람쥐", "눈표범"
    };
}