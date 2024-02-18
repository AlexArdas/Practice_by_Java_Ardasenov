package org.example.searadar.mr231_3.station;

import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.example.searadar.mr231_3.convert.Mr231_3Converter;
import java.nio.charset.Charset;

/**
 * Класс Mr231_3StationType представляет тип станции МР-231-3.
 * Он отвечает за инициализацию и создание конвертера для данного типа станции.
 */
public class Mr231_3StationType {

    // Строковая константа, представляющая тип станции
    private static final String STATION_TYPE = "МР-231-3";

    // Строковая константа, представляющая название кодека
    private static final String CODEC_NAME = "mr231-3";

    /**
     * Метод doInitialize() выполняет инициализацию для данного типа станции.
     * Здесь настраивается кодек для работы с текстовыми данными.
     */
    protected void doInitialize() {
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(
                Charset.defaultCharset(),
                LineDelimiter.UNIX,
                LineDelimiter.CRLF
        );

    }

    /**
     * Метод createConverter() создает и возвращает новый экземпляр класса Mr231_3Converter.
     * Этот конвертер используется для обработки данных определенного формата.
     * @return новый экземпляр Mr231_3Converter
     */
    public Mr231_3Converter createConverter() {
        return new Mr231_3Converter();
    }
}
