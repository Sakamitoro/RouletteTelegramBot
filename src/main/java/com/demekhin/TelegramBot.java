package com.demekhin;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "ConsoleClientBot";
    }

    @Override
    public String getBotToken() {
        return "5349494181:AAG_p38yQGfPdvbzGP9IrIyvmjS0W9FMSeA";
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> betSelectionKeyboard(chatId);

                case "/stop" -> stopCommandReceived(chatId, update.getMessage().getChat().getFirstName());

                default -> sendMessage(chatId, "Sorry, there is no such team");
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();


            switch (callbackData) {
                case "betBlack" -> {
                    typeOfBet = "black";
                    String text = "You have chosen Black";
                    executeEditMessageText(text, chatId, messageId);
                    amountSelectionKeyboard(chatId);
                    break;
                }
                case "betRed" -> {
                    typeOfBet = "red";
                    String text = "You have chosen Red";
                    executeEditMessageText(text, chatId, messageId);
                    amountSelectionKeyboard(chatId);
                    break;
                }
                case "betNumber" -> {
                    typeOfBet = "number";
                    String text = "You have chosen Number";
                    executeEditMessageText(text, chatId, messageId);
                    numberSelectionKeyboard(chatId);
                    break;
                }
                case "betEven" -> {
                    typeOfBet = "even";
                    String text = "You have chosen Even";
                    executeEditMessageText(text, chatId, messageId);
                    amountSelectionKeyboard(chatId);
                    break;
                }
                case "betOdd" -> {
                    typeOfBet = "odd";
                    String text = "You have chosen Odd";
                    executeEditMessageText(text, chatId, messageId);
                    amountSelectionKeyboard(chatId);
                    break;
                }
                case "bet1to18" -> {
                    typeOfBet = "bet118";
                    String text = "You have chosen Odd";
                    executeEditMessageText(text, chatId, messageId);
                    amountSelectionKeyboard(chatId);
                    break;
                }
                case "bet19to36" -> {
                    typeOfBet = "bet1936";
                    String text = "You have chosen Odd";
                    executeEditMessageText(text, chatId, messageId);
                    amountSelectionKeyboard(chatId);
                    break;
                }
                case "bet5" -> {
                    betAmount = 5;
                    betProcessingService();
                    String text = "your bet amount $" + betAmount + " your result " + resultRandomColor + ", this: " + victoryOrDefeat +
                            "\n your balance $" + balance;
                    executeEditMessageText(text, chatId, messageId);
                    break;
                }
                case "bet10" -> {
                    betAmount = 10;
                    betProcessingService();
                    String text = "your bet amount $" + betAmount + " your result " + resultRandomColor + ", this: " + victoryOrDefeat +
                            "\n your balance $" + balance;
                    executeEditMessageText(text, chatId, messageId);
                    break;
                }
                case "bet25" -> {
                    betAmount = 25;
                    betProcessingService();
                    String text = "your bet amount $" + betAmount + " your result " + resultRandomColor + ", this: " + victoryOrDefeat +
                            "\n your balance $" + balance;
                    executeEditMessageText(text, chatId, messageId);
                    break;
                }
                case "bet50" -> {
                    betAmount = 50;
                    betProcessingService();
                    String text = "your bet amount $" + betAmount + " your result " + resultRandomColor + ", this: " + victoryOrDefeat +
                            "\n your balance $" + balance;
                    executeEditMessageText(text, chatId, messageId);
                    break;
                }
            }
            callbackDataNumber = Integer.parseInt(callbackData);
            amountSelectionKeyboard(chatId);
        }
    }


    private int callbackDataNumber;
    private String typeOfBet;
    private int balance = 1000;
    private int betAmount;
    private String victoryOrDefeat;
    private int resultRandomNumber;
    private String resultRandomColor;


    private void stopCommandReceived(long chatId, String name) {

        String answer = "Good bye " + name + " com back to us";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void betSelectionKeyboard(long chatId) {
        Server rouletteServer = new Server();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Choose the type of bet\n" +
                "1 - black\n" +
                "2 - red\n" +
                "3 - Number\n" +
                "4 - Even\n" +
                "5 - Odd\n" +
                "6 - From 1 to 18\n" +
                "7 - From 19 to 36");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        rouletteServer.addButton(rowInLine, "1", "betBlack");
        rouletteServer.addButton(rowInLine, "2", "betRed");
        rouletteServer.addButton(rowInLine, "3", "betNumber");
        rouletteServer.addButton(rowInLine, "4", "betEven");
        rouletteServer.addButton(rowInLine, "5", "betOdd");
        rouletteServer.addButton(rowInLine, "6", "bet1to18");
        rouletteServer.addButton(rowInLine, "7", "bet19to36");

        rowsInLine.add(rowInLine);
        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void amountSelectionKeyboard(long chatId) {
        Server rouletteServer = new Server();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Select the bid amount, your balance $" + balance);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        rouletteServer.addButton(rowInLine, "5", "bet5");
        rouletteServer.addButton(rowInLine, "10", "bet10");
        rouletteServer.addButton(rowInLine, "25", "bet25");
        rouletteServer.addButton(rowInLine, "50", "bet50");

        rowsInLine.add(rowInLine);
        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private void numberSelectionKeyboard(long chatId) {
        Server rouletteServer = new Server();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Choose a number to bet on");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();

        rouletteServer.addButton(rowInLine, "1", "1");
        rouletteServer.addButton(rowInLine, "2", "2");
        rouletteServer.addButton(rowInLine, "3", "3");
        rouletteServer.addButton(rowInLine, "4", "4");
        rouletteServer.addButton(rowInLine, "5", "5");
        rouletteServer.addButton(rowInLine, "6", "6");
        rouletteServer.addButton(rowInLine, "7", "7");
        rouletteServer.addButton(rowInLine, "8", "8");

        rouletteServer.addButton(rowInLine1, "9", "9");
        rouletteServer.addButton(rowInLine1, "10", "10");
        rouletteServer.addButton(rowInLine1, "11", "11");
        rouletteServer.addButton(rowInLine1, "12", "12");
        rouletteServer.addButton(rowInLine1, "13", "13");
        rouletteServer.addButton(rowInLine1, "14", "14");
        rouletteServer.addButton(rowInLine1, "15", "15");
        rouletteServer.addButton(rowInLine1, "16", "16");

        rouletteServer.addButton(rowInLine2, "17", "17");
        rouletteServer.addButton(rowInLine2, "18", "18");
        rouletteServer.addButton(rowInLine2, "19", "19");
        rouletteServer.addButton(rowInLine2, "20", "20");
        rouletteServer.addButton(rowInLine2, "21", "21");
        rouletteServer.addButton(rowInLine2, "22", "22");
        rouletteServer.addButton(rowInLine2, "23", "23");
        rouletteServer.addButton(rowInLine2, "24", "24");

        rouletteServer.addButton(rowInLine3, "25", "25");
        rouletteServer.addButton(rowInLine3, "26", "26");
        rouletteServer.addButton(rowInLine3, "27", "27");
        rouletteServer.addButton(rowInLine3, "28", "28");
        rouletteServer.addButton(rowInLine3, "29", "29");
        rouletteServer.addButton(rowInLine3, "30", "30");
        rouletteServer.addButton(rowInLine3, "31", "31");
        rouletteServer.addButton(rowInLine3, "32", "32");

        rouletteServer.addButton(rowInLine4, "33", "33");
        rouletteServer.addButton(rowInLine4, "34", "34");
        rouletteServer.addButton(rowInLine4, "35", "35");
        rouletteServer.addButton(rowInLine4, "36", "36");
        rouletteServer.addButton(rowInLine4, "0", "0");

        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);
        rowsInLine.add(rowInLine4);
        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void resultRandomNumber() {
        final List<String> colorArray = IntStream.range(0, 37).boxed()
                .map(x -> {
                    if (x == 0) {
                        return x + " green";
                    }
                    if (x % 2 == 0) {
                        return x + " red";
                    } else {
                        return x + " black";
                    }
                }).toList();

        resultRandomNumber = (int) Math.floor(Math.random() * colorArray.size());
        resultRandomColor = colorArray.get(resultRandomNumber);

    }


    private void betProcessingService() {
        resultRandomNumber();
        switch (typeOfBet) {
            case "black":
                if ((resultRandomNumber & 1) == 0) {
                    balance -= betAmount;
                    victoryOrDefeat = "loss";
                } else {
                    balance += betAmount * 2;
                    victoryOrDefeat = "win";
                }
                break;
            case "red":
                if ((resultRandomNumber & 1) == 0) {
                    balance += betAmount * 2;
                    victoryOrDefeat = "win";
                } else {
                    balance -= betAmount;
                    victoryOrDefeat = "loss";
                }
                break;
            case "number":
                if (resultRandomNumber == callbackDataNumber) {
                    balance += betAmount * 35;
                    victoryOrDefeat = "win";
                } else {
                    balance -= betAmount;
                    victoryOrDefeat = "loss";
                }
                break;
            case "even":
                if (resultRandomNumber % 2 == 0) {
                    balance += betAmount * 2;
                    victoryOrDefeat = "win";
                } else {
                    balance -= betAmount;
                    victoryOrDefeat = "loss";
                }
                break;
            case "odd":
                if (resultRandomNumber % 2 == 0) {
                    balance -= betAmount;
                    victoryOrDefeat = "loss";
                } else {
                    balance += betAmount * 2;
                    victoryOrDefeat = "win";
                }
                break;
            case "bet118":
                if (resultRandomNumber <= 18) {
                    balance += betAmount * 2;
                    victoryOrDefeat = "win";
                } else {
                    balance -= betAmount;
                    victoryOrDefeat = "loss";
                }
                break;
            case "bet1936":
                if (resultRandomNumber >= 19) {
                    balance += betAmount * 2;
                    victoryOrDefeat = "win";
                } else {
                    balance -= betAmount;
                    victoryOrDefeat = "loss";
                }
                break;
        }
    }

}
