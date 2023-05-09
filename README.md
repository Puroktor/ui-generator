# Курсовая работа
### Автоматическая генерация пользовательского интерфейса Java приложений на Angular 
Приложение состоит из 3-х основных модулей:
* `ui-generator-core` - хранящего API и отвечающего за создание Angular приложения по заданной конфигурации.
####
* `ui-generator-spring-boot-starter` - парсер UI конфигурации для spring-boot приложений, поддерживающий автоконфигурацию. 
Конфигурация UI создается, исходя из текущего контекста. <br/>
Для его работы достаточно подключить зависимость через jitpack. Для Maven:
  * Указываем репозиторий 
  ```
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
  ```
  * Указываем зависимость
  ```
    <dependency>
        <groupId>com.github.Puroktor.ui-generator</groupId>
        <artifactId>ui-generator-spring-boot-starter</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
  ```
  Демонстрационный проект можно найти [здесь](https://github.com/Puroktor/spring-ui-generation-demo).
####
* Парсер конфигурации для моего [фреймворка](https://github.com/Puroktor/HandmadeFramework). 
Он вдохновлен Java EE и организован по схожему принципу.\
При использовании фреймворка никакой дополнительной настройки генерации UI не требуется.
Тестовый проект, показывающий его возможности находится в репозитории. 
