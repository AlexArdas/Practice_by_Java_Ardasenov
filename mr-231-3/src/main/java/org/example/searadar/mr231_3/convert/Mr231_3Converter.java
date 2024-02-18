package org.example.searadar.mr231_3.convert;

import ru.oogis.searadar.api.message.InvalidMessage;
import ru.oogis.searadar.api.message.RadarSystemDataMessage;
import ru.oogis.searadar.api.message.SearadarStationMessage;
import ru.oogis.searadar.api.message.TrackedTargetMessage;
import ru.oogis.searadar.api.types.IFF;
import ru.oogis.searadar.api.types.TargetStatus;
import ru.oogis.searadar.api.types.TargetType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Класс Mr231_3Converter выполняет конвертацию сообщений формата Mr231 в объекты SearadarStationMessage.
 */
public class Mr231_3Converter{

    // Массив DISTANCE_SCALE содержит значения масштаба расстояния.
    private static final Double[] DISTANCE_SCALE = {0.125, 0.25, 0.5, 1.5, 3.0, 6.0, 12.0, 24.0, 48.0, 96.0};

    // Поля fields и msgType используются для хранения полей сообщения и его типа.
    private String[] fields;
    private String msgType;

    /**
     * Метод convert() выполняет конвертацию сообщения в объекты SearadarStationMessage.
     * @param message строка сообщения в формате Mr231
     * @return список объектов типа SearadarStationMessage
     */
    public List<SearadarStationMessage> convert(String message) {

        // Создание списка сообщений.
        List<SearadarStationMessage> msgList = new ArrayList<>();

        // Чтение полей сообщения.
        readFields(message);

        // Обработка сообщения в зависимости от его типа.
        switch (msgType) {

            case "TTM" :
                msgList.add(getTTM());
                break;

            case "RSD" : {
                // Получение объекта RadarSystemDataMessage.
                RadarSystemDataMessage rsd = getRSD();
                InvalidMessage invalidMessage = checkRSD(rsd);

                // Проверка RSD и добавление в список сообщений.
                msgList.add(Objects.requireNonNullElse(invalidMessage, rsd));
                break;
            }

        }

        return msgList;
    }

    /**
     * Метод readFields() читает поля сообщения и определяет его тип.
     * @param msg строка сообщения
     */
    private void readFields(String msg) {

        String temp = msg.substring( 3, msg.indexOf("*") ).trim();

        fields = temp.split(Pattern.quote(","));
        msgType = fields[0];

    }

    /**
     * Метод getTTM() создает объект типа TrackedTargetMessage на основе данных из сообщения.
     * @return объект типа TrackedTargetMessage
     */
    private TrackedTargetMessage getTTM() {

        TrackedTargetMessage ttm = new TrackedTargetMessage();
        Long msgRecTimeMillis = System.currentTimeMillis();

        ttm.setMsgTime(msgRecTimeMillis);
        TargetStatus status = TargetStatus.UNRELIABLE_DATA;
        IFF iff = IFF.UNKNOWN;
        TargetType type = TargetType.UNKNOWN;

        // Определение статуса.
        switch (fields[12]) {
            case "L" : status = TargetStatus.LOST;
                break;

            case "Q" : status = TargetStatus.UNRELIABLE_DATA;
                break;

            case "T" : status = TargetStatus.TRACKED;
                break;
        }

        // Определение IFF.
        switch (fields[11]) {
            case "b" : iff = IFF.FRIEND;
                break;

            case "p" : iff = IFF.FOE;
                break;

            case "d" : iff = IFF.UNKNOWN;
                break;
        }

        // Установка параметров TTM.
        ttm.setMsgRecTime(new Timestamp(System.currentTimeMillis()));
        ttm.setTargetNumber(Integer.parseInt(fields[1]));
        ttm.setDistance(Double.parseDouble(fields[2]));
        ttm.setBearing(Double.parseDouble(fields[3]));
        ttm.setCourse(Double.parseDouble(fields[6]));
        ttm.setSpeed(Double.parseDouble(fields[5]));
        ttm.setStatus(status);
        ttm.setIff(iff);

        ttm.setType(type);

        return ttm;
    }

    /**
     * Метод getRSD() создает объект типа RadarSystemDataMessage на основе данных из сообщения.
     * @return объект типа RadarSystemDataMessage
     */
    private RadarSystemDataMessage getRSD() {

        RadarSystemDataMessage rsd = new RadarSystemDataMessage();

        rsd.setMsgRecTime(new Timestamp(System.currentTimeMillis()));

        rsd.setInitialDistance(Double.parseDouble(fields[1]));
        rsd.setInitialBearing(Double.parseDouble(fields[2]));
        rsd.setMovingCircleOfDistance(Double.parseDouble(fields[3]));
        rsd.setBearing(Double.parseDouble(fields[4]));
        rsd.setDistanceFromShip(Double.parseDouble(fields[9]));
        rsd.setBearing2(Double.parseDouble(fields[10]));
        rsd.setDistanceScale(Double.parseDouble(fields[11]));
        rsd.setDistanceUnit(fields[12]);
        rsd.setDisplayOrientation(fields[13]);
        rsd.setWorkingMode(fields[14]);

        return rsd;

    }

    /**
     * Метод checkRSD() проверяет объект типа RadarSystemDataMessage на валидность.
     * @param rsd объект типа RadarSystemDataMessage
     * @return объект типа InvalidMessage, если данные неверны, иначе null
     */
    private InvalidMessage checkRSD(RadarSystemDataMessage rsd) {

        InvalidMessage invalidMessage = new InvalidMessage();
        String infoMsg = "";

        // Проверка масштаба расстояния на валидность.
        if (!Arrays.asList(DISTANCE_SCALE).contains(rsd.getDistanceScale())) {

            infoMsg = "RSD message. Wrong distance scale value: " + rsd.getDistanceScale();
            invalidMessage.setInfoMsg(infoMsg);
            return invalidMessage;
        }

        return null;
    }
}