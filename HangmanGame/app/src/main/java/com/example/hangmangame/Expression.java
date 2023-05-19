package com.example.hangmangame;

import com.google.gson.annotations.SerializedName;

public class Expression {
    @SerializedName("language")
    private String language;
    @SerializedName("category")
    private String category;
    @SerializedName("level")
    private String level;
    @SerializedName("expressionInHungarian")
    private String expressionInHungarian;
    @SerializedName("expression")
    private String expression;
    @SerializedName("descriptionInHungarian")
    private String descriptionInHungarian;
    @SerializedName("descriptionInTargetLanguage")
    private String descriptionInTargetLanguage;
    @SerializedName("picture")
    private String picture;

    public Expression(String language,
                      String category,
                      String level,
                      String expressionInHungarian,
                      String expression,
                      String descriptionInHungarian,
                      String descriptionInTargetLanguage,
                      String picture
    ) {
        this.language = language;
        this.category = category;
        this.level = level;
        this.expressionInHungarian = expressionInHungarian;
        this.expression = expression;
        this.descriptionInHungarian = descriptionInHungarian;
        this.descriptionInTargetLanguage = descriptionInTargetLanguage;
        this.picture = picture;
    }
}
