package com.example.filmrating.service;

public interface IMailSenderService {
    void sendMessageWithHtml(String to, String subject, String text);}
