# О приложении
В моем приложении можно управлять коллекцией музыкальных групп. 
Разработанное приложение является кульминацией обучения по дисциплине "Программирование на Java" 1-го курса.

## Взаимодествие клиент-сервер
После успешного подключения к серверу клиент может начать общение, которое строится следующим образом:
1. Клиент принимает от пользователя команду и аргументы.
2. Клиент запаковывает команду, аргументы и дробит их на пакеты.
3. Клиент отправляет пакеты на сервер и переходит в режим ожидания ответа.
4. Сервер собирает пакеты и восстанавливает команду и аргументы.
5. Сервер исполняет команду и формирует ответ.
6. Сервер посылает ответ клиенту.
7. Клиент принимает ответ.
   
Передача данных между сервером и клиентами осуществляется по протоколу UDP. 

![alt text](https://github.com/alldaygooning/lab8/blob/main/readme_images/diagramm_1.png?raw=true)

При каждом обновлении элементов коллекции одним из клиентов всем остальным клиентам необходимо сообщать об изменениях. Чтобы не приходилось несколь раз посылать разным клиентам одни и те же потенциально объемные данные, было принято решение воспользоваться бесплатным сервисом [JSON Silo](https://jsonsilo.com/).

JSON Silo позволяет хранить .json файлы объемом до 5Мб и предоставляет безлимитное число запросов к ним.

В моей реализации сервер периодически обновляет musicbands.json в хранилище на JSON Silo. Клиенты могут в любой момент считать свежие данные о коллекции с того же хранилища на JSON Silo.

![alt text](https://github.com/alldaygooning/lab8/blob/main/readme_images/diagramm_2.png?raw=true)

## Порты
Изначально приложение разрабатывалось для работы на сервере helios (Гелиос). На Гелиосе одновременно может быть запущено множество различных программ, каждая из которых может занимать случайный порт. 

В целях минимизации риска того, что порт, необходимый для работы моего приложения, будет оккупирован сторонней программой, серверная часть приложения производит цикл выборки порта.
Серверная часть приложения поочередно проверяет каждый из пяти заранее заданных портов* и запускается на первом доступном.

В случае если все порты заняты, серверная часть приложения будет периодически вновь проверять заранее заданные порты на доступность в атоматическом режиме.

Клиентская часть приложения проверяет каждый из пяти заранее заданных портов* и подключается к первому, на котором будет обнаружена серверная часть.

В случае если ни по одному из заранее заданных портов не удается подключиться к серверной части, клиентская часть сообщит пользователю о недоступности сервера и предоставит пользователю функционал для осуществления повторной попытки подключения.

###### *Заранее заданные порты: 51000, 51128, 51256, 51384, 51512

## Пакеты
При передачи данных по протоколу UDP на сетевом уровне происходит дробление сообщения на пакеты. Если хотя бы один из пакетов не будет доставлен или пакеты придут не в том порядке, то сообщение будет отклонено на сетевом уровне, т.е. приложение не получит ни сообщения об ошибке, ни ответа.

Максимальный размер сообщения, при котором оно гарантировано не будет разбито на пакеты - 508 бит.

Таким образом, если программно разбить каждое сообщение на маленькие сообщения по 508 бит и отправить их на сервер, то на сетевом уровне эти маленькие сообщения дробиться уже не будут. Это позволяет делегировать процесс сборки пакетов, а соответсвенно и обноружения ошибок в их порядке и количестве, на программный уровень. В отличие от сетевого, программный уровень может отправить сообщение об ошибке или иной сигнал о неисправности клиенту в ответ.

# Сервер
## Зависимости
* commons-lang
* HikariCP + slf4-api
* JSON-java
* log4j-api + log4j-core
* postgresql-java


# Клиент
## Режимы работы
## Адрес сервера
## Зависимости
* commons-lang
* flat-laf 
* JSON-java
