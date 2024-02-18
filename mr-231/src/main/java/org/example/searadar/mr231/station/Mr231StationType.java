package org.example.searadar.mr231.station;

import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import org.example.searadar.mr231.convert.Mr231Converter;
import ru.oogis.searadar.api.convert.SearadarExchangeConverter;
import ru.oogis.searadar.api.station.AbstractStationType;

import java.nio.charset.Charset;

/**
 * Класс Mr231StationType представляет тип станции МР-231.
 * Этот класс отвечает за инициализацию и создание конвертера для данного типа станции.
 */
public class Mr231StationType {

    // Строковая константа, представляющая тип станции
    private static final String STATION_TYPE = "МР-231";
    // Строковая константа, представляющая название кодека
    private static final String CODEC_NAME = "mr231";

    /**
     * Метод doInitialize() выполняет инициализацию для данного типа станции.
     * Здесь настраивается кодек для работы с текстовыми данными.
     */
    protected void doInitialize() {
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(
                Charset.defaultCharset(),    // Используется кодировка по умолчанию
                LineDelimiter.UNIX,    // Разделитель строк в формате UNIX
                LineDelimiter.CRLF    // Разделитель строк в формате CRLF
        );

    }

    /**
     * Метод createConverter() создает и возвращает новый экземпляр класса Mr231Converter.
     * Этот конвертер используется для обработки данных определенного формата.
     * @return новый экземпляр Mr231Converter
     */
    public Mr231Converter createConverter() {
        return new Mr231Converter();
    }
}
