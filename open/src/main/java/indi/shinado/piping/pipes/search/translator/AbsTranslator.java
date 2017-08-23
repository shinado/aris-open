package indi.shinado.piping.pipes.search.translator;

import android.content.Context;

import indi.shinado.piping.pipes.entity.SearchableName;

public abstract class AbsTranslator {

    public AbsTranslator(Context context){
    }

    /**
     * Kakao       -> {kakao}
     * KakaoTalk   -> {kakao, talk}
     * reKakao     -> {re, kakao}
     * iKakao Talk -> {i,kakao, talk}
     * @param name
     * @return
     */
    public abstract SearchableName getName(String name);
    public abstract void destroy();
    public abstract boolean ready();
}
