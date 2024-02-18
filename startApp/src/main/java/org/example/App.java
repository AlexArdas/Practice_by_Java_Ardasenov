package org.example;

import org.example.searadar.mr231.convert.Mr231Converter;
import org.example.searadar.mr231.station.Mr231StationType;
import org.example.searadar.mr231_3.convert.Mr231_3Converter;
import org.example.searadar.mr231_3.station.Mr231_3StationType;
import ru.oogis.searadar.api.message.SearadarStationMessage;

import java.util.List;

/**
 * Практическое задание направлено на проверку умения создавать функциональные модули и работать с технической
 * документацией.
 * Задача написать парсер сообщений для протокола МР-231-3 на основе парсера МР-231.
 * Приложение к заданию файлы:
 * - Протокол МР-231.docx
 * - Протокол МР-231-3.docx
 * <p>
 * Требования:
 * 1. Перенести контрольный пример из "Протокола МР-231.docx" в метод main, по образцу в методе main;
 * 2. Проверить правильность работы конвертера путём вывода контрольного примера в консоль, по образцу в методе main;
 * 3. Создать модуль с именем mr-231-3 и добавить его в данный проект <module>../mr-231-3</module> и реализовать его
 * функционал в соответствии с "Протоколом МР-231-3.docx" на подобии модуля mr-231;
 * 4. Создать для модуля контрольный пример и проверить правильность работы конвертера путём вывода контрольного
 * примера в консоль
 *
 * <p>
 *     Примечание:
 *     1. Данные в пакете ru.oogis не изменять!!!
 *     2. Весь код должен быть покрыт JavaDoc
 */

/**
 * Класс App представляет точку входа в приложение.
 * Здесь демонстрируется использование конвертеров для различных форматов сообщений станций МР-231 и МР-231-3.
 */
public class App {
    public static void main(String[] args) {
        // Контрольный пример для МР-231
        String mr231_TTM = "$RATTM,66,28.71,341.1,T,57.6,024.5,T,0.4,4.1,N,b,L,,457362,А*42";
        String mr231_VHW = "$RAVHW,115.6,T,,,46.0,N,,*71";
        String mr231_RSD = "$RARSD,50.5,309.9,64.8,132.3,,,,,52.6,155.0,48.0,K,N,S*28";


        // Создание конвертера для формата TTM МР-231

        Mr231StationType mr231_TTM_Obj = new Mr231StationType();
        Mr231Converter converter_TTM = mr231_TTM_Obj.createConverter();
        // Конвертация и вывод сообщений
        List<SearadarStationMessage> searadarMessages_TTM = converter_TTM.convert(mr231_TTM);
        searadarMessages_TTM.forEach(System.out::println);

        // Создание конвертера для формата VHW МР-231

        Mr231StationType mr231_VHW_Obj = new Mr231StationType();
        Mr231Converter converter_VHW = mr231_VHW_Obj.createConverter();
        // Конвертация и вывод сообщений
        List<SearadarStationMessage> searadarMessages_VHW = converter_VHW.convert(mr231_VHW);
        searadarMessages_VHW.forEach(System.out::println);

        // Создание конвертера для формата RSD МР-231

        Mr231StationType mr231_RSD_Obj = new Mr231StationType();
        Mr231Converter converter_RSD = mr231_RSD_Obj.createConverter();
        // Конвертация и вывод сообщений
        List<SearadarStationMessage> searadarMessages_RSD = converter_RSD.convert(mr231_RSD);
        searadarMessages_RSD.forEach(System.out::println);

        // Пример для МР-231-3

        String mr231_3_TTM = "$RATTM,28,28.99,160.0,T,88.4,064.0,T,4.7,77.7,N,b,L,,774920,А*59";
        String mr231_3_RSD = "$RARSD,36.5,331.4,8.4,320.6,,,,,11.6,185.3,96.0,N,N,S*33";

        // Создание конвертера для формата ТТМ МР-231-3
        Mr231_3StationType mr231_3_TTM_Obj = new Mr231_3StationType();
        Mr231_3Converter converter_3_TTM = mr231_3_TTM_Obj.createConverter();
        // Конвертация и вывод сообщений
        List<SearadarStationMessage> searadarMessages_3_TTM = converter_3_TTM.convert(mr231_3_TTM);
        searadarMessages_3_TTM.forEach(System.out::println);

        // Создание конвертера для формата RSD МР-231-3
        Mr231_3StationType mr231_3_RSD_Obj = new Mr231_3StationType();
        Mr231_3Converter converter_3_RSD = mr231_3_RSD_Obj.createConverter();
        // Конвертация и вывод сообщений
        List<SearadarStationMessage> searadarMessages_3_RSD = converter_3_RSD.convert(mr231_3_RSD);
        searadarMessages_3_RSD.forEach(System.out::println);

    }
}
