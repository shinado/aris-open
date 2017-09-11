package com.ss.aris.open.pipes.search.translator;

import android.content.Context;

import com.ss.aris.open.pipes.entity.SearchableName;

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
