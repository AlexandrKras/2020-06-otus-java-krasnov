#Выбор GC для приложения:
За критерий взяты параметр Throughput(производительность) и Latency(предсказуемость). 
Самым оптимальным для работы на моем компьютере оказался "Serial Collector" с Heap 4096Mb. 
Как видим из таблицы для ткущего приложения при Heap 4096Mb наилучшая производительность с минимальными задержками.
При этом максимальное время задержки составило 158мс это значение было в начале запуска приложения. 
Затем приложение стабильно работает с задержками на сборку не превышающих 1мс.
#Вывод:
Если в приложении мало объектов, которые будут перемещаться в область старого поколения, лучше отрабатывает сборщик "Serial Collector" и "Parallel Collector".
Но "Parallel Collector" работает лучше с большим Heap более 8196Mb.
Сборщик "G1" хорошо работает со сборкой мусора в старом и молодом поколении. 
Пробовал крутить параметр "-XX:MaxGCPauseMillis", он оказывает влияние на работу GC только для Heap не более 2048Mb.
Пробовал настраивать другие параметры на моем компьютере - эти параметры особого влияния на "G1" не оказали.
