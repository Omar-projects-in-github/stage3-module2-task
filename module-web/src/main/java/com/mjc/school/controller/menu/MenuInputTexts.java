package com.mjc.school.controller.menu;

public enum MenuInputTexts {
    ENTER_NUMBER_OF_OPERATION("Enter the number of operation:"),
    ENTER_NEWS_ID("Enter news id:"),
    ENTER_NEWS_TITLE("Enter news title:"),
    ENTER_NEWS_CONTENT("Enter news content:"),
    ENTER_NEWS_AUTHOR_ID("Enter news' author id:"),
    ENTER_AUTHOR_ID("Enter author id:"),
    ENTER_AUTHOR_NAME("Enter author name:"),
    OPERATION("Operation: ");

    private String text;

    MenuInputTexts() {
    }

    MenuInputTexts(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
