Правки: зельеварение требует 30 зелий и даёт +2% длительности за уровень интеллекта;
ломание блоков даёт немного опыта строительства. Раскладка Телосложения разнесена по строкам.

## Текущий баланс и настройки

Отображаемые имена: `essence_core`, `essence_stamina`, `essence_epic_fight_compat`.
Технические ID сохранены. Крафт даёт интеллект вдвое медленнее, нанесение урона —
силу в 1,5 раза медленнее. Беговые прыжки и уклонения Epic Fight дают ловкость.
Ношение брони тренируют железо, кольчуга, алмаз и незерит; кожа и золото не дают опыта.
При истощении броня сверх освоенной нагрузки запрещает прыжки.
Блок щитом расходует 2 единицы (целую молнию), стрелы — 1; скидка каждого навыка — 1,
итог ограничен нулём. Бережный щит и сокращение отключения щита дают 25%.
Бережный щит и экономный блок обменялись требованиями, зависимостями и местами.
Амортизация и скорость атаки требуют ловкость 10, здоровье — телосложение 7.

В списке модов доступны редакторы Stamina (Fabric: Mod Menu; NeoForge: Mods)
и Epic Fight Compat (NeoForge). Изменение серверных настроек требует администратора.
Ресурсные файлы Stamina находятся в `config/fauststamina/`; прежние значения
копируются из `config/faustcore/`, исходники сохраняются. Способности compat —
`config/faust-epic-compat/abilities.json`; раскладка дерева общая. Миграция требований
выполняется однократно, последующие изменения редактора сохраняются.
Сон и начальная защита от усталости не изменены.

# Faust Essence: Stamina

Версия **1.6.0** для Minecraft **1.21.1**, Fabric и NeoForge; JDK **21**.
Mod ID: `fauststamina`. Требуется [Faust Essence](https://github.com/faust-man/faust-essence-core) 1.6.0 или новее.
`main` содержит последнюю версию; `1.21.1` — ветка Minecraft 1.21.1.

Дополнение добавляет выносливость, усталость, вес брони, HUD и связанные навыки.
Без дополнения ядро сохраняет обычную прогрессию. Данные игрока и конфиги остаются
в прежнем пространстве `faustcore`, включая `config/faustcore/`.
Интеграция с [Epic Fight Compat](https://github.com/faust-man/faust-essence-epic-fight-compat) доступна на NeoForge.

## Установка

Установите ядро и один JAR дополнения под свой загрузчик на клиент и сервер.
Fabric требует Fabric API через зависимость ядра. NeoForge: 21.1.80 или новее
совместимой ветки 21.1; Fabric Loader: 0.16.9 или новее.

- `fabric/build/libs/fauststamina-fabric-1.21.1-1.6.0.jar`
- `neoforge/build/libs/fauststamina-neoforge-1.21.1-1.6.0.jar`

## Сборка и проверки

Клонируйте ядро в соседний каталог `core`, а этот репозиторий — в `faust-essence-stamina`:

```powershell
git clone -b 1.21.1 https://github.com/faust-man/faust-essence-core.git core
git clone -b 1.21.1 https://github.com/faust-man/faust-essence-stamina.git faust-essence-stamina
cd core
./gradlew.bat build
cd ../faust-essence-stamina
./gradlew.bat build
```

Используется Gradle Wrapper; отдельная установка Gradle не нужна. Первая сборка
требует сети. Путь к ядру: `-PcoreProjectDir=<путь>` (по умолчанию `../core`).
Общие исходники находятся в `src/main/`; `neoforge/` и `fabric/` содержат сборочные
настройки. Fabric исключает NeoForge entrypoint и remap выполняется Loom.
Ядро используется только при компиляции и не включается внутрь JAR дополнения.

Модельные тесты, проверка спрайтов и два отдельных процесса с провайдером/без него
выполняются в core через его `build`. Самостоятельный `build` здесь проверяет сборку
и упаковку двух загрузчиков; отдельной тестовой инфраструктуры в этом репозитории нет.
Dev-запуски с дополнением выполняются из core:

```powershell
./gradlew.bat :fabric:runClient -PwithStamina
./gradlew.bat :fabric:runServer -PwithStamina
./gradlew.bat :neoforge:runClient -PwithStamina
./gradlew.bat :neoforge:runServer -PwithStamina
```

EULA принимает владелец сервера. Компиляция и модельные тесты не заменяют проверку
клиентского HUD и игрового поведения на обоих загрузчиках.

## Лицензия и происхождение

Исходники выделены из Faust Essence core 1.6.0, commit `cc2dbee`.
Сохранён LICENSE исходного проекта (CC0-1.0); метаданные сохраняют исходную маркировку
`CC0-1.0 AND Apache-2.0`. Сторонние текстуры интерфейса JustLeveling остаются в ядре;
их сведения о происхождении находятся в его META-INF/NOTICE-faustcore.txt.
Gradle Wrapper сохраняет исходные уведомления Apache-2.0 в скриптах.
