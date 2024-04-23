# Дипломная работа
### Автоматическая генерация пользовательского интерфейса бэкенд приложений на Angular 
Приложение состоит из 4-х основных модулей:
* `ui-generator-core` - CLI tool, создащий приложение на заданном фреймворке (пока поддерживается только Angular) по заранее подготовленной кофигируации. <br/>
  Хранит описание конифигурации проекта, базовое приложение, шаблоны на Freemarker для создания компонентов и логику их наполнения по конфигурации.
####
* `ui-generator-spring-boot-starter` - парсер UI конфигурации для spring-boot приложений.
Поддерживает spring-boot автоконфигурацию контектса. Конфигурация UI создается, исходя из текущего контекста. 
Напрямую использует `ui-generator-core` для создания приложения без вызова CLI<br/>
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

* `ui-config-generator-asp-net`  - парсер UI конфигурации ASP NET Core приложений. 
Для его использования необходимо подключить к проекту зависимость в виде .dll библиотеки.
Затем досточно лишь прописать в `Program.cs`
  ```
  UIConfigGenerator.UIConfigGenerator.ParseAssembley(assembley, url);
  ```
  И файл конфигурации будет создан (можно также указать директорию). <br/>
  Для построения приложения по нему необходимо использовать CLI tool:
  ```
  java -jar ui-cli.jar -o -p ui-config.json
  ```
  Демонстрационный проект можно найти [здесь](https://github.com/Puroktor/asp-net-ui-generation-demo).
* `ui-config-generator-flask`
