# Java File Finder



Программа предназначена для поиска файлов в директориях согласно маске, регулярному выражению или имени файла


## Описание
Этот проект реализует утилиту командной строки на Java, которая может:

Парсить аргументы командной строки в формате -ключ=значение и хранить их в виде пар ключ-значение (ArgsName).
Искать файлы по заданным критериям (FileFinder и SearchFilesByName)/

## Основные классы
`ArgsName` — класс для разбора аргументов командной строки в формате -ключ=значение. Выполняет валидацию аргументов и исключает некорректные пары.

`SearchFilesByName` — класс, реализующий интерфейс FileVisitor, для поиска файлов, соответствующих заданному условию. Рекурсивно обходит директории, сохраняя пути к файлам, которые удовлетворяют условию.

`FileFinder` — основной класс для выполнения поиска файлов. Этот класс может использовать как ArgsName для управления параметрами, так и SearchFilesByName для выполнения поиска, обрабатывая условия поиска, такие как расширение файла или текстовый шаблон.

## Требования

* Java: версия 8 или выше
* Maven (для сборки проекта)


## Установка

* Клонируйте репозиторий:
  * `git clone https://github.com/yourusername/yourproject.git`
* Перейдите в директорию проекта:
  * `cd job4j_finder`
* Сборка проекта с помощью Maven:
  * `mvn clean install`


## Структура проекта

`src/main/java/ru/job4j/io/ArgsName.java`: класс для обработки аргументов командной строки.

`src/main/java/ru/job4j/io/SearchFilesByName.java`: класс для поиска файлов по имени.

`src/main/java/ru/job4j/io/FileFinder.java`: основной класс для управления поиском файлов.


## Возможные улучшения
* Расширение функциональности `ArgsName` для поддержки дополнительных типов аргументов.
* Добавление дополнительных фильтров в `FileFinder` для более точной настройки поиска.